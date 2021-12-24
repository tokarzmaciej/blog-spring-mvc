function openTab(evt) {
    let i, tablinks;
    tablinks = document.getElementsByClassName("tab");
    for (i = 0; i < 6; i++) {
        tablinks[i].className = tablinks[i].className.replace(" is-active", "");
    }
    evt.currentTarget.className += " is-active";
}

// I used:https://codepen.io/t7team/pen/ZowdRN
