// Configuração inicial
const tipoUsuarioLogado = "aluno"; // pode ser "aluno", "professor" ou "empresa"
const welcomeMsg = document.getElementById('welcomeMsg');
const alunoActions = document.getElementById('alunoActions');
const btnVantagens = document.getElementById('btnVantagens');
const btnExtrato = document.getElementById('btnExtrato');
const btnLogout = document.getElementById('btnLogout');

// Mostrar mensagem personalizada
welcomeMsg.textContent = `Você está logado como: ${tipoUsuarioLogado.charAt(0).toUpperCase() + tipoUsuarioLogado.slice(1)}`;

// Mostrar ações do aluno só se for aluno
if (tipoUsuarioLogado === 'aluno') {
  alunoActions.style.display = 'block';
}

// Event Listeners
btnVantagens.addEventListener('click', () => {
window.location.href = '/implementacao/front/listaVantagens/listaVantagens.html';
});

btnExtrato.addEventListener('click', () => {
window.location.href = '/implementacao/front/extratoUsuario/extratoUsuario.html';
});

btnLogout.addEventListener('click', logout);

// Funções
function logout() {
window.location.href = '/implementacao/front/login/login.html';
  // Aqui pode limpar localStorage/sessionStorage e redirecionar para login, por exemplo:
  // localStorage.removeItem('usuario');
  // window.location.href = 'login.html';
}