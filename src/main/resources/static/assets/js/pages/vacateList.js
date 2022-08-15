function generateVacateList() {
    $.ajax({
        url: "/vacateController/",
        type: "POST",
        data: {},
        success: function(data) {
            if(data.status) {
                loadVacateTable();
            } else {
                toastr.error(data.errorMessages);
            }
        }
    });
}

function loadVacateTable() {
    $.ajax({
        url: "/vacateController/",
        type: "GET",
        data: {},
        success: function(data) {
            if(!data.status) {
                toastr.error(data.errorMessages);
            } else {
                setVacateTable(data.payload[0])
            }
        }
    });
}

function setVacateTable(posts) {
    console.log(posts)
    if ($.isEmptyObject(posts)) {
        $('#vacateListTable').DataTable().clear();
        $('#vacateListTable').DataTable({
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
        $("#vacateListTable").DataTable({
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
                    title: 'Vacate Details',
                    pageSize: 'A4'
                },
                {
                    extend: 'excel',
                    title: 'Vacate Details',
                    pageSize: 'A4'
                },
                {
                    extend: 'print',
                    title: 'Vacate Details',
                    pageSize: 'A4'
                }
            ],
            "destroy": true,
            "language": {
                search: "",
                searchPlaceholder: "Search..."
            },
            "data": posts,
            "columns": [
                {
                    "data": "employee.empNumber"
                },
                {
                    "data": "employee.name"
                },
                {
                    "data": "employee.departmentDTO.departmentTitle"
                },
                {
                    "data": "employee.mobile"
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