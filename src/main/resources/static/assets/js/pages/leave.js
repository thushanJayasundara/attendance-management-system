function saveLeave() {
    var leaveInsertDTO = {
        "id": $('#idLeave').val() == "" ? null : $('#idLeave').val(),
        "leaveEmpNumber":$('#emp_Number').val(),
        "reason":$('#reason').val(),
        "commonStatus":$('#status').val(),
        "leaveDate":$('#leaveDate').val(),
    }

    $.ajax({
        url: "/leave/",
        type: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        dataType:"json",
        data: JSON.stringify(leaveInsertDTO),
        success: function(data) {
            if(!data.status) {
                toastr.error(data.errorMessages);
            } else {
                loadLeaveTable();
                toastr.success("Successfully save.");
            }
        }
    });
}

function searchLeave() {
    var leaveDate =  $('#search_leaveDate').val() == "" ? "-1" : $('#search_leaveDate').val()
    var empNum = $('#search_empNum').val()
    if(empNum=="")
        toastr.error("Please enter valid employee number.");
    if(leaveDate=="-1")
        toastr.error("Please enter valid date.");

    var url = "/leave/advance-search/"+leaveDate+"/"+empNum
    $.ajax({
        url: url,
        type: "POST",
        data: {},
        success: function(data) {
            if(!data.status) {
                toastr.error(data.errorMessages);
            } else {
                $('#idLeave').val(data.payload[0].id);
                $('#emp_Number').val(data.payload[0].leaveUser.empNumber);
                $('#reason').val(data.payload[0].reason);
                $('#status').val(data.payload[0].commonStatus);
                $('#leaveDate').val(data.payload[0].leaveDate);
            }
        }
    });
}

function loadLeaveTable() {
    $.ajax({
        url: "/leave/",
        type: "GET",
        data: {},
        success: function(data) {
            if(!data.status) {
                toastr.error(data.errorMessages);
            } else {
                setLeaveTable(data.payload[0])
            }
        }
    });
}

function setLeaveTable(posts) {
    console.log(posts)
    if ($.isEmptyObject(posts)) {
        $('#leaveTable').DataTable().clear();
        $('#leaveTable').DataTable({
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
        $("#leaveTable").DataTable({
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
                    title: 'Leave Details',
                    pageSize: 'A4'
                },
                {
                    extend: 'excel',
                    title: 'Leave Details',
                    pageSize: 'A4'
                },
                {
                    extend: 'print',
                    title: 'Leave Details',
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
                "data": "leaveUser.empNumber"
                },
                {
                    "data": "reason"
                },
                {
                    "data": "leaveDate"
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

function clearLeave() {
    $('#idLeave').val('');
    $('#emp_Number').val('');
    $('#reason').val('');
    $('#status').val('');
    $('#leaveDate').val('');
}