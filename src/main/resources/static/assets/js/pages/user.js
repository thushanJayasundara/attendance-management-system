function  saveUser(){
    var userObj={
        "id":$('#idEmp').val() == "" ? "" : $('#idEmp').val(),
        "empNumber":$('#empNumber').val(),
        "name":$('#name').val(),
        "nic":$('#nic').val(),
        "mobile":$('#mobile').val(),
        "password":$('#password').val(),
        "userRole":$('#role').val(),
        "commonStatus":$('#status').val(),
        "departmentDTO": {
            "id": "",
            "departmentTitle": $('#dep').val(),
            "departmentDescription": "",
            "departmentContactNumber": "",
            "commonStatus": "ACTIVE"
        },
        "complainDTO":null

    }

    $.ajax({
        url: "/user/save-update",
        type: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        dataType:"json",
        data: JSON.stringify(userObj),
        success: function(data) {
            if(!data.status) {
                toastr.error(data.errorMessages);
            } else {
                toastr.success("Successfully saved.");

            }
        }
    });
}

function searchUser(userId) {
    var url = "/user/find-by-emp-num/"+userId
    $.ajax({
        url: url,
        type: "GET",
        data: {},
        success: function(data) {
            if(!data.status) {
                toastr.error(data.errorMessages);
            } else {
                $('#empNumber').val(data.payload[0].empNumber);
                $('#idEmp').val(data.payload[0].id);
                $('#name').val(data.payload[0].name);
                $('#nic').val(data.payload[0].nic);
                $('#mobile').val(data.payload[0].mobile);
                $('#role').val(data.payload[0].userRole);
                $('#status').val(data.payload[0].commonStatus);
                $('#dep').val(data.payload[0].departmentDTO.departmentTitle);
            }
        }
    });
}

function setFieldData (user) {



}



