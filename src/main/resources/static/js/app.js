(function () {
    'use strict';

    angular
        .module('app', ['ui.router','ui.bootstrap', 'ngFileUpload'])
        .config(config)
        .filter('soundcloudEmbedUrl', function ($sce) {
		    return function(trackId) {
		      return $sce.trustAsResourceUrl('https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/' + trackId);
		    };
		})
        .run(run);
   
   config.$inject = ['$stateProvider', '$urlRouterProvider', '$sceDelegateProvider'];
   function config($stateProvider, $urlRouterProvider, $sceDelegateProvider) {
	   
	   
	   $urlRouterProvider.otherwise('/');
	   $stateProvider
       .state('main', {
    	   		url: '/',
                controller: 'MainViewController',
                templateUrl: '/views/main.view.html',
                controllerAs: 'vm',
                module: 'public'
       });
	   
	   $sceDelegateProvider.resourceUrlWhitelist([
		       // Allow same origin resource loads.
		       'self',
		       // Allow loading from soundcloud. **.
		       'https://w.soundcloud.com/**']);
	   
	   
	   
   }
   
   

    run.$inject = ['$rootScope', '$state', '$http'];
    function run($rootScope, $state, $http) {
        

        
    }

})();