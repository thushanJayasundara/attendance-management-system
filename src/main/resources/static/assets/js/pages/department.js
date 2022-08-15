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
                loadDepartmentTable();
                toastr.success("Successfully saved.");

            }
        }
    });
}

function searchDepartment() {
    var departmentTitle = $('#search_Dep').val()
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

function loadDepartmentTable() {
    $.ajax({
        url: "/department/get-all",
        type: "GET",
        data: {},
        success: function(data) {
            if(!data.status) {
                toastr.error(data.errorMessages);
            } else {
                setDepartmentTable(data.payload[0])
            }
        }
    });
}

function setDepartmentTable(posts) {
    console.log(posts)
    if ($.isEmptyObject(posts)) {
        $('#departmentTable').DataTable().clear();
        $('#departmentTable').DataTable({
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
        $("#departmentTable").DataTable({
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
                    title: 'Department Details',
                    pageSize: 'A4'
                },
                {
                    extend: 'excel',
                    title: 'Department Details',
                    pageSize: 'A4'
                },
                {
                    extend: 'print',
                    title: 'Department Details',
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
                    "data": "departmentTitle"
                },
                {
                    "data": "departmentDescription"
                },
                {
                    "data": "departmentContactNumber"
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

function  clearDepartment(){
    $('#depTit').val('');
    $('#idDep').val('');
    $('#depDes').val('');
    $('#depCon').val('');
    $('#statusDep').val('');
}