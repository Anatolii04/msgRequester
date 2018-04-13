var form;
var formContact;
var formEmail;

function makeEditable() {
    form = $('#detailsForm');
    formContact = $('#detailsFormContact');
    formEmail = $('#emailForm');
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({ cache: false });

    //csrf tokens for all ajaxQuerys
    // var token = $("meta[name='_csrf']").attr("content");
    // var header = $("meta[name='_csrf_header']").attr("content");
    // $(document).ajaxSend(function(e, xhr, options) {
    //     xhr.setRequestHeader(header, token);
    // });
}

/*connector functions*/

function add() {
    $('#modalTitle').html(i18n["addTitle"]);
    form.find(":input").val("");
    $('#editRow').modal();
}

function updateRow(id) {
    $('#modalTitle').html(i18n["editTitle"]);
    $.get(ajaxUrlConnector + id, function (data) {
        $.each(data, function (key, value) {
            // debugger;
            form.find("input[name='" + key + "']").val(value);
        });
        $('#editRow').modal();
    });
}

function deleteRow(id) {
    var c = confirm(i18n["confirm"]);
    if(c){
        $.ajax({
            type: 'DELETE',
            url: ajaxUrlConnector + id,
            success: function () {
                updateTable();
                successNoty('common.deleted');
            }
        });
    }
    else if(!c) return;
}


function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}

function save() {
    // debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrlConnector,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('common.saved');
        }
    });
}

function renderEditBtn(data, type, row) {
    if (type === 'display') {
        return '<a onclick="updateRow(' + row.id + ');" title="' + i18n["connector.edit"]+'">' +
            '<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>';
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === 'display') {
        return '<a onclick="deleteRow(' + row.id + ');" title="' + i18n["connector.delete"]+'">'+
            '<span class="glyphicon glyphicon-remove" aria-hidden="true" ></span></a>';
    }
}

/*contact functions*/

function renderDropdownContact(connectorId, id, contact, description){
    return  '<div class="panel panel-default"><div class="dropdown panel-heading">' +
        '<button class="btn btn-link dropdown-toggle" type="button" data-toggle="dropdown">' +
        // '<span class="glyphicon glyphicon-cog"></span>' +
        '<span class="caret"></span></button>' +
        '<ul class="dropdown-menu">' +
        '<li><a class="dropdown-item">'+
        '<span class="glyphicon glyphicon-pencil" aria-hidden="true"' +
        'onclick="updateRowContact('+ connectorId +','+ id +')">'+i18n['common.edit'] + '</span>'+
        '</a></li>'+
        '<li><a class="dropdown-item" >'+
        '<span class="glyphicon glyphicon-remove" aria-hidden="true" onclick="deleteRowContact(' + connectorId + ', ' + id + ');" >' +
        i18n['common.delete'] +
        '</span>'+
        '</a></li>'+
            //
        '<li><a class="dropdown-item">'+
        '<span class="glyphicon glyphicon-ok" onclick="copyContact('+ id + ',' + connectorId + ')" aria-hidden="true" id="copy-contact-bt-'+ id +'"' +
        'onclick="">' + '</span>'+
        '<input type="text" class="form-control input-sm" id="copy-connectorId-'+ id +'" placeholder="'+ i18n["contact.copy"]+'" size="13">'+
        '</a></li>'+

        '</ul>' +
        '<span class="">' + contact + '</span>'+//important
        '</div>'+
        '<div class="panel-body small"> ' + description + "</div></div>";

}

function copyContact(id,from) {
    var connectorId = $('#copy-connectorId-'+id).val();
    $.ajax({
        url: ajaxUrlContact + 'copy',
        data: "id="+id+"&from="+from + "&connectorId="+connectorId,
        type: 'POST',
        success: function () {
            updateTable();
            successNoty('common.copy');
        }
    });
}

function deleteRowContact(connectorId, id) {
    var c = confirm(i18n["confirm"]);
    if(c){
        $.ajax({
        url: ajaxUrlContact + connectorId + '/' + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('common.deleted');
        }
        });
    }
    else if(!c) return;
}

function updateRowContact(connectorId,id) {
    $('#modalTitleContact').html(i18n["editTitleContact"]);
    $.get(ajaxUrlContact + connectorId + '/' + id, function (data) {
        $.each(data, function (key, value) {
            if(key=='description'){
                formContact.find("textarea[name='" + key + "']").val(value);
            } else {
                formContact.find("input[name='" + key + "']").val(value);
            }
        });
        $('#editRowContact').modal();
    });
    $('#connectorId').val(connectorId);
}

function contactRenderCaret(data, type, row) {
    if (type === 'display') {
        var s = new Array();
        for (var i = 0; i < data.length; i++) {
            // debugger;
            s[i] = renderDropdownContact(row.id, data[i].id,data[i].contact,data[i].description);
            // debugger;
        }
        return s.join("");
    }
    return data;
}



function renderAddContactBtn(data, type, row) {
    if (type === 'display') {
        return '<a class="btn btn-link" onclick="addContact(' + row.id +');" title="' + i18n["contact.add"]+'">'+
        '<span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>';

    }
}

function addContact(connectorId) {
    //add title
    $('#modalTitleContact').html(i18n["addTitleContact"]);
    //find all input tag and clear values
    formContact.find(":input").val("");
    //call modal form editRowContact
    $('#editRowContact').modal();
    //save connectorId in input tag with id=connectorId
    $('#connectorId').val(connectorId);
}


function saveContact() {
    var connectorId = $('#connectorId').val();
    var d = formContact.serialize();
    debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrlContact + connectorId,
        data: d,
        success: function () {
            $('#editRowContact').modal('hide');
            updateTable();
            successNoty('common.saved');
        }
    });

}

function renderSendMailBtn(data, type, row) {
    if (type === 'display') {
        return '<a class="btn btn-link" id="sendEmailButton" onclick="makeEmailsCheckboxex('+ row.id +');" title="' + i18n["send.email"]+'">'+
                '<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></a>';
    }
}


function makeEmailsCheckboxex(connectorId) {
    removeTagsEmails();
    removeSendEmailsButton();
    $('#modalTitleMailList').html(i18n["emailList"]);
    var counter = 0;
    $.get(ajaxUrlContact + connectorId, function (data) {
        debugger;
        if(data.length>0){
            $.each(data, function (key, value) {
                debugger;
                 if(data[key].contact.indexOf('@')!=-1){
                     debugger;
                    $('#checkbox-list').append(
                        '<div class="form-group "><div class="checkbox col-xs-offset-2">' +
                        '<div class="col-xs-0">' +
                        '<input type="checkbox" id="'+ 'checkbox-'+counter +'" onclick="$(this).val(this.checked ? 1 : 0)" value="">' +
                        '</div>' +
                        '<label for="'+ 'checkbox-'+ counter +'" class="col-xs-7 ">'+data[key].contact+'</label>' +
                        '</div></div>'
                    );counter++;
                 }
            });
            if($("#checkbox-list :input").length==1){
                $("#checkbox-list :input").each(function(){
                    $(this).click();
                })
            }
            //TODO нужно переместить в jsp c event
            $('#sndEmlBtn').append(
              '<button type="button"  onclick="'+'setValuesInEmailMess('+connectorId+')'+'" class="btn btn-primary" id="send-to-form">'+
              '<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>'+
              '</button>'
            );
            var emailTagslLength = $("#checkbox-list :input").length;
            if(emailTagslLength>1){
                $('#emailListModal').modal({backdrop: "static"});
            } else if(emailTagslLength==1){
                setValuesInEmailMessWithoutModal(connectorId);
            }
            else{
                infoNoty(i18n["info.noemails"]);
            }
            autofillSubj(connectorId);
        }else {
            infoNoty(i18n["info.nocontacts"]);
        }
    });

}

// if(attributes.length>1) {//этот if нужно в createNmeListCheckboxex
//createNameListCheckboxex был помещен в toBeOrNotConnectorCheckboxex
//изначально было условие построенное на длине attributes.
// Перенесено в toBeOrNotConnectorCheckboxex
//Данные из форм не вычищаются после заполнения
//main meth for send email
var attributes;
var operatorEmails;
function setValuesInEmailMess(connectorId){

    debugger;
    operatorEmails = getEmailsList();
    var x = jQuery.param(operatorEmails);

    if(Object.keys(operatorEmails).length>0) {
        $.ajax({
            type: "POST",
            url: ajaxUrlEmail + "data/"+connectorId,
            data: x,
            success:function (data) {
                // removeTagsConnectors();
                attributes = JSON.parse(data);
                $('#emailListModal').modal('hide');
                $('#emailListModal').on('hidden.bs.modal', function () {
                    var b = toBeOrNotConnectorCheckboxex(attributes);debugger;
                    if(b!=undefined){
                        debugger;
                        sendEmailsToForm(b, Object.values(operatorEmails));
                    }
                });
            }
        });
    }
    else {
        infoNoty(i18n["info.emailnotselect"]);
    }
}

function setValuesInEmailMessWithoutModal(connectorId) {
    operatorEmails = getEmailsList();
    var x = jQuery.param(operatorEmails);
    $.ajax({
        type: "POST",
        url: ajaxUrlEmail + "data/"+connectorId,
        data: x,
        success:function (data) {
            // removeTagsConnectors();
            attributes = JSON.parse(data);
            var b = toBeOrNotConnectorCheckboxex(attributes);debugger;
            if(b!=undefined){
                sendEmailsToForm(b,Object.values(operatorEmails));

            }
        }
    });
}

var close = false;
$(".nameList-close").on("click", function () {
    close = true;
    debugger;
});


function sendEmailsToForm(b, operatorEmailsValues) {
    debugger;
    var DELIMITER = "; ";
    if(b) {
        $('#nameListModal').on('hidden.bs.modal', function () {
            if(!close){
                debugger;
                $('#recipient').val(operatorEmailsValues.join(DELIMITER));
                $('#emailModal').modal({backdrop: "static"});
            }
        });

    } else if(!b) {
        // if(!close){
            debugger;
            $('#recipient').val(operatorEmailsValues.join(DELIMITER));
            $('#emailModal').modal({backdrop: "static"});
        // }
    }
    close = false;
}

function getEmailsList(){
    var arr = {};
    $("#checkbox-list :input").each(function(e){
        debugger;
        var value =$(this).val();
        if(value==1){
            debugger;
            var label = $("label[for='" + 'checkbox-'+ e + "']");
            arr[e] = label.text();
        }
    });
    // debugger;
    return arr;
}

function toBeOrNotConnectorCheckboxex(attributes){
    if(attributes.length>1){
        createNameListCheckboxex(attributes);
        return true;
    } else if(attributes.length==1){
        sendAttributesToForm(attributes);
        return false;
    }
}

function createNameListCheckboxex(attributes) {
    removeTagsConnectors();//!!!
    for (var i = 0; i < attributes.length; i++) {
        for (var key in attributes[i]) {
            if (key == 'name') {
                $('#name-list').append(
                    '<div class="form-group "><div class="checkbox col-xs-offset-2">' +
                    '<div class="col-xs-0">' +
                    '<input type="checkbox"   id="' + 'name-checkbox-' + i + '" onclick="$(this).val(this.checked ? 1 : 0)" value="">' +
                    '</div>' +
                    '<label for="' + 'name-checkbox-' + i + '" class="col-xs-7">' + attributes[i][key] + '</label>' +
                    '</div></div>'
                );
            }
        }
    }
    $('#nameListModal').modal({backdrop: "static"});
}

function sendAttributesToForm(selectedConnectors){
    var attrToForm = '';
    debugger;

    for(var i = 0;i < selectedConnectors.length;i++ ){
        for (var key in selectedConnectors[i]) {
            if (key == 'systemId' || key == 'smscAddr' || key == 'port'){ //|| key == 'name') {
                attrToForm += key + '=' + selectedConnectors[i][key] + '\n';
            }
        }
    }
    var techInfo = attrToForm ? "Параметры подключения:\n" + attrToForm : "";
    debugger;
    $('#message').val(techInfo + getSignature());
    $('#copy').val(getTechsupp());
}

function getMarkedNameConnectors() {
    var listNames = {};
    $("#name-list :input").each(function(e,value){
        var value =$(this).val();debugger;
        if(value==1){
            var label = $("label[for='" + "name-checkbox-"+ e + "']");
            listNames[e] = label.text();
        }
    });debugger;
    return listNames;
}

function sendAttributesBtn() {
    var listNames = getMarkedNameConnectors();
    var selectedConnectors = [];
    for(var i = 0; i < attributes.length;i++) {
        if(checkName(listNames,attributes[i]['name'])){
            selectedConnectors.push(attributes[i]);
        }
    }debugger;
    sendAttributesToForm(selectedConnectors);

    $('#nameListModal').modal('hide');
}

function checkName(arr, name){
    for(var i in arr){
        if(arr[i]==name){
            return true;
        }
    }
    return false;
}

function getSignature() {
    return SIGNA_RU;
}

function getTechsupp() {
    // return "a.utkin@i-free.com; "
    //disable on tests
    return "techsupp@i-free.com; ";
}

function autofillSubj(connectorID){
    $.get(ajaxUrlConnector + connectorID,function (data) {
        debugger;
        if(data.jurName=="i-Free"){
            $("#subject").val("Запрос от группы компаний "+ data.jurName);
        }else if(data.jurName==null);
        else $("#subject").val("Запрос от компании "+ data.jurName);
    });
}

function sendAjaxEmail(){
    $.ajax({
        type: "POST",
        url: "/ajax/email/send/",
        data: formEmail.serialize(),
        success: function (data) {
            $('#emailModal').modal("hide");
            successNotyText(i18n["email.success"]);
        }
    });
}

//remove all div elements from div with id=checkbox-list
function removeTagsEmails() {
    $('#checkbox-list').empty();
}
function removeSendEmailsButton() {
    $('#sndEmlBtn').empty();
}
function removeTagsConnectors() {
    $('#name-list').empty();
}

/*notifications functions*/

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    noty({
        text: i18n[key],
        type: 'success',
        layout: 'bottomRight',
        timeout: 4000
    });
}

function successNotyText(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: 4000
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: i18n['common.errorStatus'] + ': ' +jqXHR.status + (jqXHR.responseText ? '<br>' + jqXHR.responseText: ''),
        type: 'error',
        layout: 'bottomRight',
        progressBar: true,
        timeout: 15000
    });
}

function infoNoty(message) {
    closeNoty();
    noty({
        text: message,
        type: 'info',
        layout: 'bottomRight',
        timeout: 4000
    });
}

//Единственный первый пришедший в голову способ прервать  показ очереди 'modal' окон
//это обновить страницу


/*Events*/

//Очистка форм по event'у - божественно
$("#emailModal").on("hidden.bs.modal", function () {
    attributes = {};
    operatorEmails = {};
});

$('#emailListModal').on("hidden.bs.modal",function () {
    removeTagsEmails();
});
$('#nameListModal').on("hidden.bs.modal",function () {
    removeTagsConnectors();
});
// clear email form when creating email to operator started
$('#emailListModal').on("show.bs.modal",function () {
    attributes = {};
    operatorEmails = {};
    formEmail.find(":input").val("");
});


$("#subject-select").change(function () {
        var subjVal = $("#subject-select option:selected").text();
        $("#subject").val(subjVal);
    }
);
$("#copy-select").change(function () {
        var copyRecipVal = $("#copy").val() + $("#copy-select").val() + "; ";
        $("#copy").val(copyRecipVal);
    }
);


var DISCONNECT = "Здравствуйте!\n\nФиксируем отсутствие соединения с Вашим SMSC от [dd MMM hh:mm].\n" +
    "С нашей стороны проблем не обнаружено. Пожалуйста, проверьте работоспособность на вашей стороне.\n";
var NOTRAFFIC = "Здравствуйте!\n\nФиксируем отсутствие трафика с Вашим SMSC от [dd MMM hh:mm].\n" +
    "С нашей стороны проблем не обнаружено. Пожалуйста, проверьте работоспособность на вашей стороне.\n" ;
var DISCONNECT_ENG = "Hello!\n\nWe've registered disconnection via SMPP channel from [dd MMM hh:mm].\n" +
    "There's no any technical problems with system on our side.Please check system availability on your side.\n";
var INB_NOTRAFF = "Здравствуйте!\n\nФиксируем отсутствие входящих сообщений от абонентов с [dd MMM hh:mm].\n" +
"С нашей стороны проблем не обнаружено. Пожалуйста, проверьте работоспособность на вашей стороне.\n" ;
var OUT_NOTRAFF = "Здравствуйте!\n\nФиксируем отсутствие исходящих сообщений по КН ? с [dd MMM hh:mm].\n" +
    "С нашей стороны проблем не обнаружено. Пожалуйста, проверьте работоспособность на вашей стороне.\n" ;
var textProblems = [DISCONNECT,NOTRAFFIC,DISCONNECT_ENG,INB_NOTRAFF,OUT_NOTRAFF];
$("#problem-select").change(function () {
    var problemSelectVal = $(this).val();
    if(problemSelectVal=="disconnect"){
        var textMessage = getTextMessage(DISCONNECT);
    } else if(problemSelectVal=="notraffic"){
        var textMessage = getTextMessage(NOTRAFFIC);
    } else if(problemSelectVal=="disconnect-en"){
        var textMessage = getTextMessage(DISCONNECT_ENG);
    }else if(problemSelectVal=="notraffic-in"){
        var textMessage = getTextMessage(INB_NOTRAFF);
    }else if(problemSelectVal=="notraffic-out"){
        var textMessage = getTextMessage(OUT_NOTRAFF);
    }else if(problemSelectVal=="void"){
        var textMessage = $("#message").val();
        for(var i = 0;i < textProblems.length;i++){
           textMessage = textMessage.replace(textProblems[i],"").trim();
        }
        // var textMessage = $("#message").val().replace(DISCONNECT,"").replace(NOTRAFFIC,"").replace(DISCONNECT_ENG,"").trim();
    }
    $("#message").val(textMessage);
});

function getTextMessage(problem) {
    var text = problem + "\n" + $("#message").val();
    return text;
}

var SIGNA_RU= '\nС уважением,\ni-Free, Служба мониторинга и технической поддержки\n+7 911 100-14-57\ntechsupp@i-free.com';
var SIGNA_EN = "\nKind Regards,\ni-Free, Technical Support Department\n+7 911 100-14-57\ntechsupp@i-free.com";
$("#signa-select").change(function () {
    var signaSelectVal = $(this).val();
    if(signaSelectVal=="ru"){
        var textMessage = $("#message").val() + "\n" + SIGNA_RU;
    } else if(signaSelectVal=="en"){
        var textMessage = $("#message").val() + "\n" + SIGNA_EN;
    } else if(signaSelectVal=="void"){
        var textMessage = $("#message").val().replace(SIGNA_RU,"").replace(SIGNA_EN,"").trim();
    }
    $("#message").val(textMessage);
});

// accidents
function updateAccidentRow(id) {
    var c = confirm(i18n["confirm"]);
    if(c){
        $.ajax({
            type: 'POST',
            url: ajaxUrlAccidents + id,
            success: function () {
                updateTable();
                successNoty('common.deleted');
            }
        });
    }
    else if(!c) return;
}

function renderAckBtn(data, type, row) {
    if (type === 'display') {
        return '<a onclick="updateAccidentRow(' + row.id + ');" title="' + i18n["ackAccid"]+'">'+
            '<span class="glyphicon glyphicon-ok" aria-hidden="true" ></span></a>';
    }
}

