define(['durandal/system', 'durandal/plugins/router', 'config'],
    function (system, router, config) {


        //#region Internal Methods
        var activate = function () {
            return true;
        };

        var deactivate = function () {
        };

        var message = "Mike is a Software Engineer who is interested in many computer topics including: Java, Javascript, C#, MVC, Game Design and more";

        //#endregion

        var vm = {
            activate: activate,
            deactivate: deactivate,
            title: 'About Me',
            message: message
        };

        return vm;
    });

