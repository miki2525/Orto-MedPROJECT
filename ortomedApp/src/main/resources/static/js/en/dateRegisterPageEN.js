$(function(){
    if($("#id").val() > 0) {
        $("#update").text("Fill in the fields below to change the date. Changing the date is possible no later than one day before the planned visit.\n");
    }

    var today = new Date(Date.now());
    var yourDate;
    var dd = today.getDate();
    var mm = today.getMonth() + 1;
    var yyyy = today.getFullYear();
    var ddMin = dd + 1;
    var mmMax = mm + 1;

    if(dd < 10){
        dd= "0" + dd;
    }
    if (ddMin < 10){
        ddMin="0" + ddMin;
    }
    if(mm < 10){
        mm= "0" + mm;
    }
    if(mmMax < 10){
        mmMax = "0" + mmMax;
    }

    var min = yyyy + "-" + mm + "-" + ddMin; //rejestracja na nastepny dzien
    var max = yyyy + "-" + mmMax + "-" + dd; //rejestracja najdalej na za 1 miesiac
    var date = document.getElementById("date");
    date.setAttribute("min", min);
    date.setAttribute("max", max);


    date.addEventListener("change", function () {
        $("#submit").prop("disabled", false);

        var yourYyyy = date.value.toString().substr(0, 4);
        var yourMm = date.value.toString().substr(5, 2);
        var yourDd = date.value.toString().substr(8, 2);
        yourDate = new Date(yourYyyy, yourMm - 1, yourDd);
    })


    $("#form").on("submit", function () {
        if($("#doc").val() == null || $("#doc").val() == ""){
            alert("Please,choose the doctor");
            return false;
        }

        if($("#date").val() == null || $("#date").val() == ""){
            alert("Invalid date");
            return false;
        }
        if(yourDate.getDay() == 0 || yourDate.getDay() == 6){
            alert("Please, choose only weekdays");
            return false;
        }

        return true;

    });


});