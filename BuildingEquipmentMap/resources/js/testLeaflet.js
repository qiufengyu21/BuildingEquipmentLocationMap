var localhost = "http://localhost:8080/";

var map = L.map('image-map', {
  minZoom: 1,
  maxZoom: 3,
  center: [0, 0],
  zoom: 1,
  crs: L.CRS.Simple
});

// Dimensions of the image
var w = 1920,
    h = 1080,
    url = 'resources/images/blueprints/editMode.png';

var southWest = map.unproject([0, h], map.getMaxZoom()-1);
var northEast = map.unproject([w, 0], map.getMaxZoom()-1);
var bounds = new L.LatLngBounds(southWest, northEast);

var blueprintOverlay = new L.imageOverlay(url, bounds).addTo(map);

map.setMaxBounds(bounds);

var sidebar = L.control.sidebar('sidebar').addTo(map);

map.on('zoomend',function() {
  if(map.getZoom() < 2) {
    $('#printMap').prop('disabled', true);
  } else {
    $('#printMap').prop('disabled', false);
  }
});
