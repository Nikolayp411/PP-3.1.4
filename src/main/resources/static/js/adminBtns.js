document.addEventListener('DOMContentLoaded', function() {

    const tableButton = document.querySelector('button[name="view"][value="table"]');
    const formButton = document.querySelector('button[name="view"][value="form"]');
    const tableContainer = document.getElementById('users-table-container');
    const formContainer = document.getElementById('new-user-form-container');

    tableButton.addEventListener('click', function(event) {
        event.preventDefault();
        tableContainer.style.display = 'block';
        formContainer.style.display = 'none';
    });

    formButton.addEventListener('click', function(event) {
        event.preventDefault();
        tableContainer.style.display = 'none';
        formContainer.style.display = 'block';
    });
});
