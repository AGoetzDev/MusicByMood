(function () {
    'use strict';

    angular
        .module('app')
        .controller('MainViewController', MainViewController);

    MainViewController.$inject = ['$state', '$scope', 'Upload', '$timeout', 'ImageService', '$http', '$anchorScroll', '$location'];
    function MainViewController($state, $scope, Upload, $timeout, ImageService, $http, $anchorScroll, $location) {
        var vm = this;
		vm.uploadPic = uploadPic;
		vm.playTrack = playTrack;
		vm.file ={};
		vm.emotion = {};
		vm.playlist = [];
		vm.currentTrackIndex;
		vm.widget;
		
		
		
		
		
		function uploadPic(file) {
			vm.widget = {};
			vm.currentTrackIndex = 0;
			vm.emotion = {};
			vm.playlist = [];
			vm.file = file;
			ImageService.scaleImage(file, function(imageData){
				
				$http({
					url: '/api/mood',
					method: 'POST',
					transformRequest: function(obj) {
						var str = [];
						for(var p in obj)
						str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
						return str.join("&");
					},
					data: {dataUrl: imageData},
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded'
					}
				}).then(function successCallback(response) {
					vm.emotion = response.data.data.emotion;
					vm.playlist = response.data.data.tracks;
					setupSCWidget();
				}, function errorCallback(response) {
					if (response.status > 0){
						vm.errorMsg = response.status + ': ' + response.data;
					}
				}, function (evt) {
					  // Math.min is to fix IE which reports 200% sometimes
					  file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
				});
				
			});
			
			
			

		}
		
		function goToAnchor(id){
			var newHash = id;
            var old = $location.hash();
            if ($location.hash() !== newHash) {
                $location.hash(id);
                $anchorScroll(newHash);
                $location.hash(old);
            } else {
                $anchorScroll(newHash);
                $location.hash(old);
            }
		}
		
		function setupSCWidget(){
			
			vm.currentTrackIndex = 0;
			var widgetIframe = document.getElementById('sc-widget'),
	        widget       = SC.Widget(widgetIframe);
			console.log(widgetIframe);
			var newSoundUrl = 'http://api.soundcloud.com/tracks/'+vm.playlist[vm.currentTrackIndex].idOnPlatform;
			widget.bind(SC.Widget.Events.READY, function() {
			      // load new widget
				  console.log("AAA");
				 
			      widget.bind(SC.Widget.Events.FINISH, function() {
			    	 
			    	vm.currentTrackIndex = vm.currentTrackIndex +1;
			    	
			    	if(vm.currentTrackIndex === vm.playlist.length){
			    		vm.currentTrackIndex = 0;
			    	}
			    	var newSoundUrl = 'http://api.soundcloud.com/tracks/'+vm.playlist[vm.currentTrackIndex].idOnPlatform;
			        widget.load(newSoundUrl, {
			          show_artwork: true,
			          auto_play: true
			        });
			      });
			      
			      widget.load(newSoundUrl, {
			          show_artwork: true
			       });
			      vm.widget = widget;
			      goToAnchor("playlist");
			      
			 });
			
	        
		}
		
		function playTrack(index){
			vm.currentTrackIndex = index;
			vm.widget.load('http://api.soundcloud.com/tracks/'+vm.playlist[vm.currentTrackIndex].idOnPlatform, {
		          show_artwork: true,
		          auto_play: true
		        });
		}

        

        
       
    }

})();