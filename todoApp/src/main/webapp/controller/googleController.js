todoApp.controller('googleController', function($http, $location) {
	$http({
		method : "GET",
		url : 'tokenAftergLogin'
	}).then(function(response) {
		console.log("google resp")
		console.log(response.data.responseMessage);
		localStorage.setItem("token", response.data.message);
		$location.path('home');
	}, function(response) {
		console.log(response.data.responseMessage);
	});
});	