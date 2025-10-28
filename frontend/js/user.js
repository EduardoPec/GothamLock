const USER_API_URL = `${CONFIG.API_URL}/users`;

async function loadUsers() {
    const tableBody = document.getElementById('userTable').querySelector('tbody');
    tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center;">Carregando usuários...</td></tr>';

    try {
        const response = await fetchAuth('/users', { method: 'GET' }); 
        if (!response.ok) throw new Error(`Erro ao buscar usuários: Status ${response.status}`);

        const users = await response.json();
        tableBody.innerHTML = ''; 
        users.forEach(user => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
                <td>
                    <button class="action-btn edit-btn" onclick="prepareEdit(${user.id}, '${user.name}', '${user.email}', '${user.role}')" title="Editar"><i class="fas fa-edit"></i></button>
                    <button class="action-btn delete-btn" onclick="deleteUser(${user.id})" title="Deletar"><i class="fas fa-trash"></i></button>
                </td>
            `;
            tableBody.appendChild(tr);
        });

    } catch (error) {
        console.error("Erro ao carregar usuários:", error);
        tableBody.innerHTML = `<tr><td colspan="5" class="error-message" style="display: block;">Falha ao carregar usuários. ${error.message || ''}</td></tr>`;
    }
}

async function handleUserSubmit(event) {
    event.preventDefault();
    
    const id = document.getElementById('userId').value;
    const name = document.getElementById('userName').value;
    const email = document.getElementById('userEmail').value;
    const role = document.getElementById('userRole').value;
    const password = document.getElementById('userPassword').value;
    const formTitle = document.getElementById('formTitle');
    const submitButton = document.querySelector('.primary-button');
    const errorMessageDiv = document.getElementById('userErrorMessage');
    
    submitButton.disabled = true;
    errorMessageDiv.style.display = 'none';

    try {
        const userPayload = { name, email, role, password };
        let response;
        let url = '/users';
        let method = 'POST';

        if (id) {
            // UPDATE
            url = `/users/${id}`;
            method = 'PUT';
        }
        
        response = await fetchAuth(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userPayload)
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => null);
            throw new Error(errorData?.message || `Falha na operação: ${response.status}`);
        }

        alert(`Usuário ${id ? 'atualizado' : 'cadastrado'} com sucesso!`);
        document.getElementById('userForm').reset();
        document.getElementById('userId').value = '';
        formTitle.innerHTML = '<i class="fas fa-user-plus"></i> Cadastrar Novo Usuário';
        await loadUsers();

    } catch (error) {
        console.error("Erro no formulário de usuário:", error);
        errorMessageDiv.textContent = error.message;
        errorMessageDiv.style.display = 'block';
    } finally {
        submitButton.disabled = false;
    }
}

function prepareEdit(id, name, email, role) {
    document.getElementById('userId').value = id;
    document.getElementById('userName').value = name;
    document.getElementById('userEmail').value = email;
    document.getElementById('userRole').value = role;
    document.getElementById('userPassword').value = '********';
    document.getElementById('formTitle').innerHTML = `<i class="fas fa-user-edit"></i> Editando Usuário ${id}`;
    document.getElementById('userPassword').disabled = true; 
}

async function deleteUser(id) {
    if (!confirm('Tem certeza que deseja excluir este usuário?')) return;
    
    try {
        const response = await fetchAuth(`/users/${id}`, { method: 'DELETE' }); 

    } catch (error) {
        alert('Erro ao excluir: ' + error.message);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('userForm').addEventListener('submit', handleUserSubmit);
    
    applyUIPermissions(); 
    loadUsers();
});