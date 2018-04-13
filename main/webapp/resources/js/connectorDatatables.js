var ajaxUrlConnector = 'ajax/connectors/' + type + "/";
var ajaxUrlContact = 'ajax/contacts/' + type + "/";
var ajaxUrlEmail = 'ajax/email/' + type + "/";
var datatableApi;

function updateTable() {
    $.get(ajaxUrlConnector, updateTableByData);
}

// $(document).ready(function () {
$(function () {

    datatableApi = $('#datatable').DataTable({

        "ajax": {
            "url": ajaxUrlConnector,
            "dataSrc": ""
        },
        "paging": true,
        "pagingType": "full",
        "pageLength": 50,
        "info": true,
        "dom":"rtlpi",
        "columns": [
            {
                "data": "name",
                "width": "10%"
            },
            {
                "data": "systemId",
                "width": "10%"
            },
            {
                "data": "smscAddr"
            },
            {
                "data": "port"
            },
            {
                "data": "enabled"
            },
            {
                "data": "jurName"
            },
            {
                "orderable": false,
                "data": "contacts",
                "width": "80%",
                "render": contactRenderCaret
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderSendMailBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderAddContactBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "columnDefs": [
            {
                "render":function (data,type,row) {
                    return data + "(" + row.id +")";
                },
                "targets": [ 0 ]
            },
            {
                "render":function (data, type, row) {
                    return "<b>" + i18n["connector.sysId"] + ": </b><br>" + data + "<br>" +
                        "<b>" + i18n["connector.smscAddr"] + ": </b><br>" + row.smscAddr + "<br>" +
                        "<b>" + i18n["connector.port"] + ": </b>"  + row.port+ "<br>" +
                        "<b>" + i18n["connector.enabled"] + ": </b>" +row.enabled + "<br><hr>" +

                        "<b>" + i18n["connector.jurName"] + ": </b><br>" +row.jurName;
                },
                "targets": [ 1 ]
            },
            {
                "targets": [ 2 ],
                "visible": false
            },
            {
                "targets": [ 3 ],
                "visible": false
            },
            {
                "targets": [ 4 ],
                "visible": false
            },
            {
                "targets": [ 5 ],
                "visible": false
            }
        ],
        "order": [
            [
                4,
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