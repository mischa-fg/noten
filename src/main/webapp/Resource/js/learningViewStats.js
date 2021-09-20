/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
initLearningView();
function initLearningView() {
    var uri = "http://localhost:8080/Notentool/apiRueck/rueckmeldung/learningView";
    fetch(uri)
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                //makeChart
                makeChart(data);
            });
}

function makeChart(data) {
    var div = document.getElementById("output");
    //Canvas generieren
    data.forEach(item => {
        var divCanv = document.createElement("div");
        divCanv.style.height = "500px";
        divCanv.style.width = "500px";
        var canvas = document.createElement("canvas");
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
                plugins: {
                    title: {
                        display: true,
                        text: item.frage
                    }
                }}


        });
        divCanv.appendChild(canvas);
        var p = document.createElement("p");
        var s = 0;
        item.wertAnzahl.forEach(wert =>{
            s += wert;
        });
        p.innerHTML = "Anzahl Werte: " + s;
        
        div.appendChild(divCanv);
        div.appendChild(p)
    });
    //Chart erstellen und dem Canvas zuordenen
}
