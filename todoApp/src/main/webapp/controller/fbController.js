todoApp.controller('fbController', function($http, $location) {
	$http({
		method : "GET",
		url : 'tokenAftergFbLogin'
	}).then(function(response) {
		console.log(response.data.responseMessage);
		localStorage.setItem("token", response.data.message);
		$location.path('home');
	}, function(response) {
		console.log(response.data.responseMessage);
	});
});	