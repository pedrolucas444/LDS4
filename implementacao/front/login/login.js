document.getElementById("loginForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const tipoUsuario = document.getElementById("tipoUsuario").value;
  const email = document.getElementById("email").value;
  const senha = document.getElementById("password").value;

  const data = {
    email,
    senha
  };

  let url;
  if (tipoUsuario === "aluno") {
    url = "http://localhost:8080/api/alunos/login";
  } else if (tipoUsuario === "professor") {
    url = "http://localhost:8080/api/professores/login";
  } else if (tipoUsuario === "empresa") {
    url = "http://localhost:8080/api/empresas/login";
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
      // Redirecionamento pode variar conforme o tipo de usuário
      // window.location.href = `/painel-${tipoUsuario}.html`;
    })
    .catch(error => {
      console.error("Erro:", error);
      alert("Falha no login. Verifique seu email e senha.");
    });
});
