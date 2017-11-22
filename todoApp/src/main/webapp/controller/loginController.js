var todoApp = angular.module('todoApp');

todoApp.controller('loginController', function($scope, loginService,$location) {

	$scope.user = {};
	$scope.loginUser = function() {
		
		var loginVariable = loginService.loginuser($scope.user);
		console.log($scope.user.email);
		loginVariable.then(function(response) {
			console.log("AccessToken: " + response.data.message);
			localStorage.setItem("token", response.data.message);
			console.log(localStorage.getItem("token"));
			$location.path('/home')
		}/*,function(response){
			$scope.errorMessage=response.data.message;
		}*/);
	}
});