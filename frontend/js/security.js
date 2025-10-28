const PROTECTED_ROUTES = {
    USERS: 'ADMIN_SEGURANCA', 
    INVENTORY: ['ADMIN_SEGURANCA', 'GERENTE'], 
};

function getUserRole() {
    return localStorage.getItem('userRole');
}

function logout() {
    localStorage.removeItem(CONFIG.AUTH_CREDENTIALS_KEY);
    localStorage.removeItem('userRole');
    localStorage.removeItem('userName');
    window.location.href = 'login.html';
}

function checkPermission(requiredRoles) {
    const role = getUserRole();
    if (!role) return false;
    const rolesArray = Array.isArray(requiredRoles) ? requiredRoles : [requiredRoles];
    return rolesArray.includes(role);
}

function applyUIPermissions() {
    const userLink = document.getElementById('userManagementLink');
    if (userLink && !checkPermission(PROTECTED_ROUTES.USERS)) {
        userLink.style.display = 'none';
    }

    const inventoryLink = document.getElementById('inventoryManagementLink');
    if (inventoryLink && !checkPermission(PROTECTED_ROUTES.INVENTORY)) {
        inventoryLink.style.display = 'none';
    }
    
    const welcomeText = document.getElementById('welcomeUser');
    if (welcomeText) {
        welcomeText.textContent = `Bem-vindo(a), ${localStorage.getItem('userName')} (${getUserRole()})`;
    }
}

document.addEventListener('DOMContentLoaded', async () => {
    if (!window.location.pathname.endsWith('login.html')) {
        const credentials = localStorage.getItem(CONFIG.AUTH_CREDENTIALS_KEY);
        if (!credentials) {
            window.location.href = 'login.html';
            return;
        }

        try {
            const res = await fetchAuth('/auth/me', { method: 'GET' });
            if (!res.ok) {
                throw new Error('Não autenticado');
            }
            const userDto = await res.json();
            localStorage.setItem('userRole', userDto.role);
            localStorage.setItem('userName', userDto.name);
            applyUIPermissions();
        } catch (err) {
            console.error('Falha na validação de autenticação:', err);
            localStorage.removeItem(CONFIG.AUTH_CREDENTIALS_KEY);
            window.location.href = 'login.html';
        }
    }
});