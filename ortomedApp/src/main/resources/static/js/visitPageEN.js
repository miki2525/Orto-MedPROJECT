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
document.getElementById("submit").addEventListener("click", showVisit);

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
                    $("#tableplace").slideDown("slow").html("\n" +
                        "No results. Check the correctness of the data.")
                }
            }
        }
        xhttp.open("POST", "/api/showVisit", true)
        xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhttp.send("email=" + email + "&pass=" + pass);
    }

function deleteVisit() {

    if (window.confirm("Are you sure you want to cancel your visit?")) {
        xhttp = new XMLHttpRequest();
        xhttp.open("POST", "api/delete", true);
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(JSON.stringify(obj));
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                if (xhttp.responseText) {
                    $("#table").remove();
                    $("#tableplace").slideDown("slow").html("Your visit has been canceled");
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
        if (window.confirm("Are you sure you want to reschedule your visit?")) {
            xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    if (window.opener != null) {
                        setTimeout(function () {
                            window.opener.parent.$("body").html(xhttp.responseText);
                            window.parent.close();
                            $("#loading").hide();
                        }, 3000);
                    } else {
                        setTimeout(function () {
                            $("#loading").hide();
                            $("body").html(xhttp.responseText);
                        }, 3000);
                    }
                }
                else {
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