let form;

function makeEditable(datatableApi) {
    ctx.datatableApi = datatableApi;
    form = $('#detailsForm'); /*переменной form присвоили форму по id detailsForm*/
    $(".delete").click(function () {  /*на класс delete (с точкой т.к. класс) повесили событие click*/
        if (confirm('Are you sure?')) { /*если подтвердили то вызываем функцию deleteRow с параметром id из тега строки*/
            deleteRow($(this).closest('tr').attr("id"));
        }
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    /*$.ajaxSetup({cache: false});*/
}

function add() {
    form.find(":input").val("");
    $("#editRow").modal();
}

function deleteRow(id) {
    $.ajax({
        url: ctx.ajaxUrl + id, /*отсылаем запрос на сервер по URL запрос DELETE*/
        type: "DELETE"
    }).done(function () { /*если ответ положительный, то вызываем функцию updateTable() и подтверждающее сообщение*/
        updateTable();
        successNoty("Deleted");
    });
}

function updateTableByData(data) {
    ctx.datatableApi.clear().rows.add(data).draw();
}

function updateTable() {
    $.get(ctx.ajaxUrl, function (data) {  /*запрос по ajax по нужному url, если данные пришли, то вызываем callback*/
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}

function save() {
    $.ajax({  //запрос по ajax
        type: "POST", //тип запроса
        url: ctx.ajaxUrl, //url запроса
        data: form.serialize() //передаем сериализацию формы как параметры запроса
    }).done(function () { //в случае успеха
        $("#editRow").modal("hide"); //прячем диалоговое окно
        updateTable(); //обновляем таблицу
        successNoty("Saved");
    });
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;Error status: " + jqXHR.status,
        type: "error",
        layout: "bottomRight"
    });
    failedNote.show()
}