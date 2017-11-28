var todoApp = angular.module('todoApp');

todoApp.controller('dummyController', function($scope, $http, $location) {

	$http({
		method : "GET",
		url : 'social',
	}).then(function(response) {
		localStorage.setItem('token',response.data.message);
		$location.path("home");
	})
});