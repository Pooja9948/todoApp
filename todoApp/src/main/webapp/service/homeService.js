var todoApp = angular.module('todoApp');

todoApp.factory('homeService', function($http, $location, $state) {
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
	// ADD NOTE
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

	// Update Note
	homePage.updateNote = function(note) {
		console.log(note);
		return $http({
			method : "PUT",
			url : 'user/updateNote',
			headers : {
				'token' : localStorage.getItem('token')
			},
			data : note
		})
	}
	// GET USER
	homePage.getUser = function() {
		return $http({
			method : "GET",
			url : 'user/currentUser',
			headers : {
				'token' : localStorage.getItem('token')
			}
		})
	}
	homePage.delAllNote = function() {
		return $http({
			method : "DELETE",
			url : 'user/delAllNotes',
			headers : {
				'token' : localStorage.getItem('token')
			}
		})
	}
	// CHANGE USER PROFILE
	homePage.changeProfile = function(User) {
		return $http({
			method : "POST",
			url : 'user/profileChange',
			headers : {
				'token' : localStorage.getItem('token')
			},
			data : User
		})
	}
	homePage.service = function(url, method, token, note) {

		return $http({

			method : method,
			url : url,
			data : note,
			headers : {
				'token' : token
			}
		});
	}
	/* FOR LABELS */
	
	
	
	homePage.getLabelNotes = function(labelName) {
		return $http({
			method : "GET",
			url : "user/getLabelNotes/" + labelName,
			headers : {
				'token' : localStorage.getItem('token')
			}
		})
	}
	homePage.saveLabel = function(label) {
		return $http({
			method : "POST",
			url : 'user/saveLabel',
			headers : {
				'token' : localStorage.getItem('token')
			},
			data : label
		})/*
			 * .then(function(response){ },function(response){
			 * if(response.status=='400') $location.path('/loginPage'); });
			 */
	}
	/*homePage.getLabels = function() {
		return $http({
			method : "GET",
			url : 'user/getLabels',
			headers : {
				'token' : localStorage.getItem('token')
			},

		})
			 * .then(function(response){ },function(response){
			 * if(response.status=='400') $location.path('/loginPage'); });
			 
	}*/
	homePage.deleteLabel = function(label) {
		return $http({
			method : "DELETE",
			url : 'user/deleteLabels/'+label.labelId,
			headers : {
				'token' : localStorage.getItem('token')
			}
		})
	}
	homePage.removeLabel = function(note) {
		return $http({
			method : "DELETE",
			url : 'user/removeLabel/'+note.id,
			headers : {
				'token' : localStorage.getItem('token')
			}
		})
	}
	homePage.editLabel = function(label) {
		return $http({
			method : "POST",
			url : 'user/editLabel/'+label,
			headers : {
				'token' : localStorage.getItem('token')
			}
		})
	}
	homePage.editNotes = function(label) {
		return $http({
			method : "POST",
			url : 'user/editLabels',
			headers : {
				'token' : localStorage.getItem('token')
			},
			data : notes,
		}).then(function(response) {
			console.log("edited notes:-");
			console.log(response.data);
		}, function(response) {
			console.log("error" + response.data.myResponseMessage);

		});
	}
	homePage.getLabelNotes = function(labelName) {
		return $http({
			method : "GET",
			url : "user/getLabelNotes/" + labelName,
			headers : {
				'token' : localStorage.getItem('token')
			}
		})
	}
	return homePage;
});