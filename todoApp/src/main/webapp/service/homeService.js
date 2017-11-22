var todoApp = angular.module('todoApp');

todoApp.factory('homeService', function($http, $location) {
	var homePage = {};

	homePage.homeuser = function(user) {
		console.log(user.email);
		return $http({
			method : "GET",
			url : 'home',
			data : user
		});
	}

	
	//cards.notes = [];
	homePage.saveNotes = function(note) {
		console.log("homeservice");
		console.log("jaskdhf "+note);
		return $http({
			method : "POST",
			url : 'user/createNote',
			headers :{
				'token' : localStorage.getItem('token')
			},
			data : note
		})
	}
	//GET ALL NOTES
	homePage.getAllNotes = function() {
		console.log("get all notes");
		return $http({
			method : "GET",
			url : 'user/getAllNotes',
			headers :{
				'token' : localStorage.getItem('token')
			}
		})
	}
	return homePage;
});