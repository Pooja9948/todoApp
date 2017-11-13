var todoApp = angular.module('todoApp');

todoApp.controller('loginController', function($scope, loginService,$location) {

	$scope.user = {};
	$scope.loginUser = function() {
		
		var loginVariable = loginService.loginuser($scope.user);

		loginVariable.then(function(response) {
			$location.path('/home')
		}/*,function(response){
			$scope.errorMessage=response.data.message;
		}*/);
	}
});