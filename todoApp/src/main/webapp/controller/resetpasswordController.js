var todoApp = angular.module('todoApp');

todoApp.controller('resetpasswordController', function($scope, resetpasswordService,$stateParams,
		$location) {
	
	console.log("inside resetpasswordcontroller")
	//var path = $location.path();
	//var lastIndex = path.substr(path.lastIndexOf("/") + 1);
	var token = $stateParams.token;
	console.log("last path "+token);
	
	$scope.resetPassword = function(user) {
		
		//alert(user.password);
		var httpReset = resetpasswordService.resetPassword(user);
		
		httpReset.then(function(response) {

			if (response.data.status == 500) {
				$scope.response = 'Invalid password or email address';
			} else if (response.data.status == -200) {
				$scope.response = 'Password could not be updated';
			} else if (response.data.status == 200) {
				$location.path('login');
			}
		});
	}
});