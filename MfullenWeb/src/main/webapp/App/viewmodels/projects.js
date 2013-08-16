define(['durandal/system', 'durandal/plugins/router', 'config'],
    function (system, router, config) {


        //#region Internal Methods
        var activate = function () {
            return true;
        };

        var deactivate = function () {
        };

        var message = "My Projects";

        //#endregion

        var vm = {
            activate: activate,
            deactivate: deactivate,
            title: 'Projects',
            message: message
        };

        return vm;
    });


