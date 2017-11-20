var todoApp = angular.module('todoApp');

todoApp.directive('sideBar', function() {
	return {
		templateUrl : 'template/sidebar.html'
	}
});

todoApp.directive('topBar', function() {
	return {
		templateUrl : 'template/header.html'	
	}
});