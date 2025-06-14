// Carregar lista de alunos ao abrir a página
document.addEventListener("DOMContentLoaded", async () => {
  try {
    const response = await fetch("http://localhost:8080/api/alunos");
    if (!response.ok) throw new Error("Erro ao carregar alunos");

    const alunos = await response.json();
    const select = document.getElementById("aluno");

    alunos.forEach(aluno => {
      const option = document.createElement("option");
      option.value = aluno.id;
      option.textContent = aluno.nome;
      select.appendChild(option);
    });
  } catch (error) {
    const erroMsg = document.getElementById("erroMsg");
    erroMsg.textContent = "Erro ao carregar lista de alunos.";
  }
});

// Lidar com envio do formulário
document.getElementById("moedasForm").addEventListener("submit", async function(event) {
  event.preventDefault();

  const usuarioLogado = JSON.parse(localStorage.getItem("usuarioLogado"));
  const professorId = usuarioLogado?.dados?.id;
  const alunoId = document.getElementById("aluno").value;
  const quantidade = parseInt(document.getElementById("quantidade").value);
  const motivo = document.getElementById("motivo").value.trim();
  const erroMsg = document.getElementById("erroMsg");

  erroMsg.textContent = "";

  try {
    const url = `http://localhost:8080/api/professores/${professorId}/enviar-moedas/${alunoId}?montante=${quantidade}&motivo=${encodeURIComponent(motivo)}`;

    const response = await fetch(url, {
      method: "POST",
    });

    if (!response.ok) {
      const msg = await response.text();
      throw new Error(msg);
    }

    alert("Moedas enviadas com sucesso!");
    document.getElementById("moedasForm").reset();
  } catch (error) {
    erroMsg.textContent = error.message || "Erro ao enviar moedas.";
  }
});
