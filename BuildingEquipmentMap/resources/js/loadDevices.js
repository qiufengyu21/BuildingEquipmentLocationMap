var firstTime = true;

function getDeviceList() {
    $.ajax({
        type: "GET",
        url: localhost + "devices",
        success: function (data) {
            deviceInfo = [];

            for(i = 0; i < data.length; i++) {
                addDeviceInfo(data[i][0], data[i][1], data[i][2], data[i][3], data[i][4], data[i][5], data[i][6], data[i][7], data[i][8], data[i][9]);
                if(firstTime) {
                    $('#selectedDevice').append('<option value=' + data[i][0] + '-' + data[i][5] + '>' + data[i][0] + ' - ' + data[i][9] + '</option>');
                }
            }
            firstTime = false;
        }
    });
}

getDeviceList();
