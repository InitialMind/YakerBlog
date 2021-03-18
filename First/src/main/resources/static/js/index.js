function a_change(lid, iid, aid) {
    $("#" + lid)[0].style.setProperty("background-color", "#233342");
    $("#" + iid)[0].style.setProperty("color", "#D2433B");
    $("#" + aid)[0].style.setProperty("color", "#fff");
}

function menu_change() {

    if (window.getComputedStyle($("#nav-left")[0], null).marginLeft != "-250px") {
        $("#div-inner")[0].style.setProperty("padding-left", "0px");
        $("#nav-left")[0].style.setProperty("margin-left", "-250px");
        $("#menu-bar")[0].style.setProperty("color", "#ffd2a8");

    }

    else {
        $("#div-inner")[0].style.setProperty("padding-left", "250px");
        $("#nav-left")[0].style.setProperty("margin-left", "0px");
        $("#menu-bar")[0].style.setProperty("color", "#f54739");
    }
}

function Dropdownlist(iid, lid, num) {
    var size = 40 * num;
    if (window.getComputedStyle($("#" + iid)[0], null).transform != "matrix(1, 0, 0, 1, 0, 0)") {
        $("#" + iid)[0].style.setProperty("transform", "matrix(1, 0,0, 1, 0, 0)");
        $("#" + lid)[0].style.setProperty("height", "47px");

    }
    else {
        $("#" + iid)[0].style.setProperty("transform", "rotate(-90deg)");
        $("#" + lid)[0].style.setProperty("height", (47 + 40 * num).toString() + "px");
    }

}

function home() {
    window.location.href = "/index";
}