define(['durandal/plugins/router', 'durandal/app', 'config'],
    function (router, app, config) {

        var shell = {
            router: router,
            search: function() {
                //It's really easy to show a message box.
                //You can add custom options too. Also, it returns a promise for the user's response.
                app.showMessage('Search not yet implemented...');
            },
            activate: activate
        };
        return shell;

        //#region Internal Methods
        function activate() {
            return boot()
            .fail(failedInitialization);
        }

        function boot() {
            //logger.log('Review Tool ClientLoaded!', null, system.getModuleId(shell), true);
            router.map(config.routes);
            return router.activate(config.startModule);
        }


        function failedInitialization(error) {
            var msg = 'App initialization failed: ' + error.message;
        //logger.logError(msg, error, system.getModuleId(shell), true);
        }
    });