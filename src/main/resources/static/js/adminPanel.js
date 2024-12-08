document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.edit-button').forEach(button => {
        button.addEventListener('click', function () {
            const userId = this.getAttribute('data-user-id');
            openEditModal(userId);
        });
    });

    document.querySelectorAll('.delete-button').forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault();
            const userId = this.closest('form').querySelector('input[name="userId"]').value;
            openDeleteModal(userId);
        });
    });
});

let userIdToDelete = null;

function openDeleteModal(userId) {
    userIdToDelete = userId;

    fetch(`/admin/getUser?id=${userId}`)
        .then(response => response.json())
        .then(data => {
            const user = data.user;

            document.getElementById('deleteUserId').value = user.id;
            document.getElementById('deleteUserFirstName').value = user.firstName;
            document.getElementById('deleteUserLastName').value = user.lastName;
            document.getElementById('deleteUserEmail').value = user.email;
            document.getElementById('deleteUserAge').value = user.age;

            const rolesSelect = document.getElementById('deleteUserRoles');
            rolesSelect.innerHTML = '';
            user.roles.forEach(role => {
                const option = document.createElement('option');
                option.value = role.id;
                option.textContent = role.name;
                rolesSelect.appendChild(option);
            });

            document.getElementById('deleteUserModal').style.display = 'flex';
        })
        .catch(error => console.error("Error fetching user data:", error));
}

function closeDeleteModal() {
    document.getElementById('deleteUserModal').style.display = 'none';
}

document.getElementById('confirmDeleteButton').addEventListener('click', function () {
    fetch(`/admin/deleteUser`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ userId: userIdToDelete }),
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text); });
            }
            location.reload();
        })
        .catch(error => console.error("Error deleting user:", error))
});

function openEditModal(userId) {
    fetch(`/admin/getUser?id=${userId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            const user = data.user;
            const availableRoles = data.availableRoles;

            document.getElementById('editUserId').value = user.id;
            document.getElementById('fakeUserId').value = user.id;
            document.getElementById('editFirstName').value = user.firstName;
            document.getElementById('editLastName').value = user.lastName;
            document.getElementById('editEmail').value = user.email;
            document.getElementById('editAge').value = user.age;

            const rolesSelect = document.getElementById('editRoles');
            rolesSelect.innerHTML = '';

            availableRoles.forEach(role => {
                const option = document.createElement('option');
                option.value = role.id;
                option.textContent = role.name;
                option.selected = user.roles.some(userRole => userRole.id === role.id);
                rolesSelect.appendChild(option);
            });

            document.getElementById('editUserModal').style.display = 'flex';
        })
        .catch(error => console.error("Error fetching user data:", error));
}



function closeModal() {
    document.getElementById('editUserModal').style.display = 'none';
}