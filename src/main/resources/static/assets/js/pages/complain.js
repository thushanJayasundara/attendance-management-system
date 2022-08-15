function saveComplain() {
    var complainObj = {
        "id": $('#idCom').val() == "" ? "" : $('#idCom').val(),
        "complainTitle":$('#comTit').val(),
        "complainDescription":$('#comDes').val(),
        "complainStatus":"UNREAD",
        "complainReply":"No reply",
        "commonStatus":"ACTIVE",
        "departmentTitle":$('#depCom').val(),
        "complainUserEmpNum":$('#comUserENum').val(),
        "repliedUserEmpNum":"",
    }

    $.ajax({
        url: "/complain/fetch-complain",
        type: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        dataType:"json",
        data: JSON.stringify(complainObj),
        success: function(data) {
            if(!data.status) {
                toastr.error(data.errorMessages);
            } else {
                toastr.success("Successfully send.");
            }
        }
    });
}

function replayComplain(){
    var complainObj = {
        "id": $('#comId').val() ,
        "complainTitle":"",
        "complainDescription":"",
        "complainStatus":"REPLAY",
        "complainReply":$('#comReplay').val(),
        "commonStatus":"ACTIVE",
        "departmentTitle":$('#depComRP').val(),
        "complainUserEmpNum":"",
        "repliedUserEmpNum":$('#adminUserENum').val(),
    }

    $.ajax({
        url: "/complain/replay-complain",
        type: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        dataType:"json",
        data: JSON.stringify(complainObj),
        success: function(data) {
            if(!data.status) {
                toastr.error(data.errorMessages);
            } else {
                toastr.success("Fetched replay.");
            }
        }
    });

}

function getComById(comId) {
    var url = "/complain/getComplainById/"+comId
    $.ajax({
        url: url,
        type: "GET",
        data: {},
        success: function(data) {
            if(!data.status) {
                toastr.error(data.errorMessages);
            } else {
                setFieldData(data.payload)
            }
        }
    });
}

function setFieldData (complain) {
    $('#comReplay2').text(complain[0]?.complainReply);
    $('#comReplayUser2').text(complain[0].repliedUser.name);
    $('#comReplayUserId').text(complain[0].repliedUser.empNumber);
    $('#exampleModalCenter').modal('show');

}