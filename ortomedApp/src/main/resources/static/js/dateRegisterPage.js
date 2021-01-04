$(function(){
    console.log("ID to: " + $("#id").val());
    if($("#id").val() > 0){
        $("#update").text("Wypełnij poniższe pola, aby zmienić termin. Zmiana terminu możliwa jest najpóźniej na jeden dzień przed planowaną wizytą.")
    }    
});