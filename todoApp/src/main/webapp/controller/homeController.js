var todoApp = angular.module('todoApp');

todoApp.controller('homeController', function($scope, homeService,$location) {

	$scope.user = {};
	$scope.homeUser = function() {
		
		var homeVariable = homeService.homeuser($scope.user);
		console.log($scope.user.email);
		homeVariable.then(function(response) {
			$location.path('/home')
		}/*,function(response){
			$scope.errorMessage=response.data.message;
		}*/);
	}
});