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
function initYearSelect(data) {
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

function makeChart() {
    var kid = document.getElementById("klasseSelect").value;
    var jahr = document.getElementById("jahrSelect").value;
    var uri = "apiRueck/rueckmeldung/klassenUebersicht?klasse=" + kid + "&jahr=" + jahr;
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
    /*if(!isCanvasEmpty(document.getElementById('myChart'))){
     var context = canvas.getContext("2d");
     context.clearRect(0, 0, canvas.width, canvas.height);
     }*/
    if (mychart != null) {
        mychart.destroy();
    }
    mychart = new Chart(canvas, {
        type: 'line',
        data: {
            labels: label,
            datasets: [{
                    data: chart,
                    label: "Klassen Motivation",
                    borderColor: "#3e95cd",
                    fill: false
                }
            ]
        },
        options: {
            plugins: {
                title: {
                    display: true,
                    text: 'Klassen Bewertung'
                }
            },
            scales: {
                y: {
                    title: {
                        display: true,
                        text: "Durchschnittswert"
                    },
                    ticks: {
                        beginAtZero: true, // minimum value will be 0.
                        // <=> //
                        min: 0,
                        max: 10,
                        stepSize: 1 // 1 - 2 - 3 ...
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

