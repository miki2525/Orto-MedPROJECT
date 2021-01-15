$(function () {

    $("body").css("display", "none");
    $("body").fadeIn(800);


    $("li a").on("click", function (e) {
         $(this).addClass("zoom");

        setTimeout(function () {
            $("li a").removeClass("zoom");
        }, 2000);

    });

    $("#visit").on("click", function (e) {
        e.preventDefault();
        var visitPage = window.open("http://localhost:8080/visit", "", "width=800, height=600");
    })
    
    
    
    
    $(".hamburger").on("click", function(){
    $(".hamburger").toggleClass("change");
    $("ul").slideToggle("slow");
})
    var check = window.matchMedia("(max-width: 988)");
              if(check.matches){
                  $("ul").show();
              }
        var className = $(".hamburger").attr("class");
    if(window.innerWidth <= 986 && className == "hamburger"){
                  $("ul").hide();
                              
    }
})  

});