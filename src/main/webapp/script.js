const STATUS_OK  = 200;
const STATE_SENT = 4;

window.addEventListener('load', () => {
    const loginForm = document.getElementById('loginForm');
    const logoutForm = document.getElementById('logoutForm');

    loginForm.addEventListener('submit', event => loginAction(event));
    logoutForm.addEventListener('submit', event => logoutAction(event));

    checkLoggedUser();
});


function checkLoggedUser() {
    // AJAX call
    const req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        if (this.readyState === STATE_SENT && this.status === STATUS_OK) {
            const loginForm = document.getElementById('loginForm');
            const logoutForm = document.getElementById('logoutForm');
            const username = document.getElementById('username');

            const response = JSON.parse(this.responseText);

            clearElementChildren(username);

            if (response.login) {
                loginForm.classList.add('hidden');
                logoutForm.classList.remove('hidden');
                username.appendChild(document.createTextNode(response.login));
            } else {
                loginForm.classList.remove('hidden');
                logoutForm.classList.add('hidden');
            }
        }
    };
    req.open("GET", getContextRoot() + '/index.html', true);
    req.send();
}

function clearElementChildren(element) {
    while (element.firstChild) {
        element.removeChild(element.firstChild);
    }
}

function loginAction(event) {
    event.preventDefault();

    const req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        if (this.readyState === STATE_SENT && this.status === STATUS_OK) {
            document.getElementById('login').value = '';
            document.getElementById('password').value = '';
            checkLoggedUser();
        }
    };
    req.open('POST', getContextRoot() + '/api/scientist/login', true);

    const request = new FormData();
    request.append('login', document.getElementById('login').value);
    request.append('password', document.getElementById('password').value);

    req.send(request);
}

function logoutAction(event) {
    event.preventDefault();

    const req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        if (this.readyState === STATE_SENT && this.status === STATUS_OK) {
            checkLoggedUser();
        }
    };
    req.open('POST', getContextRoot() + '/api/scientist/logout', true);
    req.send();
}

function getContextRoot() {
    return '/' + location.pathname.split('/')[1];
}
