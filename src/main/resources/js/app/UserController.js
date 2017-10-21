'use strict';

angular.module('MusicWeb').controller('UserController',
    ['UserService', '$scope',  function( UserService, $scope) {

        var self = this;
        self.user = {};
        self.users=[];

        self.submit = submit;
        self.getAllUsers = getAllUsers;
        self.createOrUpdateUser = createOrUpdateUser;
        self.deleteUserById = deleteUserById;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            console.log('Submitting');
            if (self.user.id === undefined || self.user.id === null) {
                console.log('Saving New User', self.user);
                createOrUpdateUser(self.user);
            }
        }

        function createOrUpdateUser(user) {
            console.log('About to create user');
            UserService.createOrUpdateUser(user)
                .then(
                    function (response) {
                        console.log('User created successfully');
                        self.successMessage = 'User created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.user={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating User');
                        self.errorMessage = 'Error while creating User: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function deleteUserById(userId){
            console.log('About to remove User with id '+userId);
            UserService.deleteUserById(userId)
                .then(
                    function(){
                        console.log('User '+userId + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing user '+userId +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllUsers(){
            return UserService.getAllUsers();
        }

        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.user={};
            $scope.myForm.$setPristine(); //reset Form
        }
    }
    ]);