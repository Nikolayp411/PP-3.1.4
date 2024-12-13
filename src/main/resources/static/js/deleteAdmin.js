let userIdToDelete;

function openDeleteModal(userId) {
    userIdToDelete = userId;

    fetch(`/api/admin/getUser/${userId}`)
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
    fetch(`/api/admin/deleteUser`, {
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

            const rowToDelete = document.querySelector(`tr[data-user-id="${userIdToDelete}"]`);
            if (rowToDelete) {
                rowToDelete.remove();
            }

            fetchUsers();

            closeDeleteModal();
        })
        .catch(error => console.error("Error deleting user:", error));
});