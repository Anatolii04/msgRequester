var ajaxUrlAccidents = 'ajax/accidents/';
var datatableApi;

function updateTable() {
    $.get(ajaxUrlAccidents, updateTableByData);
}

// $(document).ready(function () {
$(function () {

    datatableApi = $('#accidents').DataTable({

        "ajax": {
            "url": ajaxUrlAccidents,
            "dataSrc": ""
        },
        "pagingType": "full",
        "info": true,
        "dom":"rtlpi",
        "columns": [
            {
                "data": "node"
            },
            {
                "data": "timestamp"
            },
            {
                "data": "actual"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderAckBtn
            }
        ],
        "columnDefs": [
            {
                "render":function (data,type,row) {
                    var date = new Date(data * 1000);
                    date.setYear("2018")
                    return date;
                },
                "targets": [ 1 ]
            }
        ],
        "order": [
            [
                2,
                "desc"
            ]
        ],
        "initComplete": makeEditable
    });
    var table = $('#datatable').dataTable().api();
    $('#search-box-0').on('keyup change', function () {
        table.search(this.value).draw();
    });
    // $.datetimepicker.setLocale(localeCode);
});