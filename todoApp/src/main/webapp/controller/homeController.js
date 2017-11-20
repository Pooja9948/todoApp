var todoApp = angular.module('todoApp');

todoApp.controller('homeController', function($scope, homeService,$location) {

	$scope.homePage = function() {
		var httpServiceUser = homeService.homeuser($scope.user);
		
	}
	
	$scope.toggleSideBar = function() {
		
		var width = $('#sideToggle').width();
		console.log(width);
		if (width == '250') {
			document.getElementById("sideToggle").style.width = "0px";
			document.getElementById("content-wrapper-inside").style.marginLeft = "150px";
		} else {
			document.getElementById("sideToggle").style.width = "250px";
			document.getElementById("content-wrapper-inside").style.marginLeft = "300px";
		}
	}
	
});