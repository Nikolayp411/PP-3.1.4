function openEditModal(userId) {
    fetch(`/api/admin/getUser/${userId}`)
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

            const editUserForm = document.getElementById('editUserForm');
            if (editUserForm) {
                editUserForm.addEventListener('submit', function (event) {
                    event.preventDefault();

                    const userId = document.getElementById('editUserId').value;
                    const firstName = document.getElementById('editFirstName').value;
                    const lastName = document.getElementById('editLastName').value;
                    const email = document.getElementById('editEmail').value;
                    const age = parseInt(document.getElementById('editAge').value, 10);
                    const roles = Array.from(document.getElementById('editRoles').selectedOptions).map(option => ({ id: option.value }));
                    const password = document.getElementById('editPassword').value;

                    console.log('UserId:', userId);

                    const userData = {
                        id: userId,
                        firstName: firstName,
                        lastName: lastName,
                        email: email,
                        age: age,
                        password: password,
                        roles: roles
                    };


                    fetch(`/api/admin/updateUser/${userId}`, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(userData)
                    })
                        .then(response => {
                            console.log('Server response:', response);
                            if (!response.ok) {
                                throw new Error("Network response was not ok");
                            }
                            return response.json();
                        })
                        .then(data => {
                            console.log("User  updated successfully");
                            closeModal();
                            fetchUsers();
                        })
                        .catch(error => console.error("Error updating user data:", error));
                });
            } else {
                console.error("Form with id 'editUserForm' not found");
            }
        })
        .catch(error => console.error("Error fetching user data:", error));
}

function closeModal() {
    document.getElementById('editUserModal').style.display = 'none';
}

document.addEventListener('DOMContentLoaded', () => {

    document.querySelectorAll('.edit-button').forEach(button => {
        button.addEventListener('click', function () {
            const userId = this.getAttribute('data-user-id');
            openEditModal(userId);
        });
    });
});
