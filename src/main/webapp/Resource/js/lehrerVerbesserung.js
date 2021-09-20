/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function initLehrerKlasse() {
    fetch("apiRueck/rueckmeldung/dozenten")
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                initLehrerSelect(data);
                initModuleLehrer();
            })
}

function initLehrerSelect(data) {
    var sel = document.getElementById("lehrerSelect");
    data.forEach(item => {
        var opt = document.createElement("option");
        opt.value = item.id;
        opt.text = item.vorname + " " + item.name;
        sel.add(opt, null);
    });
}

function initModuleLehrer() {
    var lid = document.getElementById("lehrerSelect").value;
    var uri = "http://localhost:8080/Notentool/apiRueck/rueckmeldung/lehrerModule?dozent=" + lid;
    fetch(uri)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                initModulSelect(data);
                makeAusgabe()
            })
}
function initModulSelect(data) {
    var sel = document.getElementById("modulSelect");
    while (sel.options.length > 0) {
        sel.remove(0);
    }
    data.forEach(item => {
        var opt = document.createElement("option");
        opt.value = item.id;
        opt.text = item.bezeichnung;
        sel.add(opt, null);
    });
}

function makeAusgabe() {
    var lid = document.getElementById("lehrerSelect").value;
    var mid = document.getElementById("modulSelect").value;
    var uri = "http://localhost:8080/Notentool/apiRueck/rueckmeldung/lehrerModuleWertung?dozent=" + lid + "&modul=" + mid;
    document.getElementById("output").innerHTML = "";
    document.getElementById("kritikLern").innerHTML = "";
    fetch(uri)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                if (data.length == 0) {
                    var row = document.createElement("div");
                    row.setAttribute("class", "row");
                    var text = document.createTextNode("Bei jeder Frage ist der Wert über 7");
                    row.appendChild(text);
                    document.getElementById("output").appendChild(row);
                    lobLink();
                } else {
                    doTabelleVerbesserung(data);
                }
            });
}

function doTabelleVerbesserung(data) {
    var divStart = document.getElementById("output");
    data.forEach(item => {
        var row = document.createElement("div");
        row.setAttribute("class", "row");
        var colFrage = document.createElement("div");
        colFrage.setAttribute("class", "col-8");
        var frage = document.createTextNode(item.frage);
        colFrage.appendChild(frage);

        var colDurch = document.createElement("div");
        colDurch.setAttribute("class", "col-4");
        var durch = document.createTextNode("Wert: " + item.durchschnitt);
        colDurch.appendChild(durch);
        row.appendChild(colFrage);
        row.appendChild(colDurch);
        divStart.appendChild(row);
    });
    lobLink();
}

function lobLink(){
    var a = document.createElement("a");
    var att = "kritikLernende()";
    var text = document.createTextNode("Kritik/Lob Lernende");
    a.appendChild(text);
    a.href = "javascript: void (0)";
    a.setAttribute("onclick", att);
    document.getElementById("output").appendChild(a);
}

function kritikLernende(){
    var lid = document.getElementById("lehrerSelect").value;
    var mid = document.getElementById("modulSelect").value;
    var uri = "http://localhost:8080/Notentool/apiRueck/rueckmeldung/kritikLernende?dozent=" + lid + "&modul="+mid;
    fetch(uri)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                tabelleKritik(data);
            });
}
function tabelleKritik(data){
    var divKritik = document.getElementById("kritikLern");
    divKritik.innerHTML = "";
    data.forEach(item => {
        var row = document.createElement("div");
        row.setAttribute("class", "row");
        var colName = document.createElement("div");
        colName.setAttribute("class", "col-2");
        var name = document.createTextNode(item.vorname + " " + item.nachname);
        colName.appendChild(name);
        
        var colFrage = document.createElement("div");
        colFrage.setAttribute("class", "col-4");
        var frage = document.createTextNode(item.frage);
        colFrage.appendChild(frage);
        
        var colAntwort = document.createElement("div");
        colAntwort.setAttribute("class", "col-6");
        var antwort = document.createTextNode(item.antwort);
        colAntwort.appendChild(antwort);
        
        row.appendChild(colName);
        row.appendChild(colFrage);
        row.appendChild(colAntwort);
        
        divKritik.appendChild(row);
    });
}

