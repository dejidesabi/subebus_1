app.service("reporteService",['$http', '$q','$window', function($http, $q,$window){
	
	this.addVenta = function(venta) {
		var d = $q.defer();
		$http.post("venta/add",venta).then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}
	
	this.getVM = function(direccion,tipo,idSuc) {
		var d = $q.defer();
		//$http.get("membresia/byDTS/"+direccion+"/"+idSuc).then(
		$http.get("membresia/asignar/"+direccion+"/"+tipo+"/"+idSuc).then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}
	
	this.getVentas = function(idUsr) {
		var d = $q.defer();
		$http.post("venta/bySucursal/",idUsr).then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}
	
}]);

app.controller("reporteController",['$scope','$rootScope','$window', '$location', '$cookieStore','reporteService','sessionService',"sucursalService",function($scope,$rootScope, $window, $location, $cookieStore, reporteService,sessionService,sucursalService){
	//sessionService.isAuthenticated();
	 $rootScope.titulo = "Pagina de Reportes";
	 $rootScope.Menu = "Reporte";
	  
	 sessionService.isAuthenticated().then(function(sesion) {
		 $scope.CurrentDate = new Date()
		 $scope.idSucursal = sesion.idSucursal;
		  
		  $scope.printVenta = function(fInicio,fFin){
				 
				 var inicio = moment(fInicio).format('MM-DD-YYYY');
				 var fin = moment(fFin).format('MM-DD-YYYY');
				 var url = "venta/xlsVentasP/"+$scope.idSucursal+"/"+inicio+"/"+fin;  
				 var link = document.createElement("a");
				    link.download = "ReporteVenta_"+inicio+"_a_"+fin+".xls";
				    link.href = url;
				    link.click();

			 }
		  $scope.printRuta= function(fInicio,fFin){
				 
				 var inicio = moment(fInicio).format('MM-DD-YYYY');
				 var fin = moment(fFin).format('MM-DD-YYYY');
				 var url = "rutaMem/xlsRutaP/"+$scope.idSucursal+"/"+inicio+"/"+fin;  
				 var link = document.createElement("a");
				    link.download = "ReporteRuta_"+inicio+"_a_"+fin+".xls";
				    link.href = url;
				    link.click();

			 }
	 
	 //Fin
	 
	 })
	
} ]);	

