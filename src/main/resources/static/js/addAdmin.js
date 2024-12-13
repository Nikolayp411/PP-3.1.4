document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('addUserForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const roles = document.getElementById('roles_to_add');
        const selectedRoles = Array.from(roles.selectedOptions, option => option.value);
        const userData = {
            firstName: document.getElementById('firstName').value,
            lastName: document.getElementById('lastName').value,
            email: document.getElementById('email').value,
            age: document.getElementById('age').value,
            password: btoa(document.getElementById('password').value),
            roles: selectedRoles.map(roleId => ({ id: roleId }))
        };

        fetch('/api/admin/addUser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        })

            .then(response => {
                if (!response.ok) throw new Error('Error adding user');
                fetchUsers()
                document.getElementById('addUserForm').reset();

                document.getElementById('new-user-form-container').style.display = 'none';
                document.getElementById('users-table-container').style.display = 'block';
            })
            .catch(error => console.error('Error adding user:', error));
    });
});
