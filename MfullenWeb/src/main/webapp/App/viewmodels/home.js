define(['durandal/system', 'durandal/plugins/router', 'config'],
    function (system, router, config) {


        //#region Internal Methods
        var activate = function () {
            return true;
        };

        var deactivate = function () {
        };

        var message = "Welcome to Mike Fullen.com!";

        var paragraph = "Thank you for visiting my personal site. It is currently underconstruction as I convert it from a .NET MVC 3 application to a Java application. I will be using a REST api for the backend and Durandaljs for the front end";
        //#endregion

        var vm = {
            activate: activate,
            deactivate: deactivate,
            title: 'Home View',
            message: message,
            paragraph: paragraph
        };

        return vm;
    });

