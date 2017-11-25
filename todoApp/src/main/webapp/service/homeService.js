var todoApp = angular.module('todoApp');

todoApp.factory('homeService', function($http, $location ,$state) {
	var homePage = {};

	homePage.homeuser = function(user) {
		console.log(user.email);
		return $http({
			method : "GET",
			url : 'home',
			data : user
		});
	}

	// cards.notes = [];
	homePage.saveNotes = function(note) {
		console.log("homeservice");
		console.log("jaskdhf " + note);
		return $http({
			method : "POST",
			url : 'user/createNote',

			data : note,
			headers : {
				'token' : localStorage.getItem('token')
			}
		})
	}
	// GET ALL NOTES
	homePage.getAllNotes = function() {
		console.log("get all notes");
		return $http({
			method : "GET",
			url : 'user/getAllNotes',
			headers : {
				'token' : localStorage.getItem('token')
			}
		})
	}
	// DELETE NOTE
	homePage.deleteNotes = function(note) {
		console.log("inside delete function;-" + note.id);
		return $http({
			method : "DELETE",
			url : 'user/deleteNote/' + note.id,
			headers : {
				'token' : localStorage.getItem('token')
			}
		}).then(function(response) {
			console.log("response message" + response.data);
		}, function(response) {
			if (response.status == '400')
				$location.path('/loginPage')
			console.log("error" + response.data.myResponseMessage);
		});
	}
	
	//pin edit
	homePage.updateNote=function(note){
		console.log(note);
		return $http({
			method : "PUT",
			url : 'user/updateNote',
			headers: {
				'token':localStorage.getItem('token')
			},
			data: note
		})
	}
	return homePage;
});