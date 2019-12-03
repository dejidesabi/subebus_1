var app=angular.module("app",['ngRoute', 'ngCookies',]);
app.config([ '$routeProvider','$httpProvider', function($routeProvider,$httpProvider) {
	$httpProvider.defaults.headers.common = {'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Method': 'GET'};
    $httpProvider.defaults.headers.post = {};
    $httpProvider.defaults.headers.put = {};
    $httpProvider.defaults.headers.patch = {};
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
    $httpProvider.defaults.headers.common["Accept"] = "application/json";
    $httpProvider.defaults.headers.common["Content-Type"] = "application/json";
	$routeProvider.when('/login', {
		templateUrl : "pages/login.html",
		controller : "navigation"
	});
	
	
	$routeProvider.when('/Principal', {
		templateUrl : "pages/dashboard.html",
		controller : "dashboardController"
	});
	$routeProvider.when('/Lotes', {
		templateUrl : "pages/lotes.html",
		controller : "loteController"
	});
	$routeProvider.when('/Membresia', {
		templateUrl : "pages/membresia.html",
		controller : "membresiaController"
	});
	$routeProvider.when('/Ruta', {
		templateUrl : "pages/ruta.html",
		controller : "rutaController"
	});
	$routeProvider.when('/Sucursal', {
		templateUrl : "pages/sucursal.html",
		controller : "sucursalController"
	});
	$routeProvider.when('/Usuario', {
		templateUrl : "pages/usuario.html",
		controller : "usuarioController"
	});
	$routeProvider.when('/Venta', {
		templateUrl : "pages/venta.html",
		controller : "ventaController"
	});
	$routeProvider.when('/Reporte', {
		templateUrl : "pages/reporte.html",
		controller : "reporteController"
	});
	$routeProvider.when('/Perfil', {
		templateUrl : "pages/perfil.html",
		controller : "controladorListaPerfiles"
	});
		$routeProvider.otherwise({
		redirectTo : '/Principal'
	});
}]);

app.factory("userFactory", function(){
	var usuarioFirmado={usuario:"", pass:"",perfil:""};
	
	    var respuesta={
	    	getUsuarioFirmado: function(){
	            return usuarioFirmado;
	        },
	        setUsuarioFirmado: function(user){
	        	usuarioFirmado = user;
//	        	console.log("EL Usuario",usuarioFirmado)
	        },
	        getUsuarioPerfil: function(){
	            return usuarioFirmado.perfil;
	        },
	    }
	return respuesta;
});

app.service('sessionService', [
	'$rootScope',
	'$http',
	'$window',
	'$location',
	'$q',
	'userFactory',
	'$cookieStore',
	function($rootScope,$http, $window,$location, $q,userFactory,$cookieStore) {
		this.authenticate = function(credentials, callback) {

			var headers = credentials ? {
				authorization : "Basic"
						+ btoa(credentials.username + ":"
								+ credentials.password)
			} : {};
			$http.get('/user', {
				headers : headers
			}).success(function(data) {
				userFactory.setUsuarioFirmado(data);
				if (data.usuario) {
					$rootScope.authenticated = true;
					$rootScope.variable = true;
					
//					$http.get("/notificacion/numAlertas/"+ data.id).then(function(response){
//						$rootScope.numNotificaciones=response.data;
//					})
					$('#mdlLogin').modal('hide');
					$location.path("/Principal");
					//$location.reload();
				
				} else {
					$rootScope.authenticated = false;
				}
			}).error(function(data) {
				alert("Usuario o Contraseña incorrectos");
//				$scope.credentials.password = ""
				$location.path("/login");
			});
		}
		this.consultarPerfilesTodos = function() {
			var d = $q.defer();
			$http.get("/perfil/getAll").then(function(response) {
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
		this.reset=function(data){
			var d = $q.defer();
			$http.post("/usuario/reset/",data).then(
				function(response) {
					d.resolve(response.data);
				});
			return d.promise;
		}
		this.isAuthenticated = function() {
			var d = $q.defer();
			$http.get("currentSession").success(function(data) {
				$rootScope.UserData=data;
				$cookieStore.put("usuario", data.usuario);
				$cookieStore.put("perfil", data.perfil);
//				console.log("Solo el Usuario a cookie",data.usuario);
				$cookieStore.put("idSucursal", data.idSucursal);
				$rootScope.authenticated = true;
				
				d.resolve(data);
			}).error(function(data) {
				$location.path("/login");
			});
			
			return d.promise;
		}

} ]);

app.controller('navigation', [ 'sessionService','$window', '$rootScope', '$scope','$http', '$location','userFactory',
	function(sessionService, $rootScope, $scope, $http, $location,userFactory) {
		$scope.credentials = {};
		$('#mdlLogin').modal('show');
		
		$scope.login = function() {
			sessionService.authenticate($scope.credentials, function() {
				if ($rootScope.authenticated) {
					$scope.error = false;
					$location.path("/Principal");
					$('#mdlLogin').modal('dimiss');
					location.reload();
				} else {
					
					$location.path("/login");
					$scope.error = true;
				}
			});
		};
		$('html, body').animate({scrollTop:0}, 'slow');
		$scope.restablecer=function(email){
			sessionService.reset(email).then(function(data){
			
					alert("Correo enviado correctamenta para restablecer su contrase\u00f1a");
					location.reload();
					
		//			setTimeout(window.location.reload.bind(window.location), 1000);
				
			
		});}
} ]);

app.run(['$rootScope','$http','sessionService','userFactory',function ($rootScope,$http,sessionService,userFactory) {
	sessionService.isAuthenticated().then(function(data){
		var us= data;
		$rootScope.UserData=data;
	
		userFactory.setUsuarioFirmado(us);
		$rootScope.perfilUsuario=userFactory.getUsuarioPerfil();
		$rootScope.titulo = "Sistema Run";
		
		 sessionService.isAuthenticated().then(function(sesion) {
			  
			  sessionService.consultarPerfilesTodos().then(function(data) {
				  
				  for(i in data){
					  if(sesion.perfil == data[i].tipo){
						 $rootScope.autorizacion = data[i].permisos;
						 console.log($rootScope.autorizacion);
					  }
				  }
			  });
		 });
//		$http.get("/notificacion/numAlertas/"+ us.id).then(function(response){
//			$rootScope.numNotificaciones=response.data;
//		})
	});
}]);