/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function fileSendFormData() {
    var file = document.getElementById("file").files[0];
    var formData = new FormData();
    formData.append("test", file);
    fetch("rest/file/readFileFormData", {
        method: "POST",
        body: formData
    }).then(function (response) {
        return response.text();
    }).then(function (data) {
        document.getElementById("ausgabe").innerHTML = data;
    });
}

function bilderAktualisieren() {
    fetch("rest/file/bildAktualisieren")
            .then(function (response) {
                return response.text();
            }).then(function (data) {
        document.getElementById("bild").innerHTML = data;
    });
}

function syncMail() {
    fetch("apiMail/mail/sync").then(function (response) {
        return response.text();
    }).then(function (data) {
        //alert(data);
    });
}
