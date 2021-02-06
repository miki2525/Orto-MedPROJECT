$(function () {
    var index;
    var length = $(".slide").length;

    $(".photo").on("click",function () {
        index = $(this).index();
        $(".slideBackground").css("display", "block");
        $(".prev, .next").fadeIn();
        showGallery(index);

    });

    $(".slideBackground").on("click", closeGallery);

$(".prev").on("click", function () {
    showGallery(index += -1);
});

$(".next").on("click", function () {
    showGallery(index += 1);
});

    function showGallery(n) {
        if(n < 0){
            index = length - 1;
        }
        if(n > length - 1){
            index = 0;
        }

        $(".doctor").css("display", "none");
        $(".slide").css("display", "none");

        $(".doctor").eq(index).css("display", "block");
        $(".slide").eq(index).fadeIn();

    }

    function closeGallery() {
        $(".slideBackground").css("display", "none");
        $(".prev, .next").css("display", "none");
    }
})