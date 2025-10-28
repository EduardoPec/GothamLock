const CONFIG = {
    API_URL: 'http://localhost:8080',
    AUTH_CREDENTIALS_KEY: 'authCredentials',
    HEADERS: {
        'Accept': 'application/json'
    }
};

function getAuthHeaders() {
    const headers = { ...CONFIG.HEADERS };
    const credentials = localStorage.getItem(CONFIG.AUTH_CREDENTIALS_KEY);
    if (credentials) {
        headers['Authorization'] = `Basic ${credentials}`;
    }
    return headers;
}

async function fetchAuth(url, options = {}) {
    try {
        const headers = getAuthHeaders();
        const fullUrl = `${CONFIG.API_URL}${url}`;
        
        const response = await fetch(fullUrl, {
            ...options,
            headers: { ...headers, ...options.headers },
            credentials: 'include' 
        });

        if (response.status === 401) {
            localStorage.removeItem(CONFIG.AUTH_CREDENTIALS_KEY);
            localStorage.removeItem('userRole');
            localStorage.removeItem('userName');
            throw new Error('Usuário incorreto! Sem permissão para acessar');
        }

        return response;
    } catch (error) {
        console.error('Erro na requisição:', error);
        throw error;
    }
}