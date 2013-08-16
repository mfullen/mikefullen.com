define(['durandal/system', 'durandal/plugins/router', 'config'],
    function (system, router, config) {


        //#region Internal Methods
        var activate = function () {
            return true;
        };

        var deactivate = function () {
        };

        var message = "My Blogs";

        //#endregion

        var vm = {
            activate: activate,
            deactivate: deactivate,
            title: 'Blogs coming soon',
            message: message
        };

        return vm;
    });

