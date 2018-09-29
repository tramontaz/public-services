// $(document).ready(function() {
//     $('#table').DataTable( {
//         "processing": true,
//         "serverSide": true,
//         "ajax": "/applications/getAll"
//     } );
// } );

$(document).ready( function () {
    var table = $('#table').DataTable({
        "serverSide": true,
        "ajax": function (query, callback, setting) {
            $.ajax({
                url: '/applications/getPages',
                data: query,
                method: "GET",
                dataType: "json",
                success: function(response) {
                    callback(response);
                }
            })
        },
        "order": [[ 0, "asc" ]],
        "aoColumns": [
            { "mData": "number"},
            { "mData": "date" },
            { "mData": "fio" },
            { "mData": "dob" },
            { "mData": "phoneNumber" },
            { "mData": "email" },
            { "mData": "nameOfService" },
            { "mData": "nameOfDepartment" }
        ]
    })
});