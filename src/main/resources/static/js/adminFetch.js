fetchRoles()
fetchUsers()

function fetchUsers() {
    fetch("/api/admin/users")
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch users');
            return response.json();
        })
        .then(users => {
            const usersTableBody = document.querySelector('#usersTable tbody');
            usersTableBody.innerHTML = '';
            users.forEach(function(user) {
                usersTableBody.insertAdjacentHTML('beforeend', `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.email}</td>
                        <td>${user.age}</td>
                        <td>
                            <span>${user.roles && user.roles.length > 0 ? user.roles.map(role => role.name).join(', ') : 'No roles assigned'}</span>
                        </td>
                        <td>
                            <button type="button" data-user-id="${user.id}" class="edit-button">Edit</button>
                        </td>
                        <td>
                            <button type="button" data-user-id="${user.id}" class="delete-button">Delete</button>
                        </td>
                    </tr>
                `);
            });

            document.querySelectorAll('.edit-button').forEach(button => {
                button.addEventListener('click', function () {
                    const userId = this.getAttribute('data-user-id');
                    openEditModal(userId);
                });
            });

            document.querySelectorAll('.delete-button').forEach(button => {
                button.addEventListener('click', function (event) {
                    event.preventDefault();
                    const userId = this.getAttribute('data-user-id');
                    openDeleteModal(userId);
                });
            });
        })
        .catch(error => console.error('Error fetching users:', error));
}

function fetchRoles() {
    fetch('/api/admin/roles')
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch roles');
            return response.json();
        })
        .then(roles => {
            const rolesSelect = document.getElementById('roles_to_add');
            rolesSelect.innerHTML = '';

            roles.forEach(role => {
                const option = document.createElement('option');
                option.value = role.id;
                option.textContent = role.name;
                rolesSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching roles:', error));
}