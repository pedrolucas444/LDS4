const saldoProfessor = 100; // saldo fictício do professor

  document.getElementById("moedasForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const aluno = document.getElementById("aluno").value.trim();
    const quantidade = parseInt(document.getElementById("quantidade").value);
    const motivo = document.getElementById("motivo").value.trim();
    const erroMsg = document.getElementById("erroMsg");

    erroMsg.textContent = "";

    if (!aluno || !quantidade || !motivo) {
      erroMsg.textContent = "Todos os campos são obrigatórios.";
      return;
    }

    if (quantidade > saldoProfessor) {
      erroMsg.textContent = `Saldo insuficiente. Seu saldo atual é de ${saldoProfessor} moedas.`;
      return;
    }

    alert(`Moedas enviadas com sucesso para ${aluno}!\nQuantidade: ${quantidade}\nMotivo: ${motivo}`);
    document.getElementById("moedasForm").reset();
  });