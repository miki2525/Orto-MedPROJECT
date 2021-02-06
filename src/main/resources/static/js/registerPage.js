$(function () {

    $("#foreigner").on("click", function () {
        ($("#foreigner").prop("checked")) ? $("#pesel").prop("disabled", true) : $("#pesel").prop("disabled", false);
    })




    checkFields();
    $("input").on("keyup", checkFields);

    function checkFields(){

        $(this).removeClass("invalid");

        var inputWithValue = 0;
        var inputs = $("input:not([type='submit']):not([type='checkbox']), #time").not("#id");

        inputs.each(function () {
            if($(this).val()) {
                inputWithValue++;
            }
        });
        if($("#foreigner").prop("checked")){
            if (!$("#pesel").val()) {
                inputWithValue++;
            }
        }
        (inputWithValue == 8) ? $("#submit").prop("disabled", false) : $("#submit").prop("disabled", true);
    }
});