app.service("ventaService",['$http', '$q','$window', function($http, $q,$window){
	
	this.addVenta = function(venta) {
		var d = $q.defer();
		$http.post("venta/add",venta).then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}

	
}]);

app.controller("ventaController",['$scope','$rootScope','$window', '$location', '$cookieStore','ventaService','sessionService',function($scope,$rootScope, $window, $location, $cookieStore, ventaService,sessionService){
	//sessionService.isAuthenticated();
	 $scope.idSuc = $cookieStore.get('idSucursal');
	 $rootScope.titulo = "Pagina de Venta";
	 $rootScope.Menu = "Venta";
	 $scope.altaVenta = null;
		 
	 $scope.addVenta = function(data) {	
		 document.getElementById('qr').innerHTML = create_qrcode("Felipe Sabeee");
		 console.log(data)
		 $('#mdlLoad').modal('show');
		 ventaService.addVenta(data).then(function(data) {
			 $('#mdlLoad').modal('hide');
			 alert("Se ha agregado la venta")			
					
		})
	
		
	 }
	
} ]);	

