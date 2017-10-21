'use strict';

angular.module('MusicWeb').factory('UserService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllUsers: loadAllUsers,
                getAllUsers: getAllUsers,
                getUserById: getUserById,
                createOrUpdateUser: createOrUpdateUser,
                deleteUserById: deleteUserById
            };

            return factory;

            function loadAllUsers() {
                console.log('Fetching all users');
                var deferred = $q.defer();
                $http.get(urls.USER_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all users');
                            $localStorage.users = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading users');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllUsers(){
                return $localStorage.users;
            }

            function getUserById(userId) {
                console.log('Fetching User with id :'+userId);
                var deferred = $q.defer();
                $http.get(urls.USER_SERVICE_API + userId)
                    .then(
                        function (response) {
                            console.log('Fetched successfully User with id :'+userId);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading user with id :'+userId);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createOrUpdateUser(user) {
                console.log('Creating User');
                var deferred = $q.defer();
                $http.post(urls.USER_SERVICE_API, user)
                    .then(
                        function (response) {
                            loadAllUsers();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while creating User : '+errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }


            function deleteUserById(userId) {
                console.log('Removing User with id '+userId);
                var deferred = $q.defer();
                $http.delete(urls.USER_SERVICE_API + userId)
                    .then(
                        function (response) {
                            loadAllUsers();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing User with id :'+userId);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);