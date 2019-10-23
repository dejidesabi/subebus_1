app.service("loteServices",['$http', '$q','$window', function($http, $q,$window){
	this.altaLote = function(lote) {
		var d = $q.defer();
		$http.post("lote/add", lote).then(
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
	this.delLote = function(id) {
		var d = $q.defer();
		$http.get("lote/delete/"+id).then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}
	this.getLotes = function(p) {
		var d = $q.defer();
		$http.get("lote/findAllP/"+p).then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}
	this.getMembresia = function(id) {
		var d = $q.defer();
		$http.get("membresia//byLote/"+id).then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}

	
}]);

app.controller("loteController",['$scope','$rootScope','$window', '$location', '$cookieStore','loteServices','sessionService','printService','sucursalService',function($scope,$rootScope, $window, $location, $cookieStore, loteServices,sessionService,printService,sucursalService){
	//sessionService.isAuthenticated();
	 $scope.idSuc = $cookieStore.get('idSucursal');
	 $rootScope.titulo = "Pagina de Lotes";
	 $rootScope.Menu = "Lote"
	
	 loteServices.getSucursal().then(function(data) {
		$scope.sucursalData=data;
		
		//console.log("La Sucursal",$scope.sucursalData);
	})
	$scope.nombreSuc = function(id){
		 sucursalService.getNameSucursal(id).then(function(data) {
				return data;
			})
	 }
	$scope.obtenerLotes = function(){
		 loteServices.getLotes(1).then(function(data) {
		
				$scope.listLote=data;
				for(i in $scope.listLote){
					for(o in $scope.sucursalData){
						if($scope.listLote[i].idSucursal == $scope.sucursalData[o].id ){
							$scope.listLote[i].idSucursal = $scope.sucursalData[o].nombre;
						}
					}
					
				}
								
			})
	 }
	 function showSeconds(){
		 alert("Ejecutado");
		}
		/*usamos setTimeout() para que se
		ejecute cada segundo (1000 milisegundos como parámetro)*/
		//setInterval(showSeconds,10000);
	 
	 $scope.obtenerLotes();
		$scope.addLote = function(lote) {	
			$('#mdlLoad').modal('show');
		 loteServices.altaLote(lote).then(function(data) {
			 $('#mdlLoad').modal('hide');
						alert("Lote Agregado");
						$scope.altaLote = null;
						 $scope.obtenerLotes();
					})
		}
	$scope.imprimirFisico = function(id){
		window.open('lote/print/'+id, '_blank');
		//window.location.href='lote/print/'+id;
//		printService.printLote().then(function(data){
//			
//		})
	}	
	$scope.eliminarLote = function(id){
		var r = confirm("Desea Eliminar el Lote?\nAl realizar esta accion se eliminara la membresia");
		if(r){
		loteServices.delLote(id).then(function(data) {
			alert(data);
			 $scope.obtenerLotes();
		})
		}
	}
	$scope.verMembresia = function(id){
		
		loteServices.getMembresia(id).then(function(data) {
			$scope.membresiaData = data;
			$('#mdlShowMembresia').modal('show');
		})
		
	}
} ]);	

