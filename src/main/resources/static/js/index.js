function loadAndDisplayUsers() {
    fetch('http://localhost:8081/api/v1/users/connected')
        .then((response) => {
            if (!response.ok) {
                throw new Error('Erreur de récupération des utilisateurs connectés');
            }
            return response.json();
        })
        .then((data) => {
            if (data.length === 0) {
                window.location = 'login.html';
            } else {
                const userListElement = document.getElementById("userList");
                displayUsers(data, userListElement);
            }
        })
        .catch((error) => {
            console.error('Une erreur s\'est produite lors de la récupération des utilisateurs :', error);
            window.location = 'error.html';
        });
}


function displayUsers(userList) {
    const userListElement = document.getElementById("userList");
    userListElement.innerHTML = "";

    userList.forEach(user => {
        const listItem = document.createElement("li");
        listItem.innerHTML = `
            <div>
                <i class="fa fa-user-circle"></i>
                ${user.username} <i class="user-email">(${user.email})</i>
            </div>
        `;
        userListElement.appendChild(listItem);
    });
}

window.addEventListener("load", loadAndDisplayUsers);

function handleLogout() {
    fetch('http://localhost:8081/api/v1/users/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: localStorage.getItem('connectedUser')
    })
        .then((response) => {
            return response;
        })
        .then((data) => {
            localStorage.removeItem('connectedUser');
            window.location.href = "login.html";
        });
}

const logoutBtn = document.getElementById("logoutBtn");
logoutBtn.addEventListener("click", handleLogout);


function handleNewMeeting() {
    const connectedUser = JSON.parse(localStorage.getItem('connectedUser'));
    window.open(`videocall.html?username=${connectedUser.username}`, "_blank");
}

const newMeetingBtn = document.getElementById("newMeetingBtn");
newMeetingBtn.addEventListener("click", handleNewMeeting);


function handleJoinMeeting() {
    const roomId = document.getElementById("meetingName").value;
    const connectedUser = JSON.parse(localStorage.getItem('connectedUser'));

    const url = `videocall.html?roomID=${roomId}&username=${connectedUser.username}`;

    window.open(url, "_blank");
}

const joinMeetingBtn = document.getElementById("joinMeetingBtn");
joinMeetingBtn.addEventListener("click", handleJoinMeeting);



