/**
 * Created by ShchykalauM on 18.01.2017.
 */
var testApp = angular.module("test", [])


testApp.controller("testController", function ($scope, $http) {

    $scope.phones = [{
        name: 'Nokia Lumia 630',
        year: 2014,
        price: 200,
        company: {
            name: 'Nokia',
            country: 'Финляндия'
        }
    }, {
        name: 'Samsung Galaxy S 4',
        year: 2014,
        price: 400,
        company: {
            name: 'Samsung',
            country: 'Республика Корея'
        }
    }, {
        name: 'Mi 5',
        year: 2015,
        price: 300,
        company: {
            name: 'Xiaomi',
            country: 'Китай'
        }
    }];

    $scope.tablets = [{
        name: 'Samsung Galaxy Tab S4',
        year: 2014,
        price: 300,
        company: 'Samsung'
    }, {
        name: 'LG G PAD 8.3',
        year: 2013,
        price: 180,
        company: 'LG'
    }, {
        name: 'IdeaTab A8',
        year: 2014,
        price: 220,
        company: 'Lenovo'
    }];

    $scope.data = {};
    $scope.setFile = function () {
        if ($scope.data.mode == 'Tablets')
            return 'tabletList.html';
        else if ($scope.data.mode == 'Phones')
            return 'phoneList.html';
    };
    $scope.modes = [{
        value: 'Tablets',
        label: 'Планшеты'
    }, {
        value: 'Phones',
        label: 'Смартфоны'
    }];


    $scope.getProducts = function () {
        $http({
            method: "GET",
            url: "/getProducts"
        }).then(function mySucces(response) {
            $scope.data = response.data;
        }, function myError(response) {
            $scope.data = response.statusText;
        });
    };

    $scope.delete = function (id) {
        $http({
            method: "DELETE",
            url: "/deleteProduct/" + id

        }).then(function mySucces(response) {
            alert("delete")
        }, function myError(response) {
            alert("error")
        });

        /*$http.delete("/deleteProduct/" + id, input).success(function (data, status) {
         console.log(data);
         });*/
    };


    $scope.getProducts();

    $scope.list = []

    $scope.addToList = function () {
        $scope.list.push($scope.task)
    }
});