package com.certificate.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Service;

import com.certificate.model.IssuerData;

@Service
public class KeyStoreService {
	
	private HashMap<String,Certificate> revokedCertificates;
	
	public KeyStoreService() {
		revokedCertificates = new HashMap<>();
	}
	/**
	 * Zadatak ove funkcije jeste da ucita podatke o izdavaocu i odgovarajuci privatni kljuc.
	 * Ovi podaci se mogu iskoristiti da se novi sertifikati izdaju.
	 * 
	 * @param keyStore - datoteka odakle se citaju podaci
	 * @param alias - alias putem kog se identifikuje sertifikat izdavaoca
	 * @param keyPass - lozinka koja je neophodna da se izvuce privatni kljuc
	 * @return - podatke o izdavaocu i odgovarajuci privatni kljuc
	 * @throws UnrecoverableKeyException 
	 */
	public IssuerData readIssuerFromStore(KeyStore keyStore, String alias, char[] keyPass) throws UnrecoverableKeyException {
		try {
			//Iscitava se sertifikat koji ima dati alias
			Certificate cert = keyStore.getCertificate(alias);
			//Iscitava se privatni kljuc vezan za javni kljuc koji se nalazi na sertifikatu sa datim aliasom
			PrivateKey privKey = (PrivateKey) keyStore.getKey(alias, keyPass);

			X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
			return new IssuerData(privKey, issuerName);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Ucitava sertifikat is KS fajla
	 */
    public Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias) {
		try {
			//Ucitavanje KeyStore-a
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			ks.load(in, keyStorePass.toCharArray());
			
			//Iscitava se sertifikat koji ima dati alias
			if(ks.isKeyEntry(alias)) {
				Certificate cert = ks.getCertificate(alias);
				return cert;
			}
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Ucitava privatni kljuc is KS fajla
	 */
	public PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) {
		try {
			//Ucitavanje KeyStore-a
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			ks.load(in, keyStorePass.toCharArray());
			
			//Iscitava se privatni kljuc koji ima dati alias
			if(ks.isKeyEntry(alias)) {
				PrivateKey pk = (PrivateKey) ks.getKey(alias, pass.toCharArray());
				return pk;
			}
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Ucitava KS
	 */
	public KeyStore loadKeyStore(File fileObj, char[] password) throws KeyStoreException, NoSuchProviderException ,IOException {
		KeyStore keyStore = this.createNewKeyStore();
		try {
			if (fileObj != null) {
				keyStore.load(new FileInputStream(fileObj.getAbsolutePath()), password);
			} else {
				//Ako je cilj kreirati novi KeyStore poziva se i dalje load, pri cemu je prvi parametar null
				keyStore.load(null, password);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return keyStore;
	}

	/**
	 * Cuvanje KS-a u fajl
	 */
	public File saveKeyStore(KeyStore keyStore,String fileName, char[] password) {
		File file = new File(fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			keyStore.store(fos, password);
			fos.close();
			return file;
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Ucitava privatni kljuc is KS fajla
	 */
	public void write(KeyStore keyStore,String parentAlias,String alias, PrivateKey privateKey, char[] password, Certificate certificate) {
		try {
			
			Certificate[] certificates=null;
			//Provera da li sertifikat self-assigned. Ako nije, pokupimo lanac sertifikata 
			if(parentAlias != null && keyStore.containsAlias(parentAlias)){
				 certificates = keyStore.getCertificateChain(parentAlias);
			}
			if (certificates != null && certificates.length != 0) {
				//Ako postoji lanac, upisujemo sertifikat na njegov pocetak i dodajemo sertifikat u KS
				ArrayList<Certificate> temp = new ArrayList<>(Arrays.asList(certificates));
				temp.add(0, certificate);
				keyStore.setKeyEntry(alias, privateKey, password, temp.toArray(new Certificate[temp.size()]));
			} else {
				keyStore.setKeyEntry(alias, privateKey, password, new Certificate[] { certificate });
			}
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
	}
	
	public KeyStore createNewKeyStore() throws KeyStoreException, NoSuchProviderException{
		return KeyStore.getInstance("JKS", "SUN");
	}
	
	/**
	 * Provera validnosti CA sertifikata po datumu i CA atributu(ne vrsi proveru da li je sertifikat povucen). Vraca podatke o vlasniku.
	 */
	public IssuerData validateCa(KeyStore keyStore, String alias, char[] keyPass) throws KeyStoreException, UnrecoverableKeyException{
		X509Certificate cert=(X509Certificate) keyStore.getCertificate(alias);
		IssuerData id=null;
		boolean validDate=true;
		try{
			cert.checkValidity(new Date());
		}catch (CertificateExpiredException | CertificateNotYetValidException e) {
			validDate=false;
		}
		if(validDate)// && cert.getKeyUsage()[5])
			id=this.readIssuerFromStore(keyStore, alias, keyPass);
		return id;
	}
	
	/**
	 * Pronalazenje sertifikata u KS zadavanjem njegovog id-a.
	 */
	public X509Certificate getSertificateBySerialNumber(KeyStore keyStore,String certificateId) throws KeyStoreException, NullPointerException{
		Enumeration<String> aliases=keyStore.aliases();
		while(aliases.hasMoreElements()){
			String alias=aliases.nextElement();
			X509Certificate temp=(X509Certificate) keyStore.getCertificate(alias);
			if(temp.getSerialNumber().toString().equals(certificateId))
				return temp;
		}
		throw new NullPointerException();
	}
	
	/**
	 * Povlacenje sertifikata
	 */
	public Iterable<X509Certificate> revokeCertificate(KeyStore keyStore, String certificateID) throws KeyStoreException, NullPointerException {
		//Pronalazenje po id-u i preuzimanje njegovog aliasa
		X509Certificate certificate = this.getSertificateBySerialNumber(keyStore, certificateID);
		String alias = keyStore.getCertificateAlias(certificate);
		ArrayList<X509Certificate> deleted=new ArrayList<>();
		
		//Ako sertifikat pripada krajnjem korisniku obrisi ga iz KS-a i dodaj u listu povucenih sertifikata
		if(certificate.getKeyUsage() == null || !certificate.getKeyUsage()[5]){
			keyStore.deleteEntry(alias);
			revokedCertificates.put(alias, certificate);
			deleted.add(certificate);
		} else {
			//Ako je sertifikat CA, povlacimo i sve sertifikate u cujem lancu se nalazi sertifikat koji zelimo da povucemo
			ArrayList<Certificate> chain = new ArrayList<>(Arrays.asList(keyStore.getCertificateChain(alias)));
			Enumeration<String> aliases=keyStore.aliases();
			while(aliases.hasMoreElements()){
				String tempA = aliases.nextElement();
				ArrayList<Certificate> tempChain =  new ArrayList<> (Arrays.asList(keyStore.getCertificateChain(tempA)));
				if(tempChain.containsAll(chain)){
					keyStore.deleteEntry(tempA);
					X509Certificate c=(X509Certificate) tempChain.get(0);
					revokedCertificates.put(tempA, c);
					deleted.add(c);
				}
			}
		}
		return deleted;
	}
	
}
