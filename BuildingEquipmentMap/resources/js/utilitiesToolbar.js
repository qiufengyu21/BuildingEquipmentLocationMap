function printMap() {
    $("#sidebar").addClass('collapsed');

    $(".sidebar").hide();
    $(".leaflet-control-zoom").hide();
    $('#logo').hide();

    window.print();

    $(".sidebar").show();
    $(".leaflet-control-zoom").show();
    $('#logo').show();
}

$("#printMap").on("click",printMap);