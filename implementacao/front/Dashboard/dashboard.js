// dashboard.js

// Utilitários comuns
function getUserData() {
  return JSON.parse(localStorage.getItem("usuario"));
}

function showOnlyDashboard(tipo) {
  document.getElementById("dashboard-aluno").style.display = tipo === "aluno" ? "block" : "none";
  document.getElementById("dashboard-professor").style.display = tipo === "professor" ? "block" : "none";
  document.getElementById("dashboard-empresa").style.display = tipo === "empresa" ? "block" : "none";
}

// Aluno: resgatar vantagem
function resgatarVantagem(idVantagem) {
  const user = getUserData();
  fetch(`/api/alunos/${user.id}/resgatar`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ vantagemId: idVantagem })
  }).then(r => r.json()).then(alertar).catch(console.error);
}

// Professor: enviar moedas
function enviarMoedas() {
  const user = getUserData();
  const alunoId = document.getElementById("destino-aluno-id").value;
  const quantidade = document.getElementById("moeda-quantidade").value;
  const mensagem = document.getElementById("mensagem-reconhecimento").value;

  fetch(`/api/professores/${user.id}/enviar-moedas`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ alunoId, quantidade, mensagem })
  }).then(r => r.json()).then(alertar).catch(console.error);
}

// Empresa: cadastrar vantagem
function cadastrarVantagem() {
  const user = getUserData();
  const descricao = document.getElementById("descricao-vantagem").value;
  const custo = document.getElementById("custo-moeda").value;

  fetch(`/api/empresas/${user.id}/vantagens`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ descricao, custo })
  }).then(r => r.json()).then(alertar).catch(console.error);
}

function alertar(response) {
  alert(response.message || "Operação realizada com sucesso!");
}

// Inicialização
window.onload = function () {
  const user = getUserData();
  if (!user) return window.location.href = "/login.html";
  showOnlyDashboard(user.tipo);
};