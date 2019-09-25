app.service("sucursalService",['$http', '$q','$window', function($http, $q,$window){
	this.altaSucursal = function(suc) {
		var d = $q.defer();
		$http.post("sucursal/add", suc).then(
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
	
	this.getSucursal = function(id) {
		var d = $q.defer();
		$http.get("sucursal/findAll").then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}
	

}]);

app.controller("sucursalController",['$scope','$rootScope','$window', '$location', '$cookieStore','sucursalService','sessionService','printService',function($scope,$rootScope, $window, $location, $cookieStore, sucursalService,sessionService,printService){
	//sessionService.isAuthenticated();
	 $scope.idSuc = $cookieStore.get('idSucursal');
	 $rootScope.titulo = "Pagina de Sucursal";
	 $rootScope.Menu = "Sucursal"
	

	 function showSeconds(){
		 alert("Ejecutado");
		}
		/*usamos setTimeout() para que se
		ejecute cada segundo (1000 milisegundos como parámetro)*/
		//setInterval(showSeconds,10000);
	 
	 	$scope.obtenerSucursal= function(){
	 		 sucursalService.getSucursal().then(function(data) {
					$scope.listSucursal = data;
				})
	 	}
	 	$scope.obtenerSucursal();
		$scope.addSucursal = function(suc) {	
		 sucursalService.altaSucursal(suc).then(function(data) {
						alert("Sucursal Agregada");
						$scope.altaSucursal = null;
						$scope.obtenerSucursal();
					})
		}

} ]);	

