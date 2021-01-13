$(document).ready(function () {

    $(".about").css("display", "none");
    $("#reason p").css("display", "none");
    $(".partners img").css("display", "none");
    $(".slideshow").css("display", "none");


    var index = 0;
    $(".slideshow").eq(index).fadeIn();
    setInterval(function () {
        index += 1;
        if(index > $(".slideshow").length - 1) {
            index = 0;
        }
        $(".slideshow").css("display", "none");
        $(".slideshow").eq(index).fadeIn();
        }, 3000);


    setInterval(function () {
        $(".needhelp div a").toggleClass("blink");
    }, 1200);


    $(window).on("scroll", function () {
        var posY = $(window).scrollTop();

        if (posY >= 280){
            $(".about").fadeIn(300).css("display", "flex");
        }
        if(posY >= 1000){
            $("#reason p").slideDown().css("display", "flex");
        }
        if(posY >= 1200){
            $("#map").addClass("slide");
        }
        if(posY >= 1650){
            $(".partners img").eq(0).fadeIn(1500);
            for (let i = 1; i < 4; i++){
                setTimeout(function () {
                    $(".partners img").eq(i).fadeIn(1000);
                }, 700 * i);
            }
        }
        })
})