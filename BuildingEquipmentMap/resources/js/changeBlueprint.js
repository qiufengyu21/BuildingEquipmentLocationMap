// Get the different stored blueprints.
function getBlueprint() {
    $.ajax({
        type: "GET",
        url: localhost + "blueprinttable",
        success: function (data) {
            var blueprints = data.split(",");

            for(i = 0; i < blueprints.length; i++) {
                $('.changeBlueprint').append('<option value=' + blueprints[i] + '>' + blueprints[i]+ '</option>');
            }
        }
    });
}

getBlueprint();

//Used to select blueprint and change the current layouts blueprint.
function changeBlueprint() {
    var x = document.getElementById("changeBlueprint");
    var blueprintName = x.options[x.selectedIndex].value;

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

            // add the image overlay, so that it covers the entire map
            blueprintOverlay =  new L.imageOverlay(url, bounds).addTo(map);

            // tell Leaflet that the map is exactly as big as the image
            map.setMaxBounds(bounds);
        }
    });
}

$("#selectBlueprint").on("click",changeBlueprint);
