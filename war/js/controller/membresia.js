app.service("membresiaServices",['$http', '$q','$window', function($http, $q,$window){
	
	this.getMembresia = function(id) {
		var d = $q.defer();
		$http.get("membresia/findAll").then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}

	
}]);

app.controller("membresiaController",['$scope','$rootScope','$window', '$location', '$cookieStore','membresiaServices','sessionService',function($scope,$rootScope, $window, $location, $cookieStore, membresiaServices,sessionService){
	//sessionService.isAuthenticated();
	 $scope.idSuc = $cookieStore.get('idSucursal');
	 $rootScope.titulo = "Pagina de Membresia";
	 $rootScope.Menu = "Membresia"
	 membresiaServices.getMembresia().then(function(data) {
		$scope.membresiaData=data;
		
		//console.log("La Sucursal",$scope.sucursalData);
	})
	
} ]);	

