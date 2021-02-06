window.onload = function () {
    var status = document.getElementById("status");
    var today = new Date(Date.now());
    if(today.getDay() != 0){
        if(today.getDay() != 6) {
            if (today.getHours() >= 7 && today.getHours() < 21) {
                console.log("OPEN");
                status.innerHTML = "!! OPEN !!";
                setInterval(function () {
                    status.classList.toggle("open");
                }, 1000);
            }
        }
        else {
            console.log("CLOSED");
            status.classList.add("closed");
            status.innerText = "CLOSED";
        }

    }
    else {
        console.log("CLOSED");
        status.classList.add("closed");
        status.innerText = "CLOSED";
    }

}