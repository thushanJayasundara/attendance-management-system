
function  saveUser(){
    var userObj={
        "id":$('#idEmp').val() == "" ? "" : $('#idEmp').val(),
        "empNumber":$('#empNumber').val(),
        "name":$('#name').val(),
        "nic":$('#nic').val(),
        "mobile":$('#mobile').val(),
        "password":"101010",
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
                loadUserTable();
                toastr.success("Successfully saved.");

            }
        }
    });
}

function searchUser() {
    var userId = $('#search_user').val()
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

function loadUserTable() {
    $.ajax({
        url: "/user/get-all",
        type: "GET",
        data: {},
        success: function(data) {
            if(!data.status) {
                toastr.error(data.errorMessages);
            } else {
                setUserTable(data.payload[0]);
            }
        }
    });
}


function setUserTable(posts) {
    console.log(posts)
    if ($.isEmptyObject(posts)) {
        $('#userTable').DataTable().clear();
        $('#userTable').DataTable({
            "bPaginate": false,
            "bLengthChange": false,
            "bFilter": false,
            "bInfo": false,
            "destroy": true,
            "language": {
                "emptyTable": "No Data Found !!!",
                search: "",
                searchPlaceholder: "Search..."
            }
        });
    } else {
        $("#userTable").DataTable({
            dom: 'Bfrtip',
            lengthMenu: [
                [ 10, 25, 50, 100],
                [ '10', '25', '50', '100']
            ],
            buttons: [{
                extend: 'pageLength'
            },
                {
                    extend: 'pdf',
                    title: 'Employee Details',
                    pageSize: 'A4'
                },
                {
                    extend: 'excel',
                    title: 'Employee Details',
                    pageSize: 'A4'
                },
                {
                    extend: 'print',
                    title: 'Employee Details',
                    pageSize: 'A4'
                }
            ],
            "destroy": true,
            "language": {
                search: "",
                searchPlaceholder: "Search..."
            },
            "data": posts,
                "columns": [{
                    "data": "empNumber"
                },
                {
                    "data": "name"
                },
                {
                    "data": "nic"
                },
                {
                     "data": "mobile"
                 },
                {
                    "data": "userRole"
                },
                {
                     "data": "departmentDTO.departmentTitle"
                 },
                {
                    "data": "commonStatus",
                    mRender: function(data) {
                        var classLb = ''
                        if(data == "ACTIVE")
                            classLb = 'badge badge-success'
                        if(data == "INACTIVE")
                            classLb = 'badge badge-info'
                        else
                            classLb = 'badge badge-success'
                        var columnHtml = '<td><label class="'+classLb+'">'+data+'</label></td>';
                        return (columnHtml);
                    }
                }]
        });
    }
}

/*function setUserTable(userList) {
    $('#userTable').html('');
    $.each(userList, function(key, users) {
        $.each(users, function(key, user) {
            var classLb = ''
            if(user.commonStatus == "ACTIVE")
                classLb = 'badge badge-success'
            if(user.commonStatus == "INACTIVE")
                classLb = 'badge badge-info'
            $('#userTable').append(
                '<tr>'+
                '<td>'+user.empNumber+'</td>'+
                '<td>'+user.name+'</td>'+
                '<td>'+user.nic+'</td>'+
                '<td>'+user.mobile+'</td>'+
                '<td><label class="'+classLb+'">'+user.commonStatus+'</label></td>'+
                '<td>'+user.userRole+'</td>'+
                '<td>'+user.departmentDTO.departmentTitle+'</td>'+
                '</tr>'
            );
        });
    });
}*/

function ClearUserField() {
    $('#empNumber').val('');
    $('#idEmp').val('');
    $('#name').val('');
    $('#nic').val('');
    $('#mobile').val('');
    $('#role').val('');
    $('#status').val('');
    $('#dep').val('');
}



