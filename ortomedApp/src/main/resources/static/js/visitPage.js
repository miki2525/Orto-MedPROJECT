$(function(){
    
   $("#eye").on("click", function(){
     
       if(($("#pass").attr("type")) == "password"){
           $("#pass").attr("type", "text");
       }
       else{
           $("#pass").attr("type", "password");
       }
   });
    
var obj;
function showVisit() {
    var xhttp = new XMLHttpRequest();
    var email = $("#email").val();
    var pass = $("#pass").val();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            obj = JSON.parse(this.responseText);
            if (obj.id != null) {
                $("#fname").html(obj.firstName);
                $("#lname").html(obj.lastName);
                $("#date").html(obj.dateOfVisit);
                $("#hour").html(obj.timeOfVisit);
                $("#doc").html(obj.doctor);
                $("#tableplace").slideDown("slow");
                document.getElementById("delete").addEventListener("click", deleteVisit);
                document.getElementById("change").addEventListener("click", changeVisit);
            } else {
                $("#tableplace").slideDown("slow").html("Brak wyników. Sprawdź poprawność danych.")
            }
        }
    }
    xhttp.open("POST", "/api/showVisit", true)
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhttp.send("email=" + email + "&pass=" + pass);
}

function deleteVisit() {

    if (window.confirm("Czy napewno chcesz anulować swoją wizytę?")) {
        xhttp = new XMLHttpRequest();
        xhttp.open("POST", "api/delete", true);
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(JSON.stringify(obj));
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                if (xhttp.responseText) {
                    $("#table").remove();
                    $("#tableplace").slideDown("slow").html("Twoja wizyta została anulowana");
                }
            }
        }
    }
}

function changeVisit(event) {
    var dontRepeat = false;
    event.preventDefault();
    if (!dontRepeat) {
        dontRepeat = true;
        if (window.confirm("Czy napewno chcesz zmienić termin swojej wizyty?")) {
            xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    var index = window.opener;
                    index.document.write(xhttp.responseText);
                    window.parent.close();
                    $("#loading").hide();
                } else {
                    $("#loading").show();
                    $("#loading").animate({height: '300px', opacity: '0.4'}, "slow");
                }

            }
            xhttp.open("POST", "/registration", true);
            xhttp.setRequestHeader("Content-Type", "application/json");
            xhttp.send(JSON.stringify(obj));
        }
    }
}
    });