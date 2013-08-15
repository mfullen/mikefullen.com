define(['durandal/system', 'durandal/plugins/router', 'config'],
    function (system, router, config) {


        //#region Internal Methods
        var activate = function () {
            return true;
        };

        var deactivate = function () {
        };

        var message = "About Mike!";

        //#endregion

        var vm = {
            activate: activate,
            deactivate: deactivate,
            title: 'About Me',
            message: message
        };

        return vm;
    });

