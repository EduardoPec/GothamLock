document.getElementById('loginForm').addEventListener('submit', async function(event) {
    event.preventDefault(); 

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const errorMessageDiv = document.getElementById('errorMessage');
    const loginButton = document.querySelector('.login-button');

    errorMessageDiv.textContent = '';
    errorMessageDiv.style.display = 'none';
    
    loginButton.disabled = true;
    loginButton.innerHTML = 'Aguarde...';

    try {
        const credentials = btoa(`${email}:${password}`);
        localStorage.setItem(CONFIG.AUTH_CREDENTIALS_KEY, credentials);

        const userResponse = await fetchAuth('/auth/me', { method: 'GET' });

        if (!userResponse.ok) {
            localStorage.removeItem(CONFIG.AUTH_CREDENTIALS_KEY);
            
            const errorData = await userResponse.json().catch(() => null);
            if (userResponse.status === 401) {
                throw new Error(errorData?.message || 'Usuário sem acesso. Verifique suas credenciais ou entre em contato com o administrador.');
            } else if (userResponse.status === 403) {
                throw new Error(errorData?.message || 'Acesso negado. Você não tem permissão para acessar o sistema.');
            } else {
                throw new Error(errorData?.message || `Erro de comunicação com o servidor (${userResponse.status}). Tente novamente mais tarde.`);
            }
        }

        const userDto = await userResponse.json();
        localStorage.setItem('userRole', userDto.role);
        localStorage.setItem('userName', userDto.name);

        loginButton.innerHTML = 'Sucesso!';
        loginButton.style.backgroundColor = '#006400';
        loginButton.style.borderColor = '#006400';
        loginButton.style.color = '#ffffff';

        setTimeout(() => {
            window.location.href = 'dashboard.html';
        }, 1000);

    } catch (error) {
        console.error('Erro de login:', error);

        let userMessage = error.message || 'Erro desconhecido';

        if (error instanceof TypeError) {
            if (!navigator.onLine) {
                userMessage = 'Sem conexão de rede. Verifique sua internet e tente novamente.';
            } else {
                userMessage = 'Não foi possível conectar ao servidor. Verifique se o servidor está rodando.';
            }
        }

        if (error.message.includes('sem acesso') || error.message.includes('Acesso negado')) {
            userMessage += '\n\nSe o problema persistir, contate o suporte: suporte@waynesecurity.com';
        }

        errorMessageDiv.textContent = userMessage;
        errorMessageDiv.style.display = 'block';
        loginButton.disabled = false;
        loginButton.innerHTML = 'Login';
    }
});