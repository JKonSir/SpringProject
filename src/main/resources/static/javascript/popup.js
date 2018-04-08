function showPopup(title, contentID) {
    document.getElementById('popupHeader').innerHTML = title;
    var p = document.getElementById(contentID);
    document.getElementById('popupDescr').innerHTML = p.innerHTML;
    document.getElementById('popupModal').style.display = 'block';
}

window.onclick = function (event) {
    var popupModal = document.getElementById('popupModal');
    if (event.target == popupModal) {
        popupModal.style.display = 'none';
    }
};