function  saveDepartment(){
    var depObj={
        "id": $('#idDep').val() == "" ? "" : $('#idDep').val(),
        "departmentTitle": $('#depTit').val(),
        "departmentDescription": $('#depDes').val(),
        "departmentContactNumber": $('#depCon').val(),
        "commonStatus": $('#statusDep').val()
    }

    $.ajax({
        url: "/department/save-update",
        type: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        dataType:"json",
        data: JSON.stringify(depObj),
        success: function(data) {
            if(!data.status) {
                toastr.error(data.errorMessages);
            } else {
                toastr.success("Successfully saved.");

            }
        }
    });
}

function searchDepartment(departmentTitle) {
    var url = "/department/find-by-title/"+departmentTitle
    $.ajax({
        url: url,
        type: "GET",
        data: {},
        success: function(data) {
            if(!data.status) {
                toastr.error(data.errorMessages);
            } else {
                $('#depTit').val(data.payload[0].departmentTitle);
                $('#idDep').val(data.payload[0].id);
                $('#depDes').val(data.payload[0].departmentDescription);
                $('#depCon').val(data.payload[0].departmentContactNumber);
                $('#statusDep').val(data.payload[0].commonStatus);
            }
        }
    });
}
