app.directive('submenu',function(){
	return{
		templateUrl:'pages/menuTemplate.html'
	};
});

app.controller('headerController',['$scope','$rootScope','$location','$http','$cookieStore',function($scope,$rootScope,$location,$http,$cookieStore){
	
	
	$http.get("/usuario/check").then(function(response){
		$rootScope.variable = true;
	},function(response){
		if(response.status==403){
			$rootScope.variable = false;
		}
		$location.path("/login");
		console.log(response);
	});
	$scope.verificaUser = function(){
		$http.get("/usuario/check").then(function(response){
			$rootScope.variable = true;
		},function(response){
			if(response.status==403){
				$rootScope.variable = false;
			}
			alert("Sesion Caducada");
			$location.path("/login");
		});
	}
	$rootScope.checaUsuario=function(){
	$http.get("/usuario/check").then(function(response){
		$rootScope.variable = true;
	},function(response){
		if(response.status==403){
			$rootScope.variable = false;
		}
		$location.path("/login");
	});
	}
	$scope.CerrarSesion = function(){
		$http.get("/usuario/cerrarSesion").then(function(response) {
			$rootScope.variable = false;
			$location.path("/login");
		}, function(response) {
			$location.path("/login");
		});
	};
	
	
}]);