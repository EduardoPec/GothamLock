const INVENTORY_API_URL = '/inventories';

async function loadInventory() {
    const tableBody = document.getElementById('inventoryTable').querySelector('tbody');
    tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center;">Carregando inventário...</td></tr>';

    try {
        const response = await fetchAuth(INVENTORY_API_URL, { method: 'GET' }); 
        
        if (!response.ok) {
            const errorData = await response.json().catch(() => null);
            throw new Error(errorData?.message || `Erro ao buscar inventário: Status ${response.status}`);
        }

        const items = await response.json();
        tableBody.innerHTML = ''; 

        items.forEach(item => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.type.replace(/_/g, ' ')}</td>
                <td>${item.status.replace(/_/g, ' ')}</td>
                <td>
                    <button class="action-btn edit-btn" onclick="prepareEdit(${item.id}, '${item.name}', '${item.type}', '${item.status}')" title="Editar"><i class="fas fa-edit"></i></button>
                    <button class="action-btn delete-btn" onclick="deleteItem(${item.id})" title="Deletar"><i class="fas fa-trash"></i></button>
                </td>
            `;
            tableBody.appendChild(tr);
        });

    } catch (error) {
        console.error("Erro ao carregar inventário:", error);
        tableBody.innerHTML = `<tr><td colspan="5" class="error-message" style="display: block;">Falha ao carregar inventário. ${error.message || 'Verifique sua conexão/permissão.'}</td></tr>`;
    }
}

async function handleInventorySubmit(event) {
    event.preventDefault();
    
    const id = document.getElementById('inventoryId').value;
    const name = document.getElementById('inventoryName').value;
    const type = document.getElementById('inventoryType').value;
    const status = document.getElementById('inventoryStatus').value;
    
    const formTitle = document.getElementById('formTitle');
    const submitButton = document.querySelector('.primary-button');
    const errorMessageDiv = document.getElementById('inventoryErrorMessage');
    
    submitButton.disabled = true;
    errorMessageDiv.style.display = 'none';

    try {
        const itemPayload = { name, type, status };
        let response;
        let url = '/inventories';
        let method = 'POST';

        if (id) {
            url = `/inventories/${id}`;
            method = 'PUT';
        }
        
        response = await fetchAuth(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(itemPayload)
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => null);
            throw new Error(errorData?.message || `Falha na operação: ${response.status}`);
        }

        alert(`Item ${id ? 'atualizado' : 'cadastrado'} com sucesso!`);
        document.getElementById('inventoryForm').reset();
        document.getElementById('inventoryId').value = '';
        formTitle.innerHTML = '<i class="fas fa-plus-circle"></i> Cadastrar Novo Item';
        await loadInventory();

    } catch (error) {
        console.error("Erro no formulário de inventário:", error);
        errorMessageDiv.textContent = error.message;
        errorMessageDiv.style.display = 'block';
    } finally {
        submitButton.disabled = false;
    }
}

function prepareEdit(id, name, type, status) {
    document.getElementById('inventoryId').value = id;
    document.getElementById('inventoryName').value = name;
    document.getElementById('inventoryType').value = type;
    document.getElementById('inventoryStatus').value = status;
    document.getElementById('formTitle').innerHTML = `<i class="fas fa-edit"></i> Editando Item ${id}`;
}

async function deleteItem(id) {
    if (!confirm('Tem certeza que deseja excluir este item do inventário?')) return;
    
    try {
        const response = await fetchAuth(`/inventories/${id}`, { method: 'DELETE' });
        
        if (!response.ok) {
            const errorData = await response.json().catch(() => null);
            throw new Error(errorData?.message || `Falha ao deletar: Status ${response.status}`);
        }
        
        alert('Item excluído com sucesso!');
        await loadInventory();
    } catch (error) {
        alert('Erro ao excluir: ' + error.message);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    applyUIPermissions(); 

    const form = document.getElementById('inventoryForm');
    if (form) {
        form.addEventListener('submit', handleInventorySubmit);
    }

    loadInventory();
});