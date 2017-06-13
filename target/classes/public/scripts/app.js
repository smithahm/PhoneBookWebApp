var app = angular.module('contactapp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute',
    'xeditable'
]);

app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/main.html',
        controller: 'TabCtrl'
    }).when('/create', {
        templateUrl: 'views/create.html',
        controller: 'CreateCtrl'
    }).when('/createGroup', {
        templateUrl: 'views/createGroup.html',
        controller: 'GCreateCtrl'
    }).otherwise({
        redirectTo: '/'
    })
});

app.controller('CreateCtrl', function ($scope, $http, $location) {
    $scope.createContact = function () {
        console.log($scope.contact);
        $http.post('/api/v1/contacts', $scope.contact).success(function (data) {
            $location.path('/');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
    $scope.cancelContact = function () {
        console.log($scope.contact);
        $http.get('/api/v1/contacts').success(function (data) {
            $location.path('/');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }

    
});

app.controller('TabCtrl', function($scope, $http) {
	
	$http.get('/api/v1/contacts').success(function (data) {
        $scope.contacts = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    })
    
    $http.get('/api/v1/groups').success(function (data) {
        $scope.groups = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    })
    
    $scope.saveContact = function (body, contact) {
   	 $http.put('/api/v1/contacts/'+ contact.id, body).success(function (data) {
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
   }
   
    $scope.deleteContact = function (contact, index) {
        $http.delete('/api/v1/contacts/'+ contact.id).success(function (data) {
        	$scope.contacts.splice(index, 1);
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
    
    $scope.saveGroup = function (body, group) {
      	 $http.put('/api/v1/groups/'+ group.id, body).success(function (data) {
      		 console.log("record updated")
           }).error(function (data, status) {
               console.log('Error ' + data)
           })
      }
       
      $scope.deleteGroup = function (group, index) {
          $http.delete('/api/v1/groups/'+ group.id).success(function (data) {
          	$scope.groups.splice(index, 1);
          }).error(function (data, status) {
              console.log('Error ' + data)
          })
      }
      
      // need to work on adding to groups
      $scope.addToGroup = function (contact) {
          $http.put('/api/v1/groups/'+ "593e2f7785968ec112007404" + '/' + contact).success(function (data) {
           console.log("contact added")
          }).error(function (data, status) {
              console.log('Error ' + data)
          })
      }
});


app.controller('GCreateCtrl', function($scope, $http, $location) {
    
    $scope.createGroup = function () {
        console.log($scope.group);
        $http.post('/api/v1/groups', $scope.group).success(function (data) {
            $location.path('/');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
    
    $scope.cancel = function () {
        console.log($scope.group);
        $http.get('/api/v1/groups').success(function (data) {
            $location.path('/');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
    
});

app.directive('navigationTabs', function() {
  return {
    restrict: 'E',
    transclude: true,
    scope: {},
    controller: ['$scope', function MyTabsController($scope) {
      var panes = $scope.panes = [];

      $scope.select = function(pane) {
        angular.forEach(panes, function(pane) {
          pane.selected = false;
        });
        pane.selected = true;
      };

      this.addPane = function(pane) {
        if (panes.length === 0) {
          $scope.select(pane);
        }
        panes.push(pane);
      };
    }],
    templateUrl: 'views/navigation-tabs.html'
  };
})
.directive('navPane', function() {
  return {
    require: '^^navigationTabs',
    restrict: 'E',
    transclude: true,
    scope: {
      title: '@'
    },
    link: function(scope, element, attrs, tabsCtrl) {
      tabsCtrl.addPane(scope);
    },
    templateUrl: 'views/nav-pane.html'
  };
});
