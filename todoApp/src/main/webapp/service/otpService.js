var todoApp = angular.module('todoApp');

todoApp.factory('otpService', function($http) {
	var otp = {};
	
	otp.otpuser = function(user) {
		//console.log("User details : "+user.email);
		return $http({
			method : "POST",
			url : 'otp',
			data : user
		});
	}
	return otp;
});