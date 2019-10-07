app.service("usuarioService",['$http', '$q','$window', function($http, $q,$window){
	this.addUsuario = function(send) {
		var d = $q.defer();
		$http.post("usuario/registro", send).then(
				function(response) {
					console.log(response);
					d.resolve(response.data);
				},
				function(response) {
					if(response.status==400){
					alert("No se puede crear "
							+ usuario.usuario + " usuario o correo no disponibles");
					}if(response.status==403){
						//alert("No tiene permiso de realizar esta acción");
//						$location.path("/login");
					}
					d.reject(response);
					$window.location.reload;
				});
		return d.promise;
	}
	this.getPerfill = function() {
		var d = $q.defer();
		$http.get("perfil/getAll/").then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}
	
	this.getUsers = function() {
		var d = $q.defer();
		$http.get("usuario/getAll/").then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}

	
}]);

app.controller("usuarioController",['$scope','$rootScope','$window', '$location', '$cookieStore','usuarioService','sessionService','sucursalService',function($scope,$rootScope, $window, $location, $cookieStore, usuarioService,sessionService,sucursalService){
	//sessionService.isAuthenticated();
	 $scope.idSuc = $cookieStore.get('idSucursal');
	 $rootScope.Menu = "Usuario";
	 $rootScope.titulo = "Pagina de Usuario";
	 $scope.formhide = true;
	 $scope.msgError = "";
	 $scope.boxError = false;

	 sucursalService.getSucursal().then(function(data) {
			$scope.sucursalData=data;
			
			//console.log("La Sucursal",$scope.sucursalData);
		})
	usuarioService.getPerfill().then(function(data){
		$scope.perfilData=data;
	})
	$scope.getListUsers = function(){
	usuarioService.getUsers().then(function(data){
		$scope.listUser=data;
	})
	 }	
	 $scope.validPassword = function(){
if(typeof($scope.altaUsuario.password)!== 'undefined'){
		 if($scope.altaUsuario.password != $scope.confirmPsw){
				$scope.boxError = true;
				$scope.msgError = "Las contraseña debe ser la misma, verifique"
			}else{
				$scope.boxError = false;
				$scope.msgError = ""
			}
		}
	 }
	 $scope.getListUsers();
		$scope.addUsuario = function(send){
			
			if(send.password != $scope.confirmPsw){
				$scope.boxError = true;
				$scope.msgError = "Las contraseña debe ser la misma, verifique"
					return
			}
			$('#mdlLoad').modal('show');
		 usuarioService.addUsuario(send).then(function(data){
			 $('#mdlLoad').modal('hide');
			 alert("Se ha registrado\n"+data);
			 $scope.altaUsuario = null;
			 $scope.getListUsers();
		 })
		 
	 }
	
//	$scope.generarQR = function(){
//		 $('#qrcode').qrcode({
//			 render: 'image',
//			 with: 150,
//			 height: 150,
//			 color:'#3A3',
//			 text: 'Hola QR'
//		 })
//	 }
} ]);	

