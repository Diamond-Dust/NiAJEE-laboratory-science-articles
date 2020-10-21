window.addEventListener('load', () => {
    const avatarForm = document.getElementById('avatarForm');

    avatarForm.addEventListener('submit', event => uploadAvatarAction(event));

    loadAvatar(getUserLogin());
});

function getContextRoot() {
    return '/' + location.pathname.split('/')[1];
}


function getUserLogin() {
    const urlParams = new URLSearchParams(window.location.search);
    var str = window.location.href;
    if(str[str.length-1] == '/')
        str = str.slice(0, -1);
    var res = str.substring(
        str.lastIndexOf('/') + 1,
        (str.indexOf('?') == 0) ? str.length : str.indexOf('?')
    );
    Console.log(res);
    return res;
}


function loadAvatar(login) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let response = JSON.parse(this.responseText);
            for (const [key, value] of Object.entries(response)) {
                let input = document.getElementById(key);
                if (input) {
                    input.value = value;
                }
            }
        }
    };
    xhttp.open("GET", getContextRoot() + '/api/avatars/' + login, true);
    xhttp.send();
}

function uploadAvatarAction(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.open('PUT', getContextRoot() + '/api/avatars/' + getCharacterId(), true);

    let request = new FormData();
    request.append("avatar", document.getElementById('avatar').files[0]);

    Console.log(request);
    Console.log(xhttp);

    xhttp.send(request);

}
