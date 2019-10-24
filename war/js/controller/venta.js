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
	
	this.getVentas = function(idUsr) {
		var d = $q.defer();
		$http.get("venta/bySucursal/",idUsr).then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}
	
}]);

app.controller("ventaController",['$scope','$rootScope','$window', '$location', '$cookieStore','ventaService','sessionService',"sucursalService",function($scope,$rootScope, $window, $location, $cookieStore, ventaService,sessionService,sucursalService){
	//sessionService.isAuthenticated();
	 $rootScope.titulo = "Pagina de Venta";
	 $rootScope.Menu = "Venta";
	 $scope.isMember = false;
	 
	 sessionService.isAuthenticated().then(function(sesion) {
		 
		 
		 ventaService.getVentas(sesion).then(function(data) {
				$scope.ventaList = data;
			})
				
	
	 $scope.addVenta = function(data) {	
		 
		 console.log(data)
		 $('#mdlLoad').modal('show');
		 ventaService.addVenta(data).then(function(data) {
			 $('#mdlLoad').modal('hide');
			 alert("Se ha agregado la venta")
			 location.reload();
					
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
				document.getElementById('qr').innerHTML = create_qrcode(data.qr);
				$scope.altaVenta.idMembresia = data.id;
				console.log("Membresia ",data);
			})
		 
	 }
	 $scope.cancelOp = function(){
		 $scope.altaVenta.idMembresia = null;
		 $scope.isMember = false;
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
	 
	 })
} ]);	

