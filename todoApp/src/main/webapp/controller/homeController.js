var todoApp = angular.module('todoApp');

todoApp
		.controller(
				'homeController',
				function($scope, homeService, $location, $state, $uibModal) {

					var addNote = {};
					$scope.note = {};
					$scope.note.description = '';
					$scope.note.title = '';
					var modalInstance;
					$scope.homePage = function() {
						var httpServiceUser = homeService.homeuser($scope.user);

					}

					$scope.toggleSideBar = function() {

						var width = $('#sideToggle').width();
						console.log(width);
						if (width == '250') {
							document.getElementById("sideToggle").style.width = "0px";
							document.getElementById("content-wrapper-inside").style.marginLeft = "270px";
						} else {
							document.getElementById("sideToggle").style.width = "250px";
							document.getElementById("content-wrapper-inside").style.marginLeft = "350px";
						}
					}

					//ADD COLOR

					$scope.AddNoteColor = "#ffffff";

					$scope.addNoteColorChange = function(color) {
						$scope.AddNoteColor = color;
					}

					$scope.colors = [

					{
						"color" : '#ffffff',
						"path" : 'image/white.png'
					}, {
						"color" : '#e74c3c',
						"path" : 'image/Red.png'
					}, {
						"color" : '#ff8c1a',
						"path" : 'image/orange.png'
					}, {
						"color" : '#fcff77',
						"path" : 'image/lightyellow.png'
					}, {
						"color" : '#80ff80',
						"path" : 'image/green.png'
					}, {
						"color" : '#99ffff',
						"path" : 'image/skyblue.png'
					}, {
						"color" : '#0099ff',
						"path" : 'image/blue.png'
					}, {
						"color" : '#1a53ff',
						"path" : 'image/darkblue.png'
					}, {
						"color" : '#9966ff',
						"path" : 'image/purple.png'
					}, {
						"color" : '#ff99cc',
						"path" : 'image/pink.png'
					}, {
						"color" : '#d9b38c',
						"path" : 'image/brown.png'
					}, {
						"color" : '#bfbfbf',
						"path" : 'image/grey.png'
					} ];

					if ($state.current.name == "home") {
						$scope.topBarColor = "#ffbb33";
						$scope.navBarHeading = "Fundoo Keep";
					} else if ($state.current.name == "archive") {
						$scope.topBarColor = "#669999";
						$scope.navBarHeading = "Archive";
					} else if ($state.current.name == "trash") {
						$scope.topBarColor = "#636363";
						$scope.navBarHeading = "Trash";
					}else if ($state.current.name == "reminder") {
						$scope.topBarColor = "#669999";
						$scope.navBarHeading = "Reminder";
					}

					$scope.saveNotes = function() {

						$scope.title = $('#note-title-input').html();
						$scope.description = $('#note-body-input').html();
						addNote.title = $scope.note.title;
						addNote.description = $scope.note.description;
						var httpCreateNote = homeService.saveNotes(addNote);

					}

					//MAKE A COPY
					$scope.copy = function(note) {
						note.pin = "false";
						note.archived = "false";
						note.trash = "false";
						var a = homeService.saveNotes(note);
						a.then(function(response) {
							getNotes();
						}, function(response) {
						});
					}

					//delete notes
					$scope.deleteNotes = function(note) {
						console.log("note id" + note.id);
						var deleteNote = homeService.deleteNotes(note);
						deleteNote.then(function(response) {
							getNotes();
						}), then(function(response) {
							console.log(response);
						});
					}
					//UPDATE PIN
					$scope.updateNote = function(note) {
						console.log(note);
						var a = homeService.updateNote(note);
						a.then(function(response) {
							getNotes();
						}, function(response) {
						});
					}
					/*OPEN  NOTE*/
					$scope.showModal = function(note) {
						$scope.note = note;
						modalInstance = $uibModal.open({
							templateUrl : 'template/newNote.html',
							scope : $scope,
							size : 'md'
						});
					};

					//GET ALL NOTES
					//function getNotes() {
					var httpNotes = homeService.getAllNotes();

					httpNotes.then(function(response) {
						if (response.data.status == '511') {
							$location.path('/login')
						} else {
							console.log(response.data);
							$scope.notes = response.data;
							homeService.notes = response.data;
						}
					});
					//}
					function getNotes() {
						httpNotes.then(function(response) {
							if (response.data.status == '511') {
								$location.path('/login')
							} else {
								console.log(response.data);
								$scope.notes = response.data;
								homeService.notes = response.data;
							}
						});
					}

					/*archive notes*/
					$scope.archiveNote = function(note) {
						note.archived = true;
						note.pin = false;
						var a = homeService.updateNote(note);

						a.then(function(response) {
							getNotes();
						}, function(response) {
						});
					}

					/*unarchive notes*/
					$scope.unarchiveNote = function(note) {
						note.archived = false;
						note.pin = false;
						var a = homeService.updateNote(note);
						a.then(function(response) {
							getNotes();
						}, function(response) {
						});
					}

					/*trash notes*/
					$scope.trashNote = function(note) {
						note.archived = false;
						note.pin = false;
						note.trash = true;
						var a = homeService.updateNote(note);

						a.then(function(response) {
							getNotes();
						}, function(response) {
						});
					}

					/*restore notes*/
					$scope.restoreNote = function(note) {
						note.archived = false;
						note.pin = false;
						note.trash = false;
						var a = homeService.updateNote(note);
						a.then(function(response) {
							getNotes();
						}, function(response) {
						});
					}
/*
					SOCIAL SHARE
					$scope.fbAsyncInit = function(note) {
						FB.init({
							appId : '1490675564380779',
							status : true,
							cookie : true,
							xfbml : true,
							version : 'v2.4'
						});
					};

					FB.ui({
						method : 'share_open_graph',
						action_type : 'og.likes',
						action_properties : JSON.stringify({
							object : {
								'og:title' : note.title,
								'og:description' : note.description
							}
						})
					},
					// callback
					function(response) {
						if (response && !response.error_message) {
							alert('Posting completed.');
						} else {
							alert('Error while posting.');
						}
					});*/
				});