document.addEventListener("DOMContentLoaded", async () => {
  const usuarioLogado = JSON.parse(localStorage.getItem("usuarioLogado"));

  const { tipo, dados } = usuarioLogado;
  const userId = dados.id;

  const saldoEl = document.getElementById("saldo");
  const tabela = document.getElementById("tabelaTransacoes");

  try {
    const response = await fetch(`http://localhost:8080/api/transacoes/extrato/${tipo}/${userId}`);

    if (!response.ok) {
      throw new Error("Erro ao carregar extrato");
    }

    const extrato = await response.json();
    saldoEl.textContent = extrato.saldoMoedas;
    tabela.innerHTML = "";

    extrato.transacoes.forEach(t => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${new Date(t.data).toLocaleDateString("pt-BR")}</td>
        <td>${t.tipo}</td>
        <td>${t.montante > 0 ? "+" : ""}${t.montante}</td>
      `;
      tabela.appendChild(row);
    });
  } catch (error) {
    console.error("Erro ao carregar extrato:", error);
    alert("Não foi possível carregar o extrato.");
  }
});
