app.service("dashboardService",['$http', '$q','$window', function($http, $q,$window){
	this.crearSucursal = function(sucursal) {
		var d = $q.defer();
		$http.post("lote/add", sucursal).then(
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
		$http.get("/sucursal/find/"+id).then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}
	

	
}]);

app.controller("dashboardController",['$scope','$rootScope','$window', '$location', '$cookieStore','dashboardService','sessionService',function($scope,$rootScope, $window, $location, $cookieStore, dashboardService,sessionService){
	//sessionService.isAuthenticated();
	 $scope.idSuc = $cookieStore.get('idSucursal');
	 $rootScope.Menu = "Principal";
	 $rootScope.titulo = "Pagina de Inicio";
	
	 $(document).ready(function() {
		 var nombre= "Toluca Centro";
			 var url = "https://www.google.com/maps/embed/v1/place?key=AIzaSyCrlLXFntBgBODxEDBVV0C6aI59brxVy5A&q="+nombre;
			 console.log("Url", url)
			 document.getElementById("mapaMvl").src=url;
			 
//			 $scope.generarQR();
	    });
	 sessionService.isAuthenticated().then(function(sesion) {
		 $scope.idSucursal = sesion.idSucursal;
		  console.log(sesion)
		  sessionService.consultarPerfilesTodos().then(function(data) {
			  
			  for(i in data){
				  if(sesion.perfil == data[i].tipo){
					 $rootScope.autorizacion = data[i].permisos;
					 console.log($rootScope.autorizacion);
				  }
			  }
		  });
	 });
	 dashboardService.getSucursal( $scope.idSuc).then(function(data) {
		$scope.sucursalData=data;
		$scope.SucursalName=data.nombre;
	
		console.log("La Sucursal",$scope.sucursalData);
	
	})
	
	
	
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

