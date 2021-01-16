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
        var visitPage = window.open("https://ortomed.herokuapp.com/visit", "", "width=800, height=600");
    })
    
    
    
    
    $(".hamburger").on("click", function(){
    $(".hamburger").toggleClass("change");
    $("ul").slideToggle("slow");
})
    
$(window).resize(function(){
              if(window.innerWidth > 986){
                  $("ul").show();
                   
              }
        var className = $(".hamburger").attr("class");
    if(window.innerWidth <= 986 && className == "hamburger"){
                  $("ul").hide();
                         

                              
    }
})  
    

});