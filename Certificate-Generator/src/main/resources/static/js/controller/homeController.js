/**
 * 
 */
var homeController = angular.module('certificateApp.homeController', []);

homeController.controller('homeController', function($scope, $location, ngNotify,
		homeService) {
	

	
	$scope.listAllCertificates = function(){
		homeService.getCertificates().then(function(response){
			if(response.data != null)
				$scope.certificates=response.data;
		});
	}
	
	$scope.keyStoreActive = {};
	homeService.getKeystoreStatus().then(function(response){
		if(response.data == true){
			$scope.keyStoreActive = "Keystore active";
			return;
		}
		$scope.keyStoreActive = "No keystore active";
	});
	
	
	$scope.listAllCertificates();

	$scope.keySizes = [1024, 2048];
	
	$scope.NewKeystore = function() {
		homeService.newKeystore().success(function(data) {
			ngNotify.set('Successfully created key store.' , {
				type : 'success',
				duration: 3000,
				theme: 'pitchy'
			});
			$scope.keyStoreActive = "Keystore active";
			
		}).error(function(data){
			console.log(data.data);
		});
	}

	$scope.OpenKeyStore = function() {
		$scope.resetOpenKeyStore();
		$('#openModal').modal('show');
	}

	$scope.open = {};
	$scope.certificates={};
	$scope.confirmOpenKeystore = function(open) {
		if(fils.name == null){
			ngNotify.set('Please select a keystore to open.' , {
				type : 'success',
				duration: 3000,
				theme: 'pitchy'
			});
			return;
		}
		homeService.openKeyStore(fils, open.password).success(function(data) {
			homeService.getCertificates().then(function(response){
				if(response.data != null)
					$scope.certificates=response.data;
			});
			ngNotify.set('Successfully opened key store.' , {
				type : 'success',
				duration: 3000,
				theme: 'pitchy'
			});
			$('#openModal').modal('hide');
		});
	}

	var flnm = {};
	var fils = {};
	$scope.uploadedFile = function(file) {
		fils=file.files[0];
		flnm = file.value;
	}

	$scope.SaveKeyStore = function() {
		$('#saveModal').modal('show');
	}

	$scope.saved = {};
	$scope.confirmSaveKeystore = function(saved) {
		homeService.saveKeyStore(saved.filename, saved.password).success(
				function(data) {
					$scope.cleanFieldsSave();
					ngNotify.set('Successfully saved key store.' , {
						type : 'success',
						duration: 3000,
						theme: 'pitchy'
					})
				});
	}
	
	$scope.addExtension = function(save){
		var n = save.filename.endsWith(".jks");
		if(!n)
			save.filename = save.filename + ".jks";
	}
	
	$scope.generateCertificate = function() {
		$("#generateCertificateModal").modal("show");
	}
	
	$scope.resetOpenKeyStore = function(){
		$scope.open={};
		$("#file").val("");
	}
	
	$scope.cert={};
	$scope.cert.keySize = 1024;
	$scope.parent={};
	$scope.certificates=[];
	$scope.createCertificate = function(cert){
		if($scope.selfSigned)
			homeService.createRootCertificate(cert).success(
					function(data) {
						$scope.certificates.push(data);
						$scope.cleanFields();
						ngNotify.set('Successfully created root certificate.' , {
							type : 'success',
							duration: 3000,
							theme: 'pitchy'
						})
					$("#generateCertificateModal").modal("hide");
			});
		else
			homeService.createCertificate(cert,$scope.parent.alias,$scope.parent.password).success(
					function(data) {
						$scope.certificates.push(data);
						$scope.cleanFields();
						ngNotify.set('Successfully created certificate.' , {
							type : 'success',
							duration: 3000,
							theme: 'pitchy'
						})
						homeService.getCertificates().then(function(response){
							$scope.certificates=response.data;
						});
						$("#generateCertificateModal").modal("hide");
			});
	}
	
	$scope.certificateId="";
	$scope.getExisting = function(){
		homeService.getExisting($scope.certificateId).then(function(response){
			$scope.certificates={};
			$scope.certificates[0] = response.data;
		});
	}
	
	$scope.checkOCSP = function(){
		homeService.checkOCSP($scope.certificateId).then(function(response){
			if(response.data == true){
				ngNotify.set('Certificate is not revoked.' , {
					type : 'success',
					duration: 3000,
					theme: 'pitchy'
				})
				return;
			} 
			ngNotify.set('Certificate has been revoked.' , {
				type : 'success',
				duration: 3000,
				theme: 'pitchy'
			})
		});
	}
	
	$scope.checkValidity = function(){
		homeService.checkValidity($scope.certificateId).then(function(response){
			if(response.data == true){
				ngNotify.set('Certificate is valid.' , {
					type : 'success',
					duration: 3000,
					theme: 'pitchy'
				})
				return;
			} 
			ngNotify.set('Certificate is not valid or does not exist.' , {
				type : 'success',
				duration: 3000,
				theme: 'pitchy'
			})
		});
	}
	
	$scope.revokeCertificate = function(certificateId){
		homeService.revokeCertificate(certificateId).then(function(response){
			for(var i=0;i<response.data.length;i++){
				for(var j=0;j<$scope.certificates.length;j++){
					if(response.data[i].serialNumber==$scope.certificates[j].serialNumber){
						$scope.certificates.splice(j,1);
						break;
					}
				}
			}
		});
	}
	
	$scope.cleanFields = function(){
		$scope.cert = {};
	}
	
	$scope.cleanFieldsSave = function(){
		$scope.save = {};
	}
	
})