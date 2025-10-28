const ACCESS_LOG_API_URL = '/access-logs';

function formatMoment(isoString) {
    const date = new Date(isoString);
    const options = {
        year: 'numeric', month: '2-digit', day: '2-digit',
        hour: '2-digit', minute: '2-digit', second: '2-digit',
        timeZone: 'UTC' 
    };
    return date.toLocaleDateString('pt-BR', options) + ' UTC'; 
}

function getResultClass(result) {
    if (result === 'NEGADO') {
        return 'log-denied';
    }
    return 'log-authorized';
}


async function loadAccessLogs() {
    const tableBody = document.getElementById('accessLogTable').querySelector('tbody');
    tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center;">Carregando logs...</td></tr>';

    try {
        const response = await fetchAuth(ACCESS_LOG_API_URL, { method: 'GET' }); 
        
        if (!response.ok) {
            const errorData = await response.json().catch(() => null);
            throw new Error(errorData?.message || `Erro ao buscar logs: Status ${response.status}`);
        }

        const logs = await response.json();
        tableBody.innerHTML = ''; 
        
        if (logs.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center;">Nenhum log de acesso encontrado.</td></tr>';
            return;
        }

        logs.forEach(log => {
            const tr = document.createElement('tr');

            const resultClass = getResultClass(log.result);
            const formattedResult = log.result.replace(/_/g, ' ');

            const formattedArea = log.area.replace(/_/g, ' ');
            const formattedType = log.type.replace(/_/g, ' ');

            tr.innerHTML = `
                <td>${log.id}</td>
                <td>${formatMoment(log.moment)}</td>
                <td>${log.user.name} (${log.user.email})</td>
                <td>${formattedArea}</td>
                <td>${formattedType}</td>
                <td class="${resultClass}">${formattedResult}</td>
            `;
            tableBody.appendChild(tr);
        });

    } catch (error) {
        console.error("Erro ao carregar logs de acesso:", error);
        tableBody.innerHTML = `<tr><td colspan="6" class="error-message" style="display: block;">Falha ao carregar logs. ${error.message || 'Verifique sua conexão/permissão.'}</td></tr>`;
    }
}

document.addEventListener('DOMContentLoaded', () => {

    applyUIPermissions(); 
    loadAccessLogs();
});