document.addEventListener("DOMContentLoaded", () => {
  fetchVantagens();

  document.getElementById("confirmButton").addEventListener("click", () => {
    const alunoId = document.getElementById("alunoId").value;
    if (!alunoId) {
      alert("Digite o ID do aluno.");
      return;
    }

    if (resgatandoVantagemId !== null) {
      resgatarVantagem(alunoId, resgatandoVantagemId);
    }
  });
});

let resgatandoVantagemId = null;

function fetchVantagens() {
  fetch("http://localhost:8080/api/vantagens")
    .then((res) => res.json())
    .then((vantagens) => {
      const ul = document.getElementById("vantagensUl");
      ul.innerHTML = "";

      vantagens.forEach((vantagem) => {
        const li = document.createElement("li");
        li.innerHTML = `
          <div>
            <strong>${vantagem.descricao}</strong> - ${vantagem.custo} moedas
          </div>
          <button onclick="openModal(${vantagem.id})">
            Resgatar
          </button>
        `;
        ul.appendChild(li);
      });
    })
    .catch((err) => {
      console.error("Erro ao buscar vantagens:", err);
      alert("Erro ao carregar vantagens.");
    });
}

function openModal(vantagemId) {
  resgatandoVantagemId = vantagemId;
  document.getElementById("alunoId").value = "";
  document.getElementById("modal").style.display = "block";
}

function closeModal() {
  document.getElementById("modal").style.display = "none";
  resgatandoVantagemId = null;
}

function resgatarVantagem(alunoId, vantagemId) {
  fetch("http://localhost:8080/api/resgatar", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ alunoId, vantagemId }),
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error("Erro ao resgatar vantagem");
      }
      return res.json();
    })
    .then(() => {
      alert("Vantagem resgatada com sucesso!");
      closeModal();
    })
    .catch((err) => {
      console.error(err);
      alert("Erro ao resgatar a vantagem.");
    });
}
