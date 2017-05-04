var currBuilding;
var currFloor;

loadBuildings();
removeAllAddedDevices();

function loadBuildings() {
    $.ajax({
        type: "GET",
        url: localhost + "buildingListName",
        success: function (data) {
            var buildings = data.split(",");

            $('#openBuilding').append('<option value="prompt">Select a Building</option>');
            for(i = 0; i < buildings.length; i++) {
                if(!(buildings[i] == "")) {
                    $('#openBuilding').append('<option value=' + buildings[i] + '>' + buildings[i]+ '</option>');
                }
            }
        }
    });
}

function loadFloors() {
    changeLayout();
    loadDefaultMap();

    var buildingName = $("#openBuilding option:selected").text();
    var value = $('#openBuilding option:selected').val();

    $('#editFloor').empty();
    $('#currentFloor').empty();

    currFloor="";

    if(value != "prompt"){
        $.ajax({
            type: "GET",
            url: localhost + "building/" + buildingName,
            success: function (data) {
                if (data){
                    currBuilding = data;
                    $('#editFloor').append('<h4>Building: '+ data.name+'</h4>');

                    for(var i = 0; i < data.numFloors; i++) {
                        $('#editFloor').append('<h5>'+ data.floors[i].name +'&nbsp;<input id="' + (data.floors[i].name).replace(/\s/g, '') + '" class="btn btn-default"  type="button" value="Load Floor"> </h5>');
                    }
                    $('#currentFloor').append('<h4>No Current Floor Selected</h4>');
                }
            }
        });
    }
}

/**
 * Function used to remove all devices used in any buildings
 */
function removeAllAddedDevices() {
    $.ajax({
        type: "GET",
        url: localhost + "buildingList",
        success: function (data) {
            var result = {
                buildings : data
            }

            for (var i = 0; i < result.buildings.length; i++){
                for(var x in result.buildings[i].floors){
                    for(var y in result.buildings[i].floors[x].devices){
                        var index;
                        var nickname;

                        for(var j = 0; j < deviceInfo.length; j++) {
                            if(result.buildings[i].floors[x].devices[y].name == deviceInfo[j].serialNumber) {
                                index = j;
                                nickname = deviceInfo[j].nickname;
                                break;
                            }
                        }
                        removeSelectedDevice(index);
                    }
                }
            }
        }
    });
}

function openLayout() {
    if(currFloor){
        sendData();
    }

    $("#addDevices").show();

    for (var i = 0; i < currBuilding.numFloors; i++) {
        if(this.id == (currBuilding.floors[i].name).replace(/\s/g, '')) {

            currFloor = currBuilding.floors[i].name;
            $('#editMode').html('Now Editing: ' + currBuilding.name + '-' + currFloor);

            $('#currentFloor').empty();
            $('#currentFloor').append('<h4> Current Floor: '+ currFloor + '</h4>');

            var blueprintName = currBuilding.floors[i].blueprint;

            $.ajax({
                type: "GET",
                url: localhost + "blueprint/" + blueprintName,
                success: function (data) {
                    var w = data.width,
                        h = data.height,
                        url = data.content;

                    map.removeLayer(blueprintOverlay);
                    var southWest = map.unproject([0, h], map.getMaxZoom()-1);
                    var northEast = map.unproject([w, 0], map.getMaxZoom()-1);
                    var bounds = new L.LatLngBounds(southWest, northEast);

                    blueprintOverlay =  new L.imageOverlay(url, bounds).addTo(map);

                    // Tell Leaflet that the map is exactly as big as the image.
                    map.setMaxBounds(bounds);
                }
            });

            var layoutName = currBuilding.floors[i].layoutName;

            changeLayout();

            loadLayout(layoutName, "edit");
        }
    }
}

function loadDefaultMap(){
    $("#addDevices").hide();
    var w = 1920,
        h = 1080,
        url = 'resources/images/blueprints/editMode.png';

    if(blueprintOverlay){
        map.removeLayer(blueprintOverlay);
    }

    var southWest = map.unproject([0, h], map.getMaxZoom()-1);
    var northEast = map.unproject([w, 0], map.getMaxZoom()-1);
    var bounds = new L.LatLngBounds(southWest, northEast);

    blueprintOverlay = new L.imageOverlay(url, bounds).addTo(map);

    map.setMaxBounds(bounds);
}


$("#openBuilding").on("change",loadFloors);

$(document).on('click', 'input[id^="Floor"]' ,openLayout);
