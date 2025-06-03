document.addEventListener("DOMContentLoaded", () => {
  // Suponha que tipo e nome do usuário foram armazenados no login
  const tipoUsuario = localStorage.getItem("tipo"); // "aluno", "professor", "empresa"
  const nomeUsuario = localStorage.getItem("nome");

  const welcomeMsg = document.getElementById("welcomeMsg");
  welcomeMsg.textContent = `Bem-vindo, ${nomeUsuario}!`;

  // Exibir ações com base no tipo
  if (tipoUsuario === "aluno") {
    document.getElementById("alunoActions").style.display = "block";
  } else if (tipoUsuario === "professor") {
    document.getElementById("professorActions").style.display = "block";
  } else if (tipoUsuario === "empresa") {
    document.getElementById("empresaActions").style.display = "block";
  }

  // Eventos de redirecionamento (exemplos)
  document.getElementById("btnVantagens")?.addEventListener("click", () => {
    window.location.href = "http://127.0.0.1:5500/implementacao/front/listaVantagens/listaVantagens.html";
  });

  document.getElementById("btnExtratoAluno")?.addEventListener("click", () => {
    window.location.href = "http://127.0.0.1:5500/implementacao/front/consultaExtrato/consulta-extrato.html?tipo=aluno";
  });

  document.getElementById("btnExtratoProfessor")?.addEventListener("click", () => {
    window.location.href = "http://127.0.0.1:5500/implementacao/front/consultaExtrato/consulta-extrato.html?tipo=professor";
  });

  document.getElementById("btnPerfilAluno")?.addEventListener("click", () => {
    window.location.href = "perfil.html?tipo=aluno";
  });

  document.getElementById("btnPerfilProfessor")?.addEventListener("click", () => {
    window.location.href = "perfil.html?tipo=professor";
  });

  document.getElementById("btnEnvioMoedasProfessor")?.addEventListener("click", () => {
    window.location.href = "http://127.0.0.1:5500/implementacao/front/envioMoedas/envio-moedas.html";
  });

  document.getElementById("btnPerfilEmpresa")?.addEventListener("click", () => {
    window.location.href = "perfil.html?tipo=empresa";
  });

  document.getElementById("btnCadastrarVantagem")?.addEventListener("click", () => {
    window.location.href = "http://127.0.0.1:5500/implementacao/front/cadastroVantagens/cadastroVantagens.html";
  });

  document.getElementById("btnLogout").addEventListener("click", () => {
    localStorage.clear();
    window.location.href = "http://127.0.0.1:5500/implementacao/front/login/login.html";
  });
});
