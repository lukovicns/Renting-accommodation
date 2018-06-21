package com.certificate.service;

import java.math.BigInteger;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Service;

import com.certificate.model.CertificateResponse;
import com.certificate.model.IssuerData;
import com.certificate.model.SubjectData;
import com.certificate.model.X500NameMap;





@Service
public class CertificateService {
	
	
	public CertificateService() {}
	
	
	/**
	 * Vraca listu svih sertifikata prosledjenog KeyStore-a
	 */
	public ArrayList<CertificateResponse> getSertificates(KeyStore keyStore) throws KeyStoreException{
		ArrayList<CertificateResponse> list = new ArrayList<CertificateResponse>();
		Enumeration<String> aliases=keyStore.aliases();
		while(aliases.hasMoreElements()){
			String alias=aliases.nextElement();
			X509Certificate temp=(X509Certificate) keyStore.getCertificate(alias);
			list.add(this.map(temp));
		}
		return list;
	}
	
	
	/**
	 * Mapira X509Certificate(java.security) na nasu klasu CertificateResponse
	 */
	public CertificateResponse map(X509Certificate cert){
		CertificateResponse response = new CertificateResponse();
		this.setX500Name(new X500Name(cert.getSubjectX500Principal().getName(X500Principal.RFC1779)),response.getSubjectData());
		this.setX500Name(new X500Name(cert.getIssuerX500Principal().getName(X500Principal.RFC1779)),response.getIssuerData());
		response.setStartDate(cert.getNotBefore());
		response.setEndDate(cert.getNotAfter());
		response.setSerialNumber(""+cert.getSerialNumber());
		response.setAlias(response.getSubjectData().getCn());
		response.setKeySize(((RSAPublicKey)cert.getPublicKey()).getModulus().bitLength());
		return response;
	}
	
	/**
	 * Mapiranje podataka o vlasniku sertifikata
	 */
	private void setX500Name(X500Name nameData,X500NameMap nameMapped){
		nameMapped.setC(IETFUtils.valueToString((nameData.getRDNs(BCStyle.C)[0]).getFirst().getValue()));
		nameMapped.setCn(IETFUtils.valueToString((nameData.getRDNs(BCStyle.CN)[0]).getFirst().getValue()));
		nameMapped.setE(IETFUtils.valueToString((nameData.getRDNs(BCStyle.E)[0]).getFirst().getValue()));
		nameMapped.setGivenName(IETFUtils.valueToString((nameData.getRDNs(BCStyle.GIVENNAME)[0]).getFirst().getValue()));
		nameMapped.setO(IETFUtils.valueToString((nameData.getRDNs(BCStyle.O)[0]).getFirst().getValue()));
		nameMapped.setOu(IETFUtils.valueToString((nameData.getRDNs(BCStyle.OU)[0]).getFirst().getValue()));
		nameMapped.setSurname(IETFUtils.valueToString((nameData.getRDNs(BCStyle.SURNAME)[0]).getFirst().getValue()));
	}
	
	/**
	 * Kreiranje novog sertifikata
	 */
	public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData,boolean ca) throws CertIOException {
		try {
			//Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
			//Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
			//Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			
			//Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
			BouncyCastleProvider bcp = new BouncyCastleProvider();
			builder = builder.setProvider(bcp);

			//Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
			ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

			//Postavljaju se podaci za generisanje sertifiakta
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
					new BigInteger(subjectData.getSerialNumber()),
					subjectData.getStartDate(),
					subjectData.getEndDate(),
					subjectData.getX500name(),
					subjectData.getPublicKey());
			if(ca)
				certGen.addExtension(Extension.keyUsage,true, new KeyUsage(KeyUsage.keyCertSign));

			//Generise se sertifikat
			X509CertificateHolder certHolder = certGen.build(contentSigner);

			//Builder generise sertifikat kao objekat klase X509CertificateHolder
			//Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider(bcp);

			//Konvertuje objekat u sertifikat
			return certConverter.getCertificate(certHolder);
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		return null;
	}
}
