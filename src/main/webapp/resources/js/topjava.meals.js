const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: mealAjaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}


$.datetimepicker.setLocale(localeCode.substring(0,2));
$.ajaxSetup({
    converters: {
        "text json": function( json_string ) {
            let json = JSON.parse(json_string);
            if (typeof json === 'object')
                $(json).each(function () {
                    if (this.hasOwnProperty('dateTime')) {
                        this.dateTime = this.dateTime.substr(0, 16).replace('T', ' ');
                    }
                });
            return json;
        }
    }
});

jQuery('#dateTime').datetimepicker({
    format:'Y-m-d H:i'
});


jQuery('#startDate,#endDate').datetimepicker({
    timepicker:false,
    format:'Y-m-d'
});

jQuery('#startTime,#endTime').datetimepicker({
    datepicker:false,
    format:'H:i'
});


$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    /*"render": function (date, type, row) {
                        if (type === "display") {
                            return date.replace("T", " ").substring(0, 16);
                        }
                        return date;
                    }*/
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
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
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                    $(row).attr("data-mealExcess", data.excess);
            }
        })
    );
});