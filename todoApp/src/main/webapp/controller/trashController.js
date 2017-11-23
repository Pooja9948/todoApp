/*todoApp.controller('trashController',function($scope, homeService,$uibModal, $location, dataStore, $rootScope){
	
	var test = {};
	$scope.margin = 0;
	$scope.view = 'grid';
	$scope.notes = {};
	var modalInstance;
	var httpGetNotes = homeService.getNotes("TRASH");
	$scope.deleteNote = function(id){
		var deleteNote = notesService.deleteNotes(id);
		modalInstance.close('resetModel');
		deleteNote.then(function(response){
			httpGetNotes;
			
		});
}
});*/