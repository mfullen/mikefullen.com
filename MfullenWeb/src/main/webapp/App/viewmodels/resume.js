define(['durandal/system', 'durandal/plugins/router', 'config'],
    function (system, router, config) {


        //#region Internal Methods
        var activate = function () {
            return true;
        };

        var deactivate = function () {
        };

        var message = "Please view my LinkedIn Resume";
        var linkedInUrl = "http://www.linkedin.com/in/mikefullen";
        //#endregion

        var vm = {
            activate: activate,
            deactivate: deactivate,
            title: 'Resume',
            message: message,
            linkedinUrl: linkedInUrl
        };

        return vm;
    });

