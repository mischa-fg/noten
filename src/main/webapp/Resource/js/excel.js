/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function registration() {
    var username = document.getElementById("usernameRegi").value;
    var pw = document.getElementById("passwort").value;
    var pwWiederholung = document.getElementById("passwortWiderholen").value;
    if (username.length >= 3 && pw.length >= 3) {
        if (pw == pwWiederholung) {
            var user = {
                username: username,
                passwort: pw
            };
            //alert(JSON.stringify(user));
            var uriRegi = "api/excel/registrierung";
            fetch(uriRegi, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user)
            })
                    .then(response => response.text())
                    .then(data => {
                        if (data != "") {
                            alert("Dein eimaliger API Key " + data);
                            window.location = "login.html";
                        } else {
                            document.getElementById("regiFehler").innerHTML = "Username ist schon vergeben.";
                        }


                    });
        } else {
            document.getElementById("regiFehler").innerHTML = "Passwörter müssen überreinstimmen.";
        }
    } else {
        document.getElementById("regiFehler").innerHTML = "Passwort und Username müssen mindestens 3 Zeichen lang sein.";
    }
    // alert("Hallo");
}

function login() {
var username = document.getElementById("usernameLogin").value;
        var pw = document.getElementById("passwortLogin").value;
        var user = {
        username: username,
                passwort: pw
        };
        var uriLogin = "api/excel/login";
        fetch(uriLogin, {
        method: 'POST',
                headers: {
                'Accept': 'application/json',
                        'Content-Type': 'application/json'
                },
                body: JSON.stringify(user)
        })
        .then(response => {
       // alert(response.status);
                if (response.status == 200){
                    window.location = "links.html";
                }else{
                    document.getElementById("loginFehler").innerHTML = "Username oder Passwort falsch."
                }
        });

//alert("Hallo");
}

