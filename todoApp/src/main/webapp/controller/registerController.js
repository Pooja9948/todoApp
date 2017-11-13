var todoApp = angular.module('todoApp');

todoApp.controller('registerController', function($scope, registerService,$location) {

	$scope.user = {};
	$scope.registerUser = function() {
		
		var regVariable = registerService.registeruser($scope.user);

		regVariable.then(function(response) {
			$location.path('/login')
		}/*,function(response){
			$scope.errorMessage=response.data.message;
		}*/);
	}
});