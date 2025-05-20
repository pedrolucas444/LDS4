function initEmpresaForm() {
  const form = document.getElementById("formEmpresa");

  form.addEventListener("submit", function (event) {
    event.preventDefault();

    const cnpj = document.getElementById("cnpj").value.trim();
    const nome = document.getElementById("nomeEmpresa").value.trim();
    const senha = document.getElementById("senhaEmpresa").value.trim();

    const data = {
      cnpj,
      nome,
      senha
    };

    console.log("Dados da empresa:", data);

    fetch("http://localhost:8080/api/empresas", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    })
    .then(response => {
      if (!response.ok) {
        throw new Error("Erro ao cadastrar empresa.");
      }
      return response.json();
    })
    .then(data => {
      alert("Empresa cadastrada com sucesso!");
      console.log("Resposta da API:", data);
      // Redirecionar ou limpar campos, se quiser
    })
    .catch(error => {
      alert("Erro no cadastro.");
      console.error("Erro:", error);
    });
  });
}

window.onload = function () {
  initEmpresaForm();
};

// Máscara para CNPJ
$(document).ready(function(){
  $('#cnpj').mask('00.000.000/0000-00');
});
