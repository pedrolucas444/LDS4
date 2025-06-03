document.getElementById("tipoUsuario").addEventListener("change", function () {
  const tipo = this.value;
  const label = document.querySelector("label[for='cpf']");
  const input = document.getElementById("cpf");

  if (tipo === "empresa") {
    label.textContent = "CNPJ";
    input.placeholder = "00.000.000/0001-00";
    $(input).mask('00.000.000/0000-00');
  } else {
    label.textContent = "CPF";
    input.placeholder = "000.000.000-00";
    $(input).mask('000.000.000-00');
  }
});

document.getElementById("loginForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const tipoUsuario = document.getElementById("tipoUsuario").value;
  const identificador = document.getElementById("cpf").value;
  const senha = document.getElementById("password").value;

  const data = { identificador, senha };

  let url;
  if (tipoUsuario === "aluno") {
    url = "http://localhost:8080/api/alunos/login";
  } else if (tipoUsuario === "professor") {
    url = "http://localhost:8080/api/professores/login";
  } else if (tipoUsuario === "empresa") {
    url = "http://localhost:8080/api/empresa/login";
  }

  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(data)
  })
    .then(response => {
      if (!response.ok) {
        throw new Error("Erro no login");
      }
      return response.json();
    })
    .then(data => {
      console.log("Login realizado com sucesso:", data);
      const usuarioLogado = {
    tipo: tipoUsuario,
    dados: data  // deve conter pelo menos `id` e `nome`
  };

  localStorage.setItem("usuarioLogado", JSON.stringify(usuarioLogado));

      alert("Login bem-sucedido!");
      window.location.href = "http://127.0.0.1:5500/implementacao/front/telainicial/telaInicial.html";
    })
    .catch(error => {
      console.error("Erro:", error);
      alert("Falha no login. Verifique seus dados.");
    });
});
