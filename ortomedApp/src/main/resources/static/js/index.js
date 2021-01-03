$(function () {
    $("#visit").on("click", function (e) {
        e.preventDefault();
        var visitPage = window.open("http://localhost:8080/visit", "", "width=800, height=600");
    })

});