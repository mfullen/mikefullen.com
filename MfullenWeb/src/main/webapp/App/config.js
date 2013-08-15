define(function () {
    var remoteServiceName = 'http://localhost:63063/' + 'api/';

    var routes = [{
        url: 'home',
        moduleId: 'viewmodels/home',
        name: 'Home',
        visible: true,
        caption: 'Home'
    }, {
        url: 'about',
        moduleId: 'viewmodels/about',
        name: 'About',
        visible: true,
        caption: 'About'
    }
        ];

    var startModule = 'home';

    return {
        debugEnabled: ko.observable(true),
        remoteServiceName: remoteServiceName,
        routes: routes,
        startModule: startModule
    };
});