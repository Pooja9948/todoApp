var todoApp = angular.module('todoApp');

todoApp
		.controller(
				'homeController',
				function($scope, homeService, $location, $state, $uibModal,fileReader,
						$filter, $interval, toastr) {

					var addNote = {};
					$scope.note = {};
					$scope.note.description = '';
					$scope.note.title = '';
					$scope.labels = {};
					$scope.newLabel = '';
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
					

					/* REMINDER */
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
					
					var path = $location.path();
					var labelName = path.substr(path.lastIndexOf("/") + 1);

					// GET ALL NOTES
					// function getNotes() {
					var httpNotes = homeService.getAllNotes();

					httpNotes.then(function(response) {
						if (response.data.status == '511') {
							$location.path('/login')
						} else {
							$scope.notes = response.data;
							if($state.current.name == "labels"){
								
								//alert(labelName);
								$scope.topBarColor = "#3e50b4";
								$scope.navBarHeading = labelName;
								var tempNotes=[];
								for(var i=0; i < $scope.notes.length; i++){
									var labels = $scope.notes[i].labels;
									for(var j=0; j < labels.length; j++){
										if(labels[j].labelName==labelName){
											tempNotes.push($scope.notes[i]);
											//alert('yhyj');
										}
									}
								}
								console.log(tempNotes);
								$scope.notes=tempNotes;
							}
							
							console.log(response.data);
							homeService.notes = response.data;
						}
					});
					// }
					
					
					
					function getNotes() {
						var httpNotes = homeService.getAllNotes();
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
						console.log("save label " + label);
						var data = {};
						if (label === undefined) {
							data.labelName = $scope.newLabel;
						} else {
							data.labelName = label.labelName;
						}
						console.log("save label data " + label);
						var saveLabel = homeService.saveLabel(data);
						saveLabel.then(function(response) {
							getlabels();
							$scope.labels = response.data;
						}, function(response) {
							if (response.status == '400')
								$location.path('/loginPage')
						});
					}

					$scope.toggleLabelOfNote = function(note, label) {
						console.log('clicked');
						var index = -1;
						var i = 0;
						for (i = 0, len = note.labels.length; i < len; i++) {
							if (note.labels[i].labelName === label.labelName) {
								index = i;
								break;
							}
						}

						if (index == -1) {
							note.labels.push(label);
						} else {
							note.labels.splice(index, 1);
						}
					}

					$scope.editLabel = function(label) {
						console.log("edit labbbbeeeelll"+label);
						homeService.editLabel(label);
						getlabels();
					}
					/*$scope.removeLabel = function(note) {
						console.log($scope.file);
						console.log("remove labbbbeeeelll"+note);
						homeService.removeLabel(note);
						getlabels();
					}*/
					$scope.deleteLabel = function(label) {
						console.log("labbbbeeeelll"+label.labelId);
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
					$scope.removeLabel = function (note, item) {
				    	$scope.note = note;
				    	var comparator = angular.equals;
				        if (angular.isArray($scope.note.labels)) {
				          for (var i = $scope.note.labels.length; i--;) {
				            if (comparator($scope.note.labels[i],item)) {
				            	$scope.note.labels.splice(i, 1);
				              break;
				            }
				          }
				        }
				      homeService.updateNote(note);
					}
					
					//alert(labelName);
					getlabels();
					function getlabels() {
						var httpGetLabels = homeService
								.getLabelNotes(labelName);
						httpGetLabels.then(function(response) {
							console.log("response data "+response.data);
							$scope.labels = response.data;
						}, function(response) {
							if (response.status == '400')
								$location.path('/loginPage')
						});
					}

					/* logout user */
					$scope.logout = function() {
						localStorage.removeItem('token');
						$location.path('/login');
					}

					// Image uploader

					$scope.imageSrc = "";

					$scope.$on("fileProgress", function(e, progress) {
						$scope.progress = progress.loaded / progress.total;
					});

					$scope.openImageUploader = function(type) {
						$scope.type = type;
						$('#imageuploader').trigger('click');
					}

					$scope.changeProfile = function(user) {

						var a = homeService.changeProfile(user);

						a.then(function(response) {

						}, function(response) {

						});
					}

					$scope.removeImage = function() {
						$scope.AddNoteBox = false;
						$scope.addimg = undefined;
					}

					$scope.type = {};
					$scope.type.noteImage = '';

					$scope
							.$watch(
									'imageSrc',
									function(newimg, oldimg) {
										if ($scope.imageSrc != '') {
											if ($scope.type === 'input') {
												$scope.addimg = $scope.imageSrc;
											} else if ($scope.type === 'user') {
												$scope.User.profileImage = $scope.imageSrc;
												$scope
														.changeProfile($scope.User);
											} else {
												$scope.type.noteImage = $scope.imageSrc;
												$scope.updateNote($scope.type);
											}
										}
									});

				});