/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function initModulQuali() {
    fetch("apiRueck/rueckmeldung/dozenten")
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                initLehrerSelect(data);
                klassenSelect();
            });
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
function klassenSelect(){
    var lid = document.getElementById("lehrerSelect").value;
    var uri = " http://localhost:8080/Notentool/apiRueck/rueckmeldung/lehrerKlassen?dozent=" + lid;
    fetch(uri)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                initKlassenSelect(data);
                modulSelect();
            });
}
function initKlassenSelect(data){
    var sel = document.getElementById("klassenSelect");
    while (sel.options.length > 0) {
        sel.remove(0);
    }
    data.forEach(item => {
        var opt = document.createElement("option");
        opt.value = item.id;
        opt.text = item.klassenname;
        sel.add(opt, null);
    });
}
function modulSelect(){
    var lid = document.getElementById("lehrerSelect").value;
    var kid = document.getElementById("klassenSelect").value;
    var uri = " http://localhost:8080/Notentool/apiRueck/rueckmeldung/lehrerKlassenModule?dozent=" + lid +"&klasse=" + kid;
    fetch(uri)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                initModulSelect(data);
                initChart();
            });
}
function initModulSelect(data){
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

function initChart(){
    document.getElementById("output").innerHTML = "";
    var lid = document.getElementById("lehrerSelect").value;
    var kid = document.getElementById("klassenSelect").value;
    var mid = document.getElementById("modulSelect").value
    var uri = " http://localhost:8080/Notentool/apiRueck/rueckmeldung/lehrerKlasseModuleStats?dozent="+ lid +"&klasse="+ kid+ "&modul=" + mid;
    fetch(uri)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                drawChart(data);
                
            });
}
function drawChart(data){
    var div = document.getElementById("output");
    var wertBol= false;
    //Canvas generieren
    data.forEach(item => {
        var divCanv = document.createElement("div");
        //divCanv.style.height = "500px";
        //divCanv.style.width = "500px";
        divCanv.style.textAlign = "center";
        var canvas = document.createElement("canvas");
        canvas.height = "400";
        canvas.width = "400"
        divCanv.style.position = "center";
        var myChart = new Chart(canvas, {
            type: 'pie',
            data: {
                labels: item.wert,
                datasets: [{
                        label: '# of Votes',
                        data: item.wertAnzahl,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)',
                            'rgba(240,248,255,0.2)',
                            'rgba(255,193,193,0.2)',
                            'rgba(32,178,170,0.2)',
                            'rgba(255,20,147,0.2)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)',
                            'rgba(240,248,255,0.2)',
                            'rgba(255,193,193,0.2)',
                            'rgba(32,178,170,0.2)',
                            'rgba(255,20,147,0.2)'
                        ],
                        borderWidth: 1
                    }]
            },
            options: {
                maintainAspectRatio: false,
                plugins: {
                    title: {
                        display: true,
                        text: item.frage,
                        font: {
                            size: 14
                        }
                    }
                }}


        });
        divCanv.appendChild(canvas);
        var p = document.createElement("p");
        var s = 0;
        
        item.wertAnzahl.forEach(wert =>{
            wertBol = true;
            s += wert;
        });
        
        p.innerHTML = "Anzahl Werte: " + s;
        p.style.textAlign = "center";
        //durchschnitt
        var durch = 0.0;
        for(var i = 0; i< item.wert.length; i++){
            durch += item.wert[i] * item.wertAnzahl[i];
        }
        if(s>0){
            durch = durch / s;
            var pDurch = document.createElement("p");
            pDurch.innerHTML = "Durchschnitt: " + durch;
            pDurch.style.textAlign = "center";
            div.appendChild(divCanv);
            div.appendChild(p);
            div.appendChild(pDurch);
        }
        
    });
    if(!wertBol){
        document.getElementById("output").innerHTML = "Es gibt keine Werte";
    }
}

