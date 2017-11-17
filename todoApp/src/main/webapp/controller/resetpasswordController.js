var todoApp = angular.module('todoApp');

todoApp.controller('resetpasswordController', function($scope, resetpasswordService,
		$location) {
	$scope.resetPassword = function() {

		var httpReset = resetpasswordService.resetpasswordController($scope.user);

		httpReset.then(function(response) {

			if (response.data.status == 5) {
				$scope.response = 'Invalid OTP or email address';
			} else if (response.data.status == 5) {
				$scope.response = 'Password could not be updated';
			} else if (response.data.status == 2) {
				$location.path('login');
			}
		});
	}
});