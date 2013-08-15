define(['durandal/system', 'durandal/plugins/router', 'config'],
    function (system, router, config) {


        //#region Internal Methods
        var activate = function () {
            return true;
        };

        var deactivate = function () {
        };

        var message = "Welcome to Mike Fullen.com!";

        //#endregion

        var vm = {
            activate: activate,
            deactivate: deactivate,
            title: 'Home View',
            message: message
        };

        return vm;
    });

