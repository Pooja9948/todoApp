var todoApp = angular.module('todoApp');

todoApp.factory('otpService', function($http) {
	var otp = {};
	
	otp.otpuser = function(otp) {
		//console.log("User details : "+user.email);
		return $http({
			method : "POST",
			url : 'otp',
			data : otp
		});
	}
	return otp;
});