var todoApp = angular.module('todoApp');

todoApp
		.controller(
				'homeController',
				function($scope, homeService, $location, $state, $uibModal,
						toastr) {

					var addNote = {};
					$scope.note = {};
					$scope.note.description = '';
					$scope.note.title = '';
					/*$scope.editLabelFocus = false;
					$scope.labels = {};
					$scope.newLabel = '';
					var labelName = path.substr(path.lastIndexOf("/") + 1);*/
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

					// ADD COLORdelReminder(note)

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
					} else if ($state.current.name == "reminder") {
						$scope.topBarColor = "#669999";
						$scope.navBarHeading = "Reminder";
					} else if ($state.current.name == "searchbar") {
						$scope.topBarColor = "#3e50b4";
						$scope.navBarHeading = "Search";
					}

					/* FOR SEARCH BAR */
					$scope.searchbar = function() {
						modalInstance = $uibModal.open({
							templateUrl : 'template/searchbar.html',
							scope : $scope,
							size : 'md'
						});
					};

					/*REMINDER*/
					$scope.AddReminder = '';
					$scope.openAddReminder = function() {
						$('#datepicker').datetimepicker();
						$scope.AddReminder = $('#datepicker').val();
					}

					$scope.reminder = "";
					$scope.openReminder = function(note) {
						$('.reminder').datetimepicker();
						var id = '#datepicker' + note.id;
						$scope.reminder = $(id).val();

						if ($scope.reminder === ""
								|| $scope.reminder === undefined) {
							console.log(note);
							console.log($scope.reminder);
						} else {
							console.log($scope.reminder);
							note.reminder = $scope.reminder;
							$scope.updateNote(note);
							$scope.reminder = "";
						}
					}

					$scope.removeReminder = function(note) {
						console.log($scope.file);
						note.reminder = null;
						$scope.updateNote(note);
					}
					/* SAVE NOTE */
					$scope.saveNotes = function() {

						$scope.title = $('#note-title-input').html();
						$scope.description = $('#note-body-input').html();
						addNote.title = $scope.note.title;
						addNote.description = $scope.note.description;
						var httpCreateNote = homeService.saveNotes(addNote);
						httpCreateNote.then(function(response) {
							getNotes();
							toastr.success('You have created a note',
									'check it out');
						}, function(response) {
						});
					}

					// MAKE A COPY
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

					// delete note
					$scope.deleteNotes = function(note) {
						console.log("note id" + note.id);
						var deleteNote = homeService.deleteNotes(note);
						deleteNote.then(function(response) {
							getNotes();
						}), then(function(response) {
							console.log(response);
						});
					}

					/* DELETE ALL NOTES */
					$scope.delAllNote = function() {
						var deleteNote = homeService.deleteAllNotes();
						deleteNote.then(function(response) {
							getNotes();
						}), then(function(response) {
							console.log(response);
						});
					}
					// UPDATE PIN
					$scope.updateNote = function(note) {
						console.log(note);
						var a = homeService.updateNote(note);
						a.then(function(response) {
							getNotes();
						}, function(response) {
						});
					}
					/* OPEN NOTE */
					$scope.showModal = function(note) {
						$scope.note = note;
						modalInstance = $uibModal.open({
							templateUrl : 'template/newNote.html',
							scope : $scope,
							size : 'md'
						});
					};

					// GET ALL NOTES
					// function getNotes() {
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
					// }
					function getNotes() {
						httpNotes
								.then(function(response) {
									if (response.data.status == '511') {
										$location.path('/login')
									} else {
										console.log(response.data);
										$scope.notes = response.data;
										homeService.notes = response.data;
										$interval(
												function() {
													var i = 0;
													for (i; i < $scope.notes.length; i++) {
														if ($scope.notes[i].reminder != 'false') {

															var currentDate = $filter(
																	'date')
																	(
																			new Date(),
																			'MM/dd/yyyy h:mm a');
															if ($scope.notes[i].reminder === currentDate) {

																toastr
																		.success(
																				'You have a reminder for a note',
																				'check it out');
															}
														}
													}
												}, 9000);

									}
								});
					}

					/* archive notes */
					$scope.archiveNote = function(note) {
						note.archived = true;
						note.pin = false;
						var a = homeService.updateNote(note);

						a.then(function(response) {
							getNotes();
						}, function(response) {
						});
					}

					/* unarchive notes */
					$scope.unarchiveNote = function(note) {
						note.archived = false;
						note.pin = false;
						var a = homeService.updateNote(note);
						a.then(function(response) {
							getNotes();
						}, function(response) {
						});
					}

					/* trash notes */
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

					/* restore notes */
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

					// SOCIAL SHARE
					$scope.fbAsyncInit = function(note) {
						console.log(note.title);
						console.log('inside fbAsyncInit');
						FB.init({
							appId : '1490675564380779',
							status : true,
							cookie : true,
							xfbml : true,
							version : 'v2.4'
						});

						FB.ui({
							method : 'share_open_graph',
							action_type : 'og.likes',
							action_properties : JSON.stringify({
								object : {
									'og:title' : note.title,
									'og:description' : note.description
								}
							})
						}, function(response) {
							if (response && !response.error_message) {
								alert('Posting completed.');
							} else {
								alert('Error while posting.');
							}
						});
					};

					// LIST AND GRID VIEW
					$scope.ListView = true;

					$scope.ListViewToggle = function() {
						if ($scope.ListView == true) {
							$scope.ListView = false;
							listGrideView();
						} else {
							$scope.ListView = true;
							listGrideView();
						}
					}

					listGrideView();

					function listGrideView() {
						if ($scope.ListView) {
							var element = document
									.getElementsByClassName('card');
							for (var i = 0; i < element.length; i++) {
								element[i].style.width = "900px";
							}
						} else {
							var element = document
									.getElementsByClassName('card');
							for (var i = 0; i < element.length; i++) {
								element[i].style.width = "300px";
							}
						}
					}
					/* GET USER */
					getUser();

					function getUser() {
						var a = homeService.getUser();
						a.then(function(response) {
							$scope.User = response.data;
							console.log(response.data);
						}, function(response) {
							// console.log("Not Found");
						});
					}

					/* For Image 
					$scope.imageSrc = "";

					$scope.$on("fileProgress", function(e, progress) {
						$scope.progress = progress.loaded / progress.total;
					});

					$scope.openImageUploader = function(type) {
						$scope.type = type;
						$('#imageuploader').trigger('click');
					}*/

					/* COLLABORATOR */
					$scope.openCollboarate = function(note, user, index) {
						$scope.note = note;
						$scope.user = user;
						$scope.indexOfNote = index;
						modalInstance = $uibModal.open({
							templateUrl : 'template/collaborator.html',
							scope : $scope,
							size : 'md'
						});
					}
					/*
					 * $scope.showModal = function(note) { $scope.note = note;
					 * modalInstance = $uibModal.open({ templateUrl :
					 * 'template/newNote.html', scope : $scope, size : 'md' }); };
					 */
					$scope.getUserlist = function(note, user, index) {
						var obj = {};
						obj.noteId = note;
						obj.ownerId = user;
						obj.shareId = {};

						var url = 'user/collaborate';
						var token = localStorage.getItem('token');
						var userDetails = homeService.service(url, 'POST',
								token, obj);
						userDetails.then(function(response) {

							console.log(response.data);
							$scope.users = response.data;
							note.collabratorUsers = response.data;

						}, function(response) {
							$scope.user = {};

						});

						console.log(user);

					}

					$scope.collborate = function(note, user, index) {
						var obj = {};
						console.log(note);
						obj.noteId = note;
						obj.ownerId = user;
						obj.shareId = $scope.shareWith;

						var url = 'user/collaborate';
						var token = localStorage.getItem('token');
						var userDetails = homeService.service(url, 'POST',
								token, obj);
						userDetails.then(function(response) {

							console.log(response.data);
							$scope.users = response.data;
							note.collabratorUsers = response.data;

						}, function(response) {
							$scope.user = {};

						});

						console.log(user);

					}

					$scope.getOwner = function(note) {
						var url = 'user/getOwner';
						var token = localStorage.getItem('token');
						var user = homeService
								.service(url, 'POST', token, note);
						user.then(function(response) {

							$scope.owner = response.data;

						}, function(response) {
							$scope.users = {};
						});
					}

					$scope.removeCollborator = function(note, user, index) {
						var obj = {};
						var url = 'user/removeCollborator';
						obj.noteId = note;
						obj.ownerId = {
							'email' : ''
						};
						obj.shareId = user;
						var token = localStorage.getItem('token');
						var user = homeService.service(url, 'POST', token, obj);
						user.then(function(response) {
							$scope.collborate(note, $scope.owner);

							console.log(response.data);

						}, function(response) {
							console.log(response.data);

						});
					}

					/* note labels */
					$scope.saveLabel = function(label) {
						var data = {};
						if (label === undefined) {
							data.labelName = $scope.newLabel;
						} else {
							data.labelName = label.labelName;
						}
						var saveLabel = homeService.saveLabel(data);
						saveLabel.then(function(response) {
							getLabels();
							$scope.labels = response.data;
						}, function(response) {
							if (response.status == '400')
								$location.path('/loginPage')
						});
					}

					$scope.deleteLabel = function(label) {
						homeService.deleteLabel(label);
						getlabels();
					}
					$scope.openLabelList = function() {
						modalInstance = $uibModal.open({
							templateUrl : 'template/label-list.html',
							windowClass : 'app-modal-window',
							scope : $scope
						});
					}
					var path = $location.path();
					var labelName = path.substr(path.lastIndexOf("/")+1);
					/*getlabels();
					function getlabels() {
						var httpGetLabels = homeService.getLabels(labelName);
						httpGetLabels.then(function(response) {
							console.log(response.data);
							$scope.labels = response.data;
						}, function(response) {
							if (response.status == '400')
								$location.path('/loginPage')
						});
					}*/

						/*search
						$scope.searchText;
						$scope.doSearch = function() {
							dataStore.searchData($scope.searchText);
						}*/
					/* logout user */
					$scope.logout = function() {
						localStorage.removeItem('token');
						$location.path('/login');
					}

					//Image uploader
					$scope.openImageUploader = function(type) {
						$scope.type = type;
						$('#image').trigger('click');
						return false;
					}

					$scope.stepsModel = [];
					$scope.imageUpload = function(element) {
						var reader = new FileReader();
						reader.onload = $scope.imageIsLoaded;
						reader.readAsDataURL(element.files[0]);
					}
					$scope.imageIsLoaded = function(e) {
						$scope.$apply(function() {
							$scope.stepsModel.push(e.target.result);

							var imageSrc = e.target.result;
							$scope.type.image = imageSrc;

							// call upate service
							var archiveRequest = homeService
									.updateNote($scope.type);
							archiveRequest.then(function(response) {

								$state.reload();

							}, function(error) {

								console.log("Could not update note");
							});

						});
					}

					/*$scope.type = {};
					$scope.type.image = '';

					$scope.$watch('imageSrc', function(newimg, oldimg) {
						if ($scope.imageSrc != '') {
							if ($scope.type === 'input') {
								$scope.addimg = $scope.imageSrc;
							} else if ($scope.type === 'user') {
								$scope.User.profile = $scope.imageSrc;
								$scope.changeProfile($scope.User);
							} else {
								console.log();
								$scope.type.image = $scope.imageSrc;
								$scope.updateNote($scope.type);
							}
						}

					});*/

				});