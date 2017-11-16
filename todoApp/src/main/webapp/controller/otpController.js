var todoApp = angular.module('todoApp');

todoApp.controller('otpController', function($scope, otpService,$location) {

	$scope.user = {};
	$scope.otpUser = function() {
		
		console.log("User details : "+$scope.user.email)
		
		var regVariable = otpService.otpuser($scope.user);
		
		regVariable.then(function(response) {
			$location.path('/login')
		}/*,function(response){
			$scope.errorMessage=response.data.message;
		}*/);
	}
});