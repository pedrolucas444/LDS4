document.getElementById("tipoUsuario").addEventListener("change", function () {
  const tipo = this.value;
  const label = document.querySelector("label[for='email']");
  const input = document.getElementById("email");

  if (tipo === "empresa") {
    label.textContent = "CNPJ";
    input.type = "text";
    input.placeholder = "00.000.000/0001-00";
  } else if (tipo === "aluno" || tipo === "professor") {
    label.textContent = "CPF";
    input.type = "text";
    input.placeholder = "000.000.000-00";
  } else {
    label.textContent = "Identificador";
    input.type = "text";
    input.placeholder = "";
  }
});

document.getElementById("loginForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const tipoUsuario = document.getElementById("tipoUsuario").value;
  const identificador = document.getElementById("email").value;
  const senha = document.getElementById("password").value;

  const data = {
    identificador,
    senha
  };

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
      alert("Login bem-sucedido!");

      // Redirecionar para a dashboard do usuário
      // window.location.href = `/painel-${tipoUsuario}.html`;
    })
    .catch(error => {
      console.error("Erro:", error);
      alert("Falha no login. Verifique seus dados.");
    });
});
