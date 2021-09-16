/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function sendRueckmeldung() {
    var file = document.getElementById("rueckFile").files[0];
    var formData = new FormData();
    formData.append("rueckmeldung", file);
    fetch("apiRueck/rueckmeldung/readRueckmeldung", {
        method: "POST",
        body: formData
    }).then(function (response) {
        return response.text();
    }).then(function (data) {
        document.getElementById("ausgabe").innerHTML = data;
    });
}
//initStats
function initStatistiken() {
    fetch("apiRueck/rueckmeldung/klassen")
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                initKlassenSelect(data);
                yearSelect();
                
            })
}
function initKlassenSelect(data) {
    var sel = document.getElementById("klasseSelect");
    data.forEach(item => {
        var opt = document.createElement("option");
        opt.value = item.id;
        opt.text = item.klassenname;
        sel.add(opt, null);
    });
}

function yearSelect() {
    var kid = document.getElementById("klasseSelect").value;
    var uri = "apiRueck/rueckmeldung/klasseJahr?klasse=" + kid;
    fetch(uri)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                initYearSelect(data);
                makeChart();
            });

}
function initYearSelect(data){
    var sel = document.getElementById("jahrSelect");
     while (sel.options.length > 0) {                
        sel.remove(0);
    }  
    data.forEach(item => {
        var opt = document.createElement("option");
        opt.value = item;
        opt.text = item;
        sel.add(opt, null);
    });
}

function makeChart(){
    var kid = document.getElementById("klasseSelect").value;
    var jahr = document.getElementById("jahrSelect").value;
    alert(jahr);
    var uri = "apiRueck/rueckmeldung/klassenUebersicht?klasse=" + kid + "&jahr=" + jahr;
    fetch(uri)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                const label = [];
                const dataChart = [];
                for(var i = 0; i < data.length; i++){
                    label[i] = data[i].monat;
                    dataChart[i] = data[i].durchschnitt;
                    alert(label[i] + "  "+ dataChart[i]);
                }
                
            });
}

