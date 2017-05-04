/*
 * This javascript file is responsible for the creation of the device toolbars
 */

var deviceInfo = new Array();

function addDeviceInfo(name, ipAddress, netmask, type, version, serialNumber, status, release, location, nickname) {
    var insert = {
        name: name,
        ipAddress: ipAddress,
        netmask: netmask,
        type: type,
        version: version,
        serialNumber: serialNumber,
        status: status,
        release: release,
        location: location,
        nickname: nickname
    }

    deviceInfo.push(insert);
}

Handlebars.registerPartial("deviceList", $("#deviceList").html())
Handlebars.registerPartial("floorList", $("#floorList").html())
Handlebars.registerPartial("buildingList", $("#buildingList").html())
Handlebars.registerHelper('compareStatus', function(value) {
    if(value == "online") {
        return new Handlebars.SafeString('<span class=\"label label-success\">'+ value + '</span>');
    }
    else {
        return new Handlebars.SafeString('<span class=\"label label-danger\">'+ value + '</span>');
    }
})

Handlebars.registerHelper('createBuildingID', function(value) {
    return new Handlebars.SafeString('<a data-toggle=\"collapse\" data-target=\"#'+ value.replace(" ", "") + '\" style=\"cursor: pointer;\" ><h4>'+ value + '<i class=\"fa fa-caret-down\"></i></h4></a>');
})

Handlebars.registerHelper('targetID', function(value) {
    return new Handlebars.SafeString('<div id=\"'+ value.replace(" ", "") + '\" + class="panel-collapse collapse">');
})

Handlebars.registerHelper('targetFloorID', function(value1, value2) {
    //value1 = "1";
    return new Handlebars.SafeString('<div id=\"'+ value1 + '\" + class="panel-collapse collapse">');
})

Handlebars.registerHelper('createFloorID', function(value1, value2) {
   
    return new Handlebars.SafeString('<a data-toggle=\"collapse\" data-target=\"#'+ value1 + '\" style=\"cursor: pointer;\"><h5>'+ value2 + '<i class="fa fa-caret-down"></i></h5></a>');

})

Handlebars.registerHelper('buttonID', function(value1,value2) {
    //value1 = "1";
    return new Handlebars.SafeString('<input class="btn btn-default btn-xs" id="flbtn'+value1+'" title="'+value2+'" type="button" value="Open">');
})

Handlebars.registerHelper('deviceLink',function(value, nickname) {

    return new Handlebars.SafeString('<div id=\"devId'+value+'" style=\"cursor: pointer;\"><a>' + nickname+ '</a></div>');
})

function buildHierarchy() {
    var source = $("#buildingTemplate").html();
    var template = Handlebars.compile(source);

    $.ajax({
        type: "GET",
        url: localhost + "buildingList",
        success: function (data) {
            var result = {
                buildings : data
            }

            var html = template(result);
            $("#testing").html(html);
        }
    });
}

//current list of markers
var markers = new Array();

var switchIcon = L.icon({
    className: "switch",
    iconUrl: 'resources/images/icons/switch.png',
    iconSize:     [50, 50], // size of the icon
    iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
    popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
});

var routerIcon = L.icon({
    className: "router",
    iconUrl: 'resources/images/icons/wireless_router.png',
    iconSize:     [50, 50], // size of the icon
    iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
    popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
});

var computerIcon = L.icon({
    className: "computer",
    iconUrl: 'resources/images/icons/computer.png',
    iconSize:     [50, 50], // size of the icon
    iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
    popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
});

function createSwitchIcon (name, status) {
    if(status == "offline"){
        var myIcon = L.divIcon({className: "switch", html: "<div class=\"icon-size\">" +
        "" +
        "<img src=\"resources/images/icons/switch.png\" style=\"width:50px;height:50px;\">" +
        "<div class='icon-div'>" +
        "<div class=\"icon-status-red\">"+ "<img src=\"resources/images/icons/red.png\" style=\"width:10px;height:10px;\">" +"</div>" +
        "<div class = \"icon-label\"> " + name + "</div>" +
        "</div>" +
        "</div>",
            iconSize: [50,50],
        })

        return myIcon;
    } else {
        var myIcon = L.divIcon({className: "switch", html: "<div class=\"icon-size\">" +
        "" +
        "<img src=\"resources/images/icons/switch.png\" style=\"width:50px;height:50px;\">" +
        "<div class='icon-div'>" +
        "<div class=\"icon-status-green\">"+ "<img src=\"resources/images/icons/green.png\" style=\"width:10px;height:10px;\">" +"</div>" +
        "<div class = \"icon-label\"> " + name + "</div>" +
        "</div>" +
        "</div>",
            iconSize: [50,50]
        })

        return myIcon;
    }
}

function createRouterIcon (name, status) {
    if(status == "offline"){
        var myIcon = L.divIcon({className: "router", html: "<div class=\"icon-size\">" +
        "" +
        "<img src=\"resources/images/icons/wireless_router.png\" style=\"width:50px;height:50px;\">" +
        "<div class='icon-div'>" +
        "<div class=\"icon-status-red\">"+ "<img src=\"resources/images/icons/red.png\" style=\"width:10px;height:10px;\">" +"</div>" +
        "<div class = \"icon-label\"> " + name + "</div>" +
        "</div>" +
        "</div>",
            iconSize: [50,50]
        })

        return myIcon;
    } else {
        var myIcon = L.divIcon({className: "router", html: "<div class=\"icon-size\">" +
        "" +
        "<img src=\"resources/images/icons/wireless_router.png\" style=\"width:50px;height:50px;\">" +
        "<div class='icon-div'>" +
        "<div class=\"icon-status-green\">"+ "<img src=\"resources/images/icons/green.png\" style=\"width:10px;height:10px;\">" +"</div>" +
        "<div class = \"icon-label\"> " + name + " </div>" +
        "</div>" +
        "</div>",
            iconSize: [50,50]
        })

        return myIcon;
    }
}

function createComputerIcon (name, status) {
    if(status == "offline"){
        var myIcon = L.divIcon({className: "computer", html: "<div class=\"icon-size\">" +
        "" +
        "<img src=\"resources/images/icons/computer.png\" style=\"width:50px;height:50px;\">" +
        "<div class='icon-div'>" +
        "<div class=\"icon-status-red\">"+ "<img src=\"resources/images/icons/red.png\" style=\"width:10px;height:10px;\">" +"</div>" +
        "<div class = \"icon-label\"> " + name + "</div>" +
        "</div>" +
        "</div>",
            iconSize: [50,50]
        })

        return myIcon;
    } else {
        var myIcon = L.divIcon({className: "computer", html: "<div class=\"icon-size\">" +
        "" +
        "<img src=\"resources/images/icons/computer.png\" style=\"width:50px;height:50px;\">" +
        "<div class='icon-div'>" +
        "<div class=\"icon-status-green\">"+ "<img src=\"resources/images/icons/green.png\" style=\"width:10px;height:10px;\">" +"</div>" +
        "<div class = \"icon-label\"> " + name + "</div>" +
        "</div>" +
        "</div>",
            iconSize: [50,50]
        })

        return myIcon;
    }
}

function createAccessPointIcon (name, status) {
    if(status == "offline"){
        var myIcon = L.divIcon({className: "accesspoint", html: "<div class=\"icon-size\">" +
        "" +
        "<img src=\"resources/images/icons/accesspoint.png\" style=\"width:50px;height:50px;\">" +
        "<div class='icon-div'>" +
        "<div class=\"icon-status-red\">"+ "<img src=\"resources/images/icons/red.png\" style=\"width:10px;height:10px;\">" +"</div>" +
        "<div class = \"icon-label\"> " + name + "</div>" +
        "</div>" +
        "</div>",
            iconSize: [50,50]
        })

        return myIcon;
    } else {
        var myIcon = L.divIcon({className: "accesspoint", html: "<div class=\"icon-size\">" +
        "" +
        "<img src=\"resources/images/icons/accesspoint.png\" style=\"width:50px;height:50px;\">" +
        "<div class='icon-div'>" +
        "<div class=\"icon-status-green\">"+ "<img src=\"resources/images/icons/green.png\" style=\"width:10px;height:10px;\">" +"</div>" +
        "<div class = \"icon-label\"> " + name + "</div>" +
        "</div>" +
        "</div>",
            iconSize: [50,50]
        })

        return myIcon;
    }
}

function createPrinterIcon (name, status) {
    if(status == "offline"){
        var myIcon = L.divIcon({className: "printer", html: "<div class=\"icon-size\">" +
        "" +
        "<img src=\"resources/images/icons/printer.png\" style=\"width:50px;height:50px;\">" +
        "<div class='icon-div'>" +
        "<div class=\"icon-status-red\">"+ "<img src=\"resources/images/icons/red.png\" style=\"width:10px;height:10px;\">" +"</div>" +
        "<div class = \"icon-label\"> " + name + "</div>" +
        "</div>" +
        "</div>",
            iconSize: [50,50]
        })

        return myIcon;
    } else {
        var myIcon = L.divIcon({className: "printer", html: "<div class=\"icon-size\">" +
        "" +
        "<img src=\"resources/images/icons/printer.png\" style=\"width:50px;height:50px;\">" +
        "<div class='icon-div'>" +
        "<div class=\"icon-status-green\">"+ "<img src=\"resources/images/icons/green.png\" style=\"width:10px;height:10px;\">" +"</div>" +
        "<div class = \"icon-label\"> " + name + "</div>" +
        "</div>" +
        "</div>",
            iconSize: [50,50]
        })

        return myIcon;
    }
}

var items = [];

$('#editAlert').hide();
$('#viewAlert').hide();

viewMode();
sessionStorage.viewmode = "none";
buildHierarchy();

function editMode() {
    sessionStorage.viewmode = "edit";

    $('#viewAlert').hide();
    $('#removeBtn').show();
    $(document).ready ( function () {
        $("#editTab").click(function () {

            $('#editAlert').show();

        });
    });

    for(i = 0; i < items.length; i++) {
        var LamMarker = new L.marker([items[i].lat, items[i].lng], {draggable:true, alt:items[i].alt, icon: items[i].icon});
        LamMarker.on('click', onMapClick);
        LamMarker.on('dragend',saveLocation);
        markers.push(LamMarker);
        map.addLayer(markers[i]);
    }

    $('#deviceInfoModal').modal('hide');
}

function removeMarkers() {
    for(i=0;i<markers.length;i++) {
        map.removeLayer(markers[i]);
    }
    markers = [];
}

function changeLayout() {
    removeMarkers();
    items = [];
}

function viewMode() {
    sessionStorage.viewmode = "view";

    $('#editAlert').hide();
    $('#removeBtn').hide();
    $(document).ready ( function () {
        $("#viewTab").click(function () {
            buildHierarchy();
            $('#viewAlert').show();
            $('#viewMode').html('Now Viewing: ' + currBuilding.name + '-' + currFloor);
        });
    });

    for(i = 0; i < items.length; i++){
        var LamMarker = new L.marker([items[i].lat, items[i].lng], {draggable:false, alt:items[i].alt, icon: items[i].icon});
        LamMarker.on('click', onMapClick);
        LamMarker.on('mouseover', markerMouseOver);
        LamMarker.on('mouseout', markerMouseOut);
        LamMarker.on('dragend',saveLocation);
        LamMarker.bindPopup("This is a POPUP!");
        markers.push(LamMarker);
        map.addLayer(markers[i]);
    }

    $('#deviceInfoModal').modal('hide');
}

// Take in the icon type, coordinates, alt.
// Add to the array and then to the map.
function addMarker(lat, lng, icon, alt) {
    var insert = {
        lat: lat,
        lng: lng,
        icon: icon,
        alt: alt
    }

    items.push(insert);

    var LamMarker = new L.marker([insert.lat, insert.lng], {draggable:true, alt:insert.alt, icon: insert.icon});
    LamMarker.on('click', onMapClick);
    LamMarker.on('mouseout', markerMouseOut);
    LamMarker.on('dragend',saveLocation);
    LamMarker.bindPopup("");
    markers.push(LamMarker);
    map.addLayer(markers[markers.length-1]);
}


function sendData() {
    var layoutName = (currBuilding.name).replace(/\s/g, '') + "-" + ((currFloor).replace(/\s/g, ''));

    for (var i = 0; i < currBuilding.numFloors; i++){
        if(currBuilding.floors[i].name == currFloor){
            currBuilding.floors[i].layoutName = layoutName;

            $.ajax({
                url: localhost + "building",
                method: "POST",
                data: {
                    data: JSON.stringify(currBuilding)
                },
                success:function(data) {

                }
            });
        }
    }

    var sendArray = [];
    var insert;
    for(var i = 0; i < items.length; i++) {
        insert = {
            lat: items[i].lat,
            lng: items[i].lng,
            icon: items[i].icon.options.className,
            alt: items[i].alt
        }

        sendArray.push(insert);
    }

    var namefield = "Name:";
    namefield = namefield.concat(layoutName, " ");

    var sendString = namefield;

    sendString = sendString.concat(JSON.stringify(sendArray));

    $.ajax({
        url: localhost + "layout",
        method: "POST",
        data: {
            data: sendString
        },
        success:function(data) {
            $("#confirmationFloorSave").html("Floor: " + currFloor+ " saved!");
            $("#confirmationFloorSave").show();
        }
    });
}

function uploadBlueprint() {
    var file = $("#blueprintUpload").get(0).files[0];
    var blueprintName = $("#blueprintName").val();
    var fileReader = new FileReader();

    fileReader.onloadend = function() {
        var image = new Image();
        image.onload = function() {
            var request = blueprintName + "," + image.height
                + "," + image.width + "," + fileReader.result;

            $.ajax({
                url: localhost + "blueprint",
                method: "POST",
                data: {
                    data: request
                },
                success:function(data) {
                    $(".changeBlueprint").empty();
                    getBlueprint();
                    $("#confirmationUploadBlueprint").html("Blueprint: " + blueprintName +" Saved!")
                    $("#confirmationUploadBlueprint").show();
                }
            });
        };
        image.src = fileReader.result;
    }
    fileReader.readAsDataURL(file);
}

$("#sendData").on("click", sendData);
$("#sendBlueprint").on("click", uploadBlueprint)

// Get the device that is selected from the drop down.
function getSelectedDevice() {
    var x = document.getElementById("selectedDevice");
    var selectedDevice = x.options[x.selectedIndex].value;
    var deviceName = selectedDevice.split("-");
    var status;
    var nickname;

    for(i = 0; i < deviceInfo.length; i++) {
        if(deviceName[1] == deviceInfo[i].serialNumber) {
            status = deviceInfo[i].status;
            nickname = deviceInfo[i].nickname;
            break;
        }
    }

    switch(deviceName[0]) {
        case "Switch":
            var myIcon = createSwitchIcon(nickname,status);
            addMarker(-50, 80, myIcon, deviceName[1]);
            break;
        case "Router":
            var myIcon = createRouterIcon(nickname,status);
            addMarker(-50, 120, myIcon, deviceName[1]);
            break;
        case "Computer":
            var myIcon = createComputerIcon(nickname,status);
            addMarker(-50,150, myIcon, deviceName[1]);
            break;
        case "AccessPoint":
            var myIcon = createAccessPointIcon(nickname,status);
            addMarker(-50,150, myIcon, deviceName[1]);
            break;
        case "Printer":
            var myIcon = createPrinterIcon(nickname,status);
            addMarker(-50,150, myIcon, deviceName[1]);
            break;
    }

    $('#selectedDevice').find('option:selected').remove();
}

// Remove the device from map and add it to the drop down.
function removeSelectedDevice(index) {
    if(deviceInfo.length > 0) {
        $("#selectedDevice option[value='" + deviceInfo[index].name + "-" + deviceInfo[index].serialNumber + "']").remove();
    }
}

$("#editTab").click(function() {
    $('#editAlert').show();
    if(markers != 0){
        removeMarkers();
    }

    editMode();
});

$("#viewTab").click(function() {
    $('#viewAlert').show();

    if(markers != 0){
        removeMarkers();
    }

    viewMode();
});

// Global variable alt for notes.
var deviceAlt;

// Events when a device icon is clicked.
function onMapClick(e) {
    $('#deviceInfoModal').modal('show');

    deviceAlt = e.target.options.alt;

    var index;

    for(i = 0; i < deviceInfo.length; i++) {
        if(e.target.options.alt == deviceInfo[i].serialNumber) {
            index = i;
            break;
        }
    }

    $('#deviceInfoModal .modal-header').html('<h4>' + deviceInfo[index].nickname + '</h4>');
    $('#deviceInfoModal .modal-body .deviceInfo').html(
        '<b>'+ "IP Address: " + "</b>" + deviceInfo[index].ipAddress
        +'<br>' + '<b>'+ "Netmask: " + "</b>" + deviceInfo[index].netmask
        + '<br>' + '<b>' + "Type: " + "</b>" + deviceInfo[index].type
        + '<br>' + '<b>'+ "Version: " + "</b>" + deviceInfo[index].version
        + '<div id=\"modalDeviceSerialNumber\">' + '<b>'+ "Serial Number: " + "</b>" + deviceInfo[index].serialNumber +' </div>'
        + '<b>'+ "Status: " + "</b>" + deviceInfo[index].status
        + '<br>' + '<b>'+ "Release: " + "</b>" + deviceInfo[index].release
        + '<br>' + '<b>'+ "Location: " + "</b>" + deviceInfo[index].location


    );

    $.ajax({
        type: "GET",
        dataType: "text",
        url: localhost + "getNotes/" + deviceInfo[index].serialNumber,
        success: function(data) {
            $('#deviceInfoModal .modal-body #addNotes').val(data);
        },
        fail: function(data) {
            $('#deviceInfoModal .modal-body #addNotes').val(data);
        }
    });

    if(deviceInfo[index].status == "offline") {
        $('#deviceInfoModal .modal-header').css("background-color", "red");
    }
    else {
        $('#deviceInfoModal .modal-header').css("background-color", "rgb(0,255,0");
    }
}

$("#notesBtn").on("click", addNotes);

function addNotes() {
    // Whatever is being added to the notes field.
    var contents = $('#deviceInfoModal .modal-body #addNotes').val();

    var notes = {
        fileName: deviceAlt,
        contents: contents
    }

    // Post to back-end with the content, naming the file by device serial number.
    $.ajax({
        url: localhost + "setNotes",
        method: "POST",
        data: {
            notes: JSON.stringify(notes)
        }
    });
}

$("#removeBtn").on("click", removeDevice);

function removeDevice() {
    var header = $('#deviceInfoModal .modal-header').text();
    var modalSerialNumber = $('#modalDeviceSerialNumber').text();
    var serialNum = modalSerialNumber.substring(15).trim();

    for(var i = 0; i < markers.length; i++) {
        var alt = markers[i].options.alt;

        if(serialNum == alt) {
            map.removeLayer(markers[i]);
            markers.splice(i, 1);
        }
    }

    for(var i = 0; i < items.length; i++) {
        var alt = items[i].alt;

        for(var j = 0; j < deviceInfo.length; j++) {
            var serialNumber = deviceInfo[j].serialNumber;
            var id = deviceInfo[j].name + ' - ' + deviceInfo[j].nickname;

            if(serialNumber == alt && serialNum == serialNumber) {
                items.splice(i, 1);

                $('#selectedDevice').append('<option value=' + deviceInfo[j].name + '-' + deviceInfo[j].serialNumber + '>' + id + '</option>');
            }
        }
    }
}

function markerMouseOver(e) {
    var markeralt = e.target.options.alt;
    var status;
    var markernickname;
    var markerIp;
    for(var i = 0; i < deviceInfo.length; i++) {
        if(markeralt == deviceInfo[i].serialNumber) {
            status = deviceInfo[i].status;
            markernickname = deviceInfo[i].nickname;
            markerIp = deviceInfo[i].ipAddress;
            break;
        }
    }

    if(status == 'online') {
        e.target._popup.setContent("Device IP: " + markerIp.toString() + '<br><span class="label label-success">Online</span>');
    } else {
        e.target._popup.setContent("Device IP: " + markerIp.toString() + '<br><span class="label label-danger">Offline</span>');
    }

    e.target.openPopup();
}

function markerMouseOut(e) {
    e.target.closePopup();
}

function saveLocation(e) {
    var marker = e.target;
    var markeralt = e.target.options.alt;

    var result = marker.getLatLng();

    for(i=0;i < items.length ;i++){
        if(items[i].alt == markeralt ){
            items[i].lat = e.target._latlng.lat;
            items[i].lng = e.target._latlng.lng;
        }
    }
}

function getItemsSize() {
    return items.length;
}

function generateFloors() {
    var floorNum = parseInt($("#floorNumber").val());

    $('#floorInput').empty();
    $('#floorInput').append('<h5>Select Floor Blueprints</h5>')

    for(var i = 1; i <= floorNum; i++) {
        $('#floorInput').append('<label for='+ "floor_" + i +'> Floor ' + i + ': &nbsp;</label>' +
            '<select id="floor_'+ i + '" class=\"changeBlueprint\">' +
            '</select> <br>');
    }

    getBlueprint();
    $('#floorInput').append('<input class="btn btn-default" id="saveBuilding" type="button" value="Save Building">');
}

function saveBuilding() {
    var floorNum = parseInt($("#floorNumber").val());
    var buildingName = $("#buildingName").val();

    var result = new Array();

    var building = {
        name: buildingName,
        numFloors: floorNum,
        building_status: ""
    }

    var insert = {
        name: "",
        blueprint: "",
        status: "",
        numDevices: 0
    }

    var layoutName;

    for(var i = 1; i <= floorNum; i++) {
        layoutName = (buildingName).replace(/\s/g, '') + "-" + ("Floor " + i).replace(/\s/g, '');
        insert = {
            name: "Floor " + i,
            blueprint: $("#floor_"+ i + " option:selected").text(),
            layoutName: layoutName
        }

        var sendArray = [];
        var namefield = "Name:";

        namefield = namefield.concat(layoutName, " ");

        var sendString = namefield;

        $.ajax({
            url: localhost + "layout",
            method: "POST",
            data: {
                data: sendString
            },
            success:function(data) {
                $("#confirmationCreateBuilding").html("Building: " + buildingName + " saved!")
            }
        });

        result.push(insert);
    };

    building.floors = result;

    $.ajax({
        url: localhost + "building",
        method: "POST",
        data: {
            data: JSON.stringify(building)
        },
        success: function(data) {
            $('#openBuilding').empty();
            loadBuildings();
        }
    });
}

var refreshLayoutName = "";

function openFloor() {
    var layoutName = this.id.substring(5);
    refreshLayoutName = layoutName;
    var blueprintName = this.title;

    $('#viewMode').html( 'Now Viewing: '+ layoutName);

    $.ajax({
        type: "GET",
        url: localhost + "blueprint/"+ blueprintName,
        success: function (data) {
            var w = data.width,
                h = data.height,
                url = data.content;

            map.removeLayer(blueprintOverlay);

            var southWest = map.unproject([0, h], map.getMaxZoom()-1);
            var northEast = map.unproject([w, 0], map.getMaxZoom()-1);
            var bounds = new L.LatLngBounds(southWest, northEast);

            // Add the image overlay so that it covers the entire map.
            blueprintOverlay =  new L.imageOverlay(url, bounds).addTo(map);

            // Tell Leaflet that the map is exactly as big as the image.
            map.setMaxBounds(bounds);
        }
    });

    changeLayout(layoutName);

    loadLayout(layoutName, "view");
}

// Pass in the name of the layout and the mode calling the function.
function loadLayout(layoutName, mode) {
    getDeviceList();

    $.ajax({
        type: "GET",
        dataType: "json",
        url: localhost + "layout/" + layoutName,

        success:function(data) {
            var str = data.content;

            if(str != "") {
                var outJson = jQuery.parseJSON(str);

                for(var x in outJson) {
                    // Calculate the status of each device while loading them.
                    var status;
                    var index;
                    var nickname;

                    for(i = 0; i < deviceInfo.length; i++) {
                        if(outJson[x].alt == deviceInfo[i].serialNumber) {
                            index = i;
                            status = deviceInfo[i].status;
                            nickname = deviceInfo[i].nickname;
                            break;
                        }
                    }

                    if(outJson[x].icon == "router") {
                        var myIcon = createRouterIcon(nickname, status);
                        addMarker(outJson[x].lat, outJson[x].lng, myIcon, outJson[x].alt);
                        removeSelectedDevice(index);
                    }
                    else if(outJson[x].icon == "switch") {
                        var myIcon = createSwitchIcon(nickname, status);
                        addMarker(outJson[x].lat, outJson[x].lng, myIcon, outJson[x].alt);
                        removeSelectedDevice(index);
                    }
                    else if(outJson[x].icon == "computer") {
                        var myIcon = createComputerIcon(nickname, status);
                        addMarker(outJson[x].lat, outJson[x].lng, myIcon, outJson[x].alt);
                        removeSelectedDevice(index);
                    }
                    else if(outJson[x].icon == "accesspoint") {
                        var myIcon = createAccessPointIcon(nickname, status);
                        addMarker(outJson[x].lat, outJson[x].lng, myIcon, outJson[x].alt);
                        removeSelectedDevice(index);
                    }
                    else if(outJson[x].icon == "printer") {
                        var myIcon = createPrinterIcon(nickname, status);
                        addMarker(outJson[x].lat, outJson[x].lng, myIcon, outJson[x].alt);
                        removeSelectedDevice(index);
                    }
                }

                if(markers != 0){
                    removeMarkers();
                }

                if(mode == "view") {
                    viewMode();
                } else if(mode == "edit") {
                    editMode();
                }
            }
        }
    });
}

function selectDevice() {
    var deviceName = this.id.substring(5);
    var status;
    var markernickname;
    var markerIP;

    // Checks current status of device
    for(var i = 0; i < deviceInfo.length; i++) {
        if(deviceName == deviceInfo[i].serialNumber) {
            status = deviceInfo[i].status;
            markernickname = deviceInfo[i].nickname;
            markerIP = deviceInfo[i].ipAddress;
            break;
        }
    }

    // Finds and updates device status.
    for(var i = 0; i < markers.length; i++){
        // Match device name with marker name
        if(deviceName == markers[i].options.alt){
            if(status == 'online') {
                markers[i]._popup.setContent("Device IP: " + markerIP + '<br><span class="label label-success">Online</span>');
            } else {
                markers[i]._popup.setContent("Device IP: " + markerIP + '<br><span class="label label-danger">Offline</span>');
            }
            markers[i].openPopup();
            map.panTo(markers[i].getLatLng());
        }
    }
}

refreshDevices();

$("#floorNumber").on("change",generateFloors);
$("#saveBuilding").on("click",saveBuilding);

$(document).on('click', '#saveBuilding' ,saveBuilding);
$(document).on('click', 'input[id^="flbtn"]',openFloor);
$(document).on('click', 'div[id^="devId"]',selectDevice);