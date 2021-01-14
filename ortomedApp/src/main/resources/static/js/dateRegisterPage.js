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

    var yourYyyy = date.value.toString().substr(0, 4);
    var yourMm = date.value.toString().substr(5, 2);
    var yourDd = date.value.toString().substr(8, 2);
    yourDate = new Date(yourYyyy, yourMm - 1, yourDd);

    date.addEventListener("change", function () {
        $("#submit").prop("disabled", false);
    })


    $("#form").on("submit", function () {

        if($("#doc").val() == null || $("#doc").val() == ""){
            alert("Prosimy wybrać lekarza");
            return false;
        }

        if($("#date").val() == null || $("#date").val() == ""){
            alert("Niepoprawna data");
            return false;
        }

        if(yourDate.getDay() == 0 || yourDate.getDay() == 6){
            alert("Pracujemy tylko w dni robocze\n " +
                "Prosimy wybrać tylko dni powszednie");
            return false;
        }

        return true;

    });


});