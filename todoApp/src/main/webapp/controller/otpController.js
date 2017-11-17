var todoApp = angular.module('todoApp');

todoApp.controller('otpController', function($scope, otpService,$location) {

	$scope.user = {};
	$scope.otpUser = function() {
		
		console.log("User details : "+$scope.otp)
		
		var regVariable = otpService.otpuser($scope.otp);
		
		regVariable.then(function(response) {
			$location.path('/resetpassword')
		}/*,function(response){
			$scope.errorMessage=response.data.message;
		}*/);
	}
});