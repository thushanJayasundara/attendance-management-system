
function saveHoliday() {
    var leaveInsertDTO = {
        "id": $('#idHoliday').val() == "" ? null : $('#idHoliday').val(),
        "reason":$('#holidayReason').val(),
        "holiday":$('#holiday').val(),
        "commonStatus":$('#holidayStatus').val(),
    }

    $.ajax({
        url: "/holiday/",
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
                loadHolidayTable();
                toastr.success("Successfully save.");
            }
        }
    });
}


function searchHoliday() {

    var search_Date = $('#search_toDate').val()

    if(search_Date=="")
        toastr.error("Please enter valid date.");

    var url = "/holiday/find-by-date/"+search_Date
    $.ajax({
        url: url,
        type: "GET",
        data: {},
        success: function(data) {
            if(!data.status) {
                toastr.error(data.errorMessages);
            } else {
                $('#idHoliday').val(data.payload[0].id);
                $('#holidayReason').val(data.payload[0].reason);
                $('#holiday').val(data.payload[0].holiday);
                $('#holidayStatus').val(data.payload[0].commonStatus);
            }
        }
    });
}

function loadHolidayTable() {
    $.ajax({
        url: "/holiday/get-all",
        type: "GET",
        data: {},
        success: function(data) {
            if(!data.status) {
                toastr.error(data.errorMessages);
            } else {
                setHolidayTable(data.payload[0])
            }
        }
    });
}

function setHolidayTable(posts) {
    console.log(posts)
    if ($.isEmptyObject(posts)) {
        $('#holidayTable').DataTable().clear();
        $('#holidayTable').DataTable({
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
        $("#holidayTable").DataTable({
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
                    title: 'Holiday Details',
                    pageSize: 'A4'
                },
                {
                    extend: 'excel',
                    title: 'Holiday Details',
                    pageSize: 'A4'
                },
                {
                    extend: 'print',
                    title: 'Holiday Details',
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
                        "data": "holiday"
                    },
                    {
                        "data": "reason"
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

function clearHoliday() {
    $('#idHoliday').val('');
    $('#holidayReason').val('');
    $('#holiday').val('');
    $('#holidayStatus').val('');
}