document.addEventListener("DOMContentLoaded", () => {
  fetchVantagens();

  const confirmButton = document.getElementById("confirmButton");
  const alunoIdInput = document.getElementById("alunoId");
  const alunoId = localStorage.getItem("alunoId");

  // Oculta o input de ID do aluno se já tiver no localStorage
  if (alunoId) {
    alunoIdInput.style.display = "none";
    alunoIdInput.value = alunoId;
  }

  confirmButton.addEventListener("click", () => {
    const localAlunoId = localStorage.getItem("alunoId");
    const inputAlunoId = alunoIdInput.value;
    const finalAlunoId = localAlunoId || inputAlunoId;

    if (!finalAlunoId) {
      alert("Digite o ID do aluno.");
      return;
    }

    if (resgatandoVantagemId !== null) {
      resgatarVantagem(finalAlunoId, resgatandoVantagemId);
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
            <strong>${vantagem.descricao}</strong> - ${vantagem.valor} moedas
          </div>
          <button onclick="openModal(${vantagem.id})">Resgatar</button>
          <button onclick="trocarVantagem(${vantagem.id})">Trocar</button>
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
  const alunoId = localStorage.getItem("alunoId");
  const input = document.getElementById("alunoId");

  if (alunoId) {
    input.value = alunoId;
    input.style.display = "none";
  } else {
    input.value = "";
    input.style.display = "block";
  }

  document.getElementById("modal").style.display = "block";
}

function closeModal() {
  document.getElementById("modal").style.display = "none";
  resgatandoVantagemId = null;
}

function resgatarVantagem(alunoId, vantagemId) {
  fetch(`http://localhost:8080/api/trocas/${vantagemId}?alunoId=${alunoId}`, {
    method: "POST"
  })
    .then((res) => {
      if (!res.ok) {
        return res.text().then(msg => {
          throw new Error(msg || "Erro ao resgatar vantagem");
        });
      }
      alert("Vantagem resgatada com sucesso!");
      closeModal();
    })
    .catch((err) => {
      console.error(err);
      alert("Erro: " + err.message);
    });
}

function trocarVantagem(vantagemId) {
  const alunoId = localStorage.getItem("alunoId");

  if (!alunoId) {
    alert("Aluno não está logado.");
    return;
  }

  fetch(`http://localhost:8080/api/trocas/${vantagemId}?alunoId=${alunoId}`, {
    method: "POST"
  })
    .then((res) => {
      if (!res.ok) {
        return res.text().then(msg => {
          throw new Error(msg || "Erro ao trocar vantagem");
        });
      }
      alert("Troca realizada com sucesso!");
      fetchVantagens(); // Atualiza a lista se necessário
    })
    .catch((err) => {
      console.error(err);
      alert("Erro: " + err.message);
    });
}
