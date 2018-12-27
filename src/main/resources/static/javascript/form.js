function submitForm() {

    clearErrorMessages();

    var from = $('#from').val();
    var to = $('#to').val();

    var fromTimestamp = !!new Date(from).getTime() ? new Date(from).getTime() : 0;
    var toTimestamp = !!new Date(to).getTime() ? new Date(to).getTime()
                                               : new Date().getTime();
    if (fromTimestamp > toTimestamp) {
        showError('integration_log',
                  '"From" cannot be more then "to"');
        return;
    }

    var limit = $('#entryLimit').val();
    if (limit < 0) {
        showError('entryLimit', 'Count cannot be less than zero');
        return;
    }

    var getUrl = "/integration-log?" + "interval=[" + fromTimestamp + ", " + toTimestamp
                 + "]&limit=" + limit;

    $.get(getUrl,
          function (data) {
              $('#table').empty();

              var tableBody = buildTableBody(data);
              $('#table').append(tableBody);
          });
}

function showError(field, errorMessage) {
    var errorSpan = document.createElement("span");
    errorSpan.setAttribute('id', 'errorSpan');
    var errorMessage = document.createTextNode(errorMessage);

    errorSpan.appendChild(errorMessage);
    errorSpan.className = "errorMsg";

    var fieldLabel = document.getElementById(field).previousSibling;
    while (fieldLabel.nodeName.toLowerCase() != "label") {
        fieldLabel = fieldLabel.previousSibling;
    }
    fieldLabel.appendChild(errorSpan);
}

function clearErrorMessages() {
    $('#errorSpan').empty();
}