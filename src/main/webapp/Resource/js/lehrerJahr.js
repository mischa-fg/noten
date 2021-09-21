/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function initLehrerJahr() {
    fetch("apiRueck/rueckmeldung/dozenten")
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                initLehrerSelect(data);
                initJahrLehrer();
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
function initJahrLehrer() {
    var did = document.getElementById("lehrerSelect").value;
    var uri = "http://localhost:8080/Notentool/apiRueck/rueckmeldung/lehrerJahr?dozent=" + did;
    fetch(uri)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                initJahrSelect(data);
                makeChart();
            });
}
function initJahrSelect(data) {
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
var monate = ["Januar", "Februar", "März", "April", "May", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"];
function makeChart() {
    var kid = document.getElementById("lehrerSelect").value;
    var jahr = document.getElementById("jahrSelect").value;
    var uri = "apiRueck/rueckmeldung/lehrerJahrUebersicht?dozent=" + kid + "&jahr=" + jahr;
    fetch(uri)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                const label = [];
                const dataChart = [];
                for (var i = 0; i < 12; i++) {
                    var k = i + 1;
                    var monat = false;
                    for (var j = 0; j < data.length; j++) {
                        if (k == data[j].monat) {
                            label[i] = data[j].monat;
                            dataChart[i] = data[j].durchschnitt;
                            monat = true
                        }
                    }
                    if (!monat) {
                        label[i] = k;
                        dataChart[i] = 0;
                    }
                }
                drawChart(label, dataChart);
            });
}
var mychart = null;
function drawChart(label, chart) {
    var canvas = document.getElementById('myChart');
    if (mychart != null) {
        mychart.destroy();
    }
    mychart = new Chart(canvas, {
        type: 'line',
        data: {
            labels: monate,
            datasets: [{
                    data: chart,
                    label: "Dozenten Übersicht",
                    borderColor: "#3e95cd",
                    fill: false
                }
            ]
        },
        options: {
            plugins: {
                title: {
                    display: true,
                    text: 'Dozenten Bewertung'
                }
            },
            scales: {
                y: {
                    title: {
                        display: true,
                        text: "Durchschnittswert"
                    },
                    min: 0,
                    max: 10,
                    ticks: {
                        stepSize: 1
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: "Monat"
                    }
                }

            }
        }

    });
}

function isCanvasEmpty(cnv) {
    const blank = document.createElement('canvas');

    blank.width = cnv.width;
    blank.height = cnv.height;

    return cnv.toDataURL() === blank.toDataURL();
}

