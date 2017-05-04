function refreshDevices() {
    setInterval(function() {
        if(sessionStorage.viewmode == "view") {
            changeLayout();
            loadLayout(refreshLayoutName, "view");
            buildHierarchy();
        }
    }, 30000);
}
