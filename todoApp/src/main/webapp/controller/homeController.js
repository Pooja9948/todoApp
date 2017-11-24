var todoApp = angular.module('todoApp');

todoApp
		.controller(
				'homeController',
				function($scope, homeService, $location) {
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
							document.getElementById("content-wrapper-inside").style.marginLeft = "150px";
						} else {
							document.getElementById("sideToggle").style.width = "250px";
							document.getElementById("content-wrapper-inside").style.marginLeft = "300px";
						}
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
					$scope.updatePinup = function(note) {
						var a = homeService.updateNote(note);
						a.then(function(response) {
							getNotes();
						}, function(response) {
						});
					}
					/*OPEN  NOTE*/
					$scope.open = function(note) {
						$scope.note = note;
						modalInstance = $uibModal.open({
							templateUrl : 'template/newNote.html',
							scope : $scope
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

				});