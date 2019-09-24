app.service("printService",['$http', '$q','$window', function($http, $q,$window){

	this.printLote = function(id) {
		var d = $q.defer();
		$http.get("lote/print/"+id).then(
			function(response) {
				d.resolve(response.data);
			});
		return d.promise;
	}

	
}]);