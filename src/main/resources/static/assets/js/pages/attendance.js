function fileValidation() {
    var fileInput =
        document.getElementById('uploadFile');

    var filePath = fileInput.value;

    // Allowing file type
    var allowedExtensions =
        /(\.txt)$/i;

    if (!allowedExtensions.exec(filePath)) {
        toastr.error('Invalid file type');
        fileInput.value = '';
        return false;
    }
    var form = new FormData();
    form.append("file", $("#uploadFile")[0].files[0]);
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/upload",
        data: form,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data.status) {
                document.getElementById("uploadFile").value=null;

                toastr.success("Successfully saved.");
                loadAttendanceTable();
            }else {
                document.getElementById("uploadFile").value=null;
                toastr.error(data.errorMessages);
            }

        }
    });
}

function loadAttendanceTable() {
    $.ajax({
        url: "/attendanceController/",
        type: "GET",
        data: {},
        success: function(data) {
            if(!data.status) {
                toastr.error(data.errorMessages);
            } else {
                setAttendanceTable(data.payload[0])
            }
        }
    });
}

function setAttendanceTable(posts) {
    console.log(posts)
    if ($.isEmptyObject(posts)) {
        $('#attendanceTable').DataTable().clear();
        $('#attendanceTable').DataTable({
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
        $("#attendanceTable").DataTable({
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
                    title: 'Attendance Details',
                    pageSize: 'A4'
                },
                {
                    extend: 'excel',
                    title: 'Attendance Details',
                    pageSize: 'A4'
                },
                {
                    extend: 'print',
                    title: 'Attendance Details',
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
                    "data": "user.empNumber"
                },
                {
                    "data": "attendance",
                    mRender:function (data) {
                        var classLb = ''
                        if(data == "PRESENT")
                            classLb = 'badge badge-success'
                        if(data == "ABSENT")
                            classLb = 'badge badge-danger'
                        if (data == "LEAVE")
                            classLb = 'badge badge-info'
                        if (data == "PRESENT_IN_HOLIDAY")
                            classLb = ' badge badge-warning'

                        var columnHtml = '<td><label class="'+classLb+'">'+data+'</label></td>';
                        return (columnHtml);
                    }
                },
                {
                    "data": "date"
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