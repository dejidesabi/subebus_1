app.service("rutaService",['$http', '$q','$window', function($http, $q,$window){
	this.altaRuta = function(lote) {
		var d = $q.defer();
		$http.post("ruta/add", lote).then(
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
	this.getRutas = function(id) {
		var d = $q.defer();
		$http.get("ruta/findAll").then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}

}]);

app.controller("rutaController",['$scope','$rootScope','$window', '$location', '$cookieStore','rutaService','sessionService','printService',function($scope,$rootScope, $window, $location, $cookieStore, rutaService,sessionService,printService){
	//sessionService.isAuthenticated();
	 $scope.idSuc = $cookieStore.get('idSucursal');
	 $rootScope.titulo = "Pagina de Rutas";
	 $rootScope.Menu = "Ruta"
	 rutaService.getSucursal().then(function(data) {
		$scope.sucursalData=data;
		
		//console.log("La Sucursal",$scope.sucursalData);
	})

	 function showSeconds(){
		 alert("Ejecutado");
		}
		/*usamos setTimeout() para que se
		ejecute cada segundo (1000 milisegundos como parámetro)*/
		//setInterval(showSeconds,10000);
	 
	 	$scope.obtenerRuta = function(){
	 		 rutaService.getRutas().then(function(data) {
					$scope.listRuta = data;
				})
	 	}
	 	$scope.obtenerRuta();
		$scope.addRuta = function(ruta) {	
		 rutaService.altaRuta(ruta).then(function(data) {
						alert("Ruta Agregada");
						$scope.altaRuta = null;
						$scope.obtenerRuta();
					})
		}

} ]);	

