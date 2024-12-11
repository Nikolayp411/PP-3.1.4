$(document).ready(function() {
    fetchUserData();
});

function fetchUserData() {
    fetch('/api/user')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(user => {
            $('#username').text(user.username);
            $('#roles').text(user.authorities.map(role => role.authority).join(', '));
            $('#user-table-body').html(`
                <tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${user.age}</td>
                    <td>${user.roles ? user.roles.map(role => role.name).join(', ') : 'No roles assigned'}</td>
                </tr>
            `);
        })
        .catch(error => console.error('Error fetching user data:', error));
}
