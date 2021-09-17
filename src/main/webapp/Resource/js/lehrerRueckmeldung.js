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
                makeChart();
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
function makeChart() {
    var lid = document.getElementById("lehrerSelect").value;
    var uri = "http://localhost:8080/Notentool/apiRueck/rueckmeldung/lehrerKlasse?dozent=" + lid;
    fetch(uri)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                const label = [];
                const dataChart = [];
                for (var i = 0; i < data.length; i++) {
                    label[i] = data[i].klassenname;
                    dataChart[i] = data[i].durchschnitt;
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
            labels: label,
            datasets: [{
                    data: chart,
                    label: "Bewertung Lehrer",
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
                yAxes: {
                        title: {
                            display: true,
                            text: "Durchschnittswert"
                        },
                        ticks: {
                            min: -100,
                            max: 100,
                            stepSize: 0.1
                        }
                    },
                xAxes: {
                    title: {
                        display: true,
                        text: "Klasse"
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
