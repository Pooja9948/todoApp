var todoApp = angular.module('todoApp');

todoApp
		.controller(
				'homeController',
				function($scope, homeService, $location) {

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
						//console.log('yes00'+$scope.note.title);
						$scope.note = {};
						/*
						 * newNote.isArchived = isArchived; newNote.color =
						 * $('#actual-input-div').css( "background-color");
						 * 
						 * newNote.reminder = $scope.newNoteReminder;
						 * newNote.image = $scope.newNoteImg;
						 * 
						 * newNote.labels = $scope.newNote.labels;
						 * 
						 * newNote.sharedUsers = $scope.n.sharedUsers;
						 * 
						 * if ($scope.pinSrc == 'images/bluepin.svg' &&
						 * !isArchived) { newNote.isPinned = true; } else {
						 * newNote.isOthersPinned = false; }
						 */

						var httpCreateNote = homeService.saveNotes($scope.note);

						/*
						 * httpCreateNote.then(function() {
						 * $('#note-title-input').html('');
						 * $('#note-body-input').html('');
						 * $('#actual-input-div').css("background-color",
						 * 'white'); $scope.pinSrc = 'images/pin.svg';
						 * $scope.showInput = false; $scope.newNoteImg = '';
						 * $scope.newNote = {labels : []}; setInputLabel();
						 * console.log('new note image' + $scope.newNoteImg); if
						 * (stateName != 'reminder') { $scope.newNoteReminder =
						 * undefined; }
						 * 
						 * getNotes(); }, function(response) {
						 */
						/*
						 * if (response.data.status == '511') {
						 * $location.path('/loginpage') }
						 */
						//});
					}
					
					
					//GET ALL NOTES
//					function getNotes() {
						var httpNotes = homeService.getAllNotes();

						httpNotes.then(function(response) {
							if (response.data.status == '511') {
								$location.path('/login')
							} else {
								console.log(response.data.notes);
								$scope.notes = response.data.notes;
								homeService.notes = response.data.notes;
							}
						});
//					}

				});