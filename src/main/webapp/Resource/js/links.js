/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
initLinks();
var links = [
    {"link": "http://localhost:8080/Notentool/api/excel/teilnehmerNoten?key=", "titel": "Alle Noten: "},
    {"link": "http://localhost:8080/Notentool/api/excel/teilnehmerUngNoten?key=", "titel": "Alle ungenügenden Noten: "},
    {"link": "http://localhost:8080/Notentool/api/excel/fehlendeNoten?key=", "titel": "Fehlende Noten: "}
];
var linksUname = [
    {"link": "http://localhost:8080/Notentool/api/excel/teilnehmerNoten?uname=", "titel": "Alle Noten: "},
    {"link": "http://localhost:8080/Notentool/api/excel/teilnehmerUngNoten?uname=", "titel": "Alle ungenügenden Noten: "},
    {"link": "http://localhost:8080/Notentool/api/excel/fehlendeNoten?uname=", "titel": "Fehlende Noten: "}
];
function generiereLinks(){
    /*var key = document.getElementById("key").value;
    var div = document.getElementById("links");
    div.innerHTML = "";
    links.forEach(element =>{
       var p = document.createElement("p");
       p.innerHTML = element.titel + element.link + key;
       div.appendChild(p);
    });*/
    var key = document.getElementById("key").value;
    var div = document.getElementById("links");
    div.innerHTML = "";
    linksUname.forEach(element =>{
       var p = document.createElement("p");
       p.innerHTML = element.titel + element.link + key;
       div.appendChild(p);
    });
      
}

function initLinks(){
    var uri = "api/excel/username";
    fetch(uri)
            .then(response => response.text())
            .then(data =>{
                linksUname.forEach(element=>{
                    element.link = element.link + data + "&key=";
                })
            })
}
