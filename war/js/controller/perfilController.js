app.service('perfilService', [ '$http', '$q', '$location', function($http, $q, $location) {
	
	this.crearPerfil = function(perfil) {
		var d = $q.defer();
		$http.post("/perfil/crear", perfil).then(function(response) {
			console.log(response);
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
	

	
	this.consultarPerfilesTodosU = function() {
		var d = $q.defer();
		$http.get("/perfil/consultarTodosU").then(function(response) {
			d.resolve(response.data);
		}, function(response) {
			if(response.status==403){
				//alert("No tiene autorización de realizar esta acción");
				$location.path("/login");
				d.reject(response);
			}
		});
		return d.promise;
	};

	this.actualizarPerfil = function(perfil) {
		var d = $q.defer();
		$http.post("/perfil/actualiza", perfil).then(function(response) {
			console.log(response);
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	};
	
}]);

app.controller('perfilController', [
	'$scope',
	'$location',
	'perfilService',
	'$window',
	function($scope, $location, perfilService, $window) {
		
		
		$scope.perfil={
				permisos:[false,false,false,false,false,false,false,false,false,false]
		};
		$scope.EnviarFormulario = function() {
			console.log($scope.perfil);
			perfilService.crearPerfil($scope.perfil).then(
					function(data) {
						alert("Perfil creado correctamente");
						$location.path("/modificarperfiles");
					},function(data){
						$scope.perfil={
								permisos:[false,false,false,false,false,false,false,false,false,false]
						};
					});
		}
	} ]);

app.controller('controladorListaPerfiles', [ '$scope', 'perfilService',
	'$location', '$window','$rootScope','sessionService', function($scope, perfilService, $location, $window,$rootScope,sessionService) {
	
	
	 $rootScope.titulo = "Pagina de Perfil";
	 $rootScope.Menu = "Perfil";
	  
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
			
			  
			  $scope.perfilesLista = data;
				
			
			
			$scope.actualizarPerfil = function(perfil){
				console.log(perfil);
				perfilService.actualizarPerfil(perfil).then(
						function(data) {
							alert("Perfil modificado correctamente");
							$window.location.reload();
						});	
			}
			
			$scope.eliminarPerfil = function($scope){
				console.log($scope);
				perfilService.eliminaPerfil($scope).then(
						function(data){
							alert("Perfil eliminado correctamente");
							$window.location.reload();
						})
			}
			
			$scope.cancelar = function(){
				$window.location.reload();
			}
	 
	 //Fin Perfil
		  });
		// Fin Auth
	 
	 })
		
	} ]);