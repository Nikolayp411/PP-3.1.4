$(document).ready(function() {
    fetchUserData();
    fetchAvailablePages()
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
function fetchAvailablePages() {
    const currentPageUrl = window.location.pathname
    fetch('/api/available-pages')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(pages => {
            const sidebar = $('.sidebar');
            sidebar.empty();
            pages.forEach(page => {
                sidebar.append(`
                    <button
                        data-url="${page.url}"
                        class="${page.url === currentPageUrl ? 'active-button' : ''}"
                        onclick="location.href=this.dataset.url">
                        ${page.name}
                    </button>
                `);
            });
        })
        .catch(error => console.error('Error fetching available pages:', error));
}


