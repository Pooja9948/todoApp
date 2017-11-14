var todoApp = angular.module('todoApp');

todoApp.controller('forgotpasswordController', function($scope, forgotpasswordService,
		$location) {

	$scope.sendLink = function() {

		var httpSendLink = forgotpasswordService.sendLinkToEmail($scope.user);

		httpSendLink.then(function(response) {

			if (response.data.status == 5) {
				$scope.response = 'User not found :';
			} else if (response.data.status == -2) {
				$scope.response = 'Mail could not be sent';
			} else if (response.data.status == 2) {
				$location.path('');
			}
		});
	}
});