app.service("alertaService",['$http', '$q','$window', function($http, $q,$window){

	this.getAlertas = function(){
		var d = $q.defer();
		$http.get("/alertas/get").then(function(response) {
			d.resolve(response.data);
		}, function(response) {
			if(response.status==403){
				//alert("No tiene autorización de realizar esta acción");
				$location.path("/login");
				d.reject(response);
			}
		});
		return d.promise;
	}
	this.getNumAlertas = function(){
		var d = $q.defer();
		$http.get("/alertas/numAlertas").then(function(response) {
			d.resolve(response.data);
		}, function(response) {
			if(response.status==403){
				//alert("No tiene autorización de realizar esta acción");
				$location.path("/login");
				d.reject(response);
			}
		});
		return d.promise;
	}

	
}]);

app.controller("alertaController",['alertaService','$scope','$rootScope','$window', '$location', '$cookieStore',function(alertaService,$scope,$rootScope, $window, $location, $cookieStore){
	
	$rootScope.titulo = "Pagina de Alertas";
		alertaService.getAlertas().then(function(data) {
				$scope.listAlerta = data;
		});	
		  
	
	

} ]);