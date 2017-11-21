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

	var cards = {};
	cards.notes = [];
	cards.saveNotes = function(note) {
		console.log("homeservice");
		console.log("jaskdhf "+note);
		return $http({
			method : "POST",
			url : 'user/createNote',
			/*headers :{
				'Authorization' : 'todoBearer ' + localStorage.getItem('todoAccessToken')
			},*/
			data : note
		})
	}
	//GET ALL NOTES
	/*cards.getAllNotes = function() {
		console.log("get all notes");
		return $http({
			method : "GET",
			url : 'user/getallnotes',
			headers :{
				'Authorization' : 'todoBearer ' + localStorage.getItem('todoAccessToken')
			}
		})
	}*/
	return homePage;
});