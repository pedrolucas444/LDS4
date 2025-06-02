document.getElementById("btnConsultar").addEventListener("click", () => {
  const tipo = document.getElementById("tipoUsuario").value;
  const nome = document.getElementById("nomeUsuario").value.trim();
  const saldoEl = document.getElementById("saldo");
  const tabela = document.getElementById("tabelaTransacoes");

  if (!nome) {
    alert("Por favor, insira o nome do usuário.");
    return;
  }

  // Simulação de dados
  const transacoesExemplo = {
    aluno: [
      { data: "2025-05-01", descricao: "Recebimento de moedas do Professor João", quantidade: 30 },
      { data: "2025-05-10", descricao: "Troca por desconto na livraria", quantidade: -20 },
      { data: "2025-05-15", descricao: "Recebimento de moedas do Professor Ana", quantidade: 15 }
    ],
    professor: [
      { data: "2025-05-01", descricao: "Enviado para Maria", quantidade: -30 },
      { data: "2025-05-10", descricao: "Enviado para Lucas", quantidade: -20 },
      { data: "2025-05-15", descricao: "Enviado para Ana", quantidade: -15 }
    ]
  };

  const transacoes = transacoesExemplo[tipo];
  const saldoAtual = transacoes.reduce((total, t) => total + t.quantidade, 0);

  saldoEl.textContent = saldoAtual;

  // Limpar tabela
  tabela.innerHTML = "";

  transacoes.forEach(t => {
    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${t.data}</td>
      <td>${t.descricao}</td>
      <td>${t.quantidade > 0 ? "+" : ""}${t.quantidade}</td>
    `;
    tabela.appendChild(row);
  });
});
