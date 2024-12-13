document.addEventListener('DOMContentLoaded', function() {
    function fetchCurrentUser () {
        fetch('/api/admin/current-user')
            .then(response => {
                if (!response.ok) throw new Error('Failed to fetch current user');
                return response.json();
            })
            .then(data => {
                console.log(data);
                if (document.getElementById('username')) {
                    document.getElementById('username').textContent = data.username;
                }
                if (document.getElementById('top_roles')) {
                    document.getElementById('top_roles').textContent = data.roles.map(role => role.name).join(', ');
                }
            })
            .catch(error => console.error('Error fetching current user:', error));
    }

    function fetchAvailableAdminPages() {
        fetch('/api/admin/available-pages')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(pages => {
                const sidebar = document.querySelector('.sidebar');
                sidebar.innerHTML = '';
                pages.forEach(page => {
                    const button = document.createElement('button');
                    button.textContent = page.name;
                    button.dataset.url = page.url;
                    button.className = page.url === window.location.pathname ? 'active-button' : '';
                    button.onclick = function() {
                        location.href = this.dataset.url;
                    };
                    sidebar.appendChild(button);
                });
            })
            .catch(error => console.error('Error fetching available admin pages:', error));
    }

    fetchCurrentUser ();
    fetchAvailableAdminPages();
});