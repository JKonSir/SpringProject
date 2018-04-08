function buildTableBody(data) {

    var tableBody = document.createElement('tableBody');

    addTableHeader(tableBody);

    for (var i = 0; i < data.length; i++) {
        var entry = data[i];

        var tr = document.createElement('tr');

        var td = document.createElement('td');
        td.appendChild(document.createTextNode(new Date(entry.time).toString()));
        tr.appendChild(td);

        var td = buildRequestCell(entry.request, i);
        tr.appendChild(td);

        if (!!entry.request.requestBody) {
            var td = buildRequestBodyCell(entry.request, i);
            tr.appendChild(td);
        } else {
            var td = document.createElement('td');
            td.appendChild(document.createTextNode(""));
            tr.appendChild(td);
        }

        var td = buildStatusCodeCell(entry.response.statusCode);
        tr.appendChild(td);

        if (JSON.parse(entry.response.responseBody).error != null) {
            var td = document.createElement('td');
            td.appendChild(document.createTextNode(""));
            tr.appendChild(td);

            var td = buildErrorCell(entry.response.responseBody, i);
            tr.appendChild(td);
        } else {
            var td = buildResponseBodyCell(entry.response, i);
            tr.appendChild(td);

            var td = document.createElement('td');
            td.appendChild(document.createTextNode(""));
            tr.appendChild(td);
        }

        tableBody.appendChild(tr);
    }

    return tableBody;
}

function addTableHeader(tableBody) {
    var listTableHeaders = ['Time', 'Request', 'Body', 'Status', 'Response', 'Error'];

    var tr = document.createElement('tr');

    listTableHeaders.forEach(function (columnHead) {
        var th = document.createElement('th');
        th.appendChild(document.createTextNode(columnHead));
        tr.appendChild(th);
    });

    tableBody.appendChild(tr);

}

function buildRequestCell(request, index) {
    var td = document.createElement('td');

    td.appendChild(
        document.createTextNode(request.httpMethod + " " + getLocation(
            request.uri).pathname));

    var requestHeaderSpan = document.createElement('span');
    requestHeaderSpan.setAttribute('onclick',
        "showPopup('Headers', 'log_head_" + index + "')");
    var br = document.createElement('br');
    requestHeaderSpan.appendChild(br);
    requestHeaderSpan.appendChild(document.createTextNode('(headers)'));
    td.appendChild(requestHeaderSpan);

    var requestHeaderParagraph = document.createElement('p');
    requestHeaderParagraph.setAttribute('id', 'log_head_' + index);
    requestHeaderParagraph.appendChild(
        document.createTextNode(JSON.stringify(request.httpHeaders, null, 2)));

    td.appendChild(requestHeaderParagraph);

    return td;
}

function buildRequestBodyCell(request, index) {
    var td = document.createElement('td');

    var requestBodySpan = document.createElement('span');
    requestBodySpan.setAttribute('onclick',
        "showPopup('RequestBody', 'log_request_body_" + index + "')");
    requestBodySpan.appendChild(document.createTextNode('(request body)'));
    td.appendChild(requestBodySpan);

    var requestBodyParagraph = document.createElement('p');
    requestBodyParagraph.setAttribute('id', 'log_request_body_' + index);
    requestBodyParagraph.appendChild(document.createTextNode(
        JSON.parse(JSON.stringify(request.requestBody, null, 2))));

    td.appendChild(requestBodyParagraph);

    return td;
}

function buildStatusCodeCell(statusCode) {
    var td = document.createElement('td');
    var statusCodeDiv = document.createElement('div');
    statusCode < 400 ? statusCodeDiv.setAttribute('style', 'color: green;')
                     : statusCodeDiv.setAttribute('style', 'color: red;');
    statusCodeDiv.appendChild(document.createTextNode(statusCode));
    td.appendChild(statusCodeDiv);

    return td;
}

function buildResponseBodyCell(response, index) {
    var td = document.createElement('td');

    var responseBodySpan = document.createElement('span');
    responseBodySpan.setAttribute('onclick',
        "showPopup('ResponseBody', 'log_response_body_" + index + "')");
    responseBodySpan.appendChild(document.createTextNode('(response body)'));
    td.appendChild(responseBodySpan);

    var responseBodyParagraph = document.createElement('p');
    responseBodyParagraph.setAttribute('id', 'log_response_body_' + index);
    responseBodyParagraph.appendChild(document.createTextNode(
        JSON.parse(JSON.stringify(response.responseBody, null, 2))));

    td.appendChild(responseBodyParagraph);

    return td;
}

function buildErrorCell(error, index) {
    var td = document.createElement('td');

    var errorJson = JSON.parse(error);

    td.appendChild(document.createTextNode(errorJson.error));
    td.appendChild(document.createElement('br'));

    td.appendChild(document.createTextNode(errorJson.exception));
    td.appendChild(document.createElement('br'));

    var errorSpan = document.createElement('span');
    errorSpan.setAttribute('onclick',
        "showPopup('Error', 'log_error_" + index + "');");
    errorSpan.appendChild(document.createTextNode('>>'));
    td.appendChild(errorSpan);

    var errorParagraph = document.createElement('p');
    errorParagraph.setAttribute('id', 'log_error_' + index);
    errorParagraph.innerHTML = JSON.stringify(JSON.parse(error), undefined, '\t');

    td.appendChild(errorParagraph);

    return td;
}

function getLocation(href) {
    var l = document.createElement("a");
    l.href = href;

    return l;
}