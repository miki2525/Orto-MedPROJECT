$(function(){
    if($("#id").val() > 0) {
        $("#update").text("Wypełnij poniższe pola, aby zmienić termin. Zmiana terminu możliwa jest najpóźniej na jeden dzień przed planowaną wizytą.");
    }

    var today = new Date(Date.now());
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
        var yourDay = new Date(this.value).getDay();
        if(yourDay == 0 || yourDay == 6){
            $("#submit").prop("disabled", true);
            alert("Prosimy wybrać tylko dni robocze");
        }
        else{
            $("#submit").prop("disabled", false);
        }
    })


    $("#form").on("submit", function () {


        if($("#date").val() == null || $("#date").val() == ""){
            alert("Niepoprawna data");
            return false;
        }

        return true;

    });


});