define(function () {
    var remoteServiceName = 'http://localhost:63063/' + 'api/';

    var routes = [
    {
        url: 'home',
        moduleId: 'viewmodels/home',
        name: 'Home',
        visible: true,
        caption: 'Home'
    },
    {
        url: 'about',
        moduleId: 'viewmodels/about',
        name: 'About',
        visible: true,
        caption: 'About'
    },
    {
        url: 'resume',
        moduleId: 'viewmodels/resume',
        name: 'Resume',
        visible: true,
        caption: 'Resume'
    },
    {
        url: 'projects',
        moduleId: 'viewmodels/projects',
        name: 'Projects',
        visible: false,
        caption: 'My Projects'
    },
    {
        url: 'blog',
        moduleId: 'viewmodels/blog',
        name: 'Blog',
        visible: false,
        caption: 'My Blogs'
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