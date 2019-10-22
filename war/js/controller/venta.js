app.service("ventaService",['$http', '$q','$window', function($http, $q,$window){
	
	this.addVenta = function(venta) {
		var d = $q.defer();
		$http.post("venta/add",venta).then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}
	
	this.getVM = function(direccion,idSuc) {
		var d = $q.defer();
		$http.get("membresia/byDTS/"+direccion+"/"+idSuc).then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}

	
}]);

app.controller("ventaController",['$scope','$rootScope','$window', '$location', '$cookieStore','ventaService','sessionService',"sucursalService",function($scope,$rootScope, $window, $location, $cookieStore, ventaService,sessionService,sucursalService){
	//sessionService.isAuthenticated();
	 $scope.idSuc = $cookieStore.get('idSucursal');
	 $rootScope.titulo = "Pagina de Venta";
	 $rootScope.Menu = "Venta";
	 $scope.isMember = false;
		 
	 $scope.addVenta = function(data) {	
		 
		 console.log(data)
		 $('#mdlLoad').modal('show');
		 ventaService.addVenta(data).then(function(data) {
			 $('#mdlLoad').modal('hide');
			 alert("Se ha agregado la venta")			
					
		})
	
		
	 }
	 $scope.obtenerSucursal= function(){
 		 sucursalService.getSucursal().then(function(data) {
				$scope.listSucursal = data;
			})
 	}
	 $scope.getMembresiaVenta= function(){
		 
 	}
	 $scope.obtenerSucursal();
	 $scope.getMembresia = function(duracion,sucursal){
		 $scope.isMember = true;
		 ventaService.getVM(duracion,sucursal).then(function(data) {
				$scope.newMem = data;
				console.log("Membresia ",data);
			})
		 document.getElementById('qr').innerHTML = create_qrcode("3Dca4fd67852d33c6191ff9dfe53d06da238b48f10");
	 }
	 $scope.costo = function (tipo){
		 switch (tipo) {
		case "Semanal":
			$scope.altaVenta.precio = 100;
			break;
		case "Mensual":
			$scope.altaVenta.precio = 300;
			break;
		
		}
	 }
	
} ]);	

