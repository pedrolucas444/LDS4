function initMultiStepForm() {
    const submitBtn = document.querySelector(".submit");

    submitBtn.addEventListener("click", function (event) {
        event.preventDefault();

        const nome = document.getElementById("nome").value.trim();
        const email = document.getElementById("email").value.trim();
        const senha = document.getElementById("login-pass").value.trim();
        const cpf = document.getElementById("cpf").value.trim();
        const rg = document.getElementById("rg").value.trim();
        const endereco = document.getElementById("endereco").value.trim();
        const instituicao = document.getElementById("instituicao").value.trim();
        const curso = document.getElementById("curso").value.trim();

        const data = {
            nome,
            email,
            senha,
            cpf,
            rg,
            endereco,
            instituicao,
            curso
        };

        console.log("Dados enviados:", data);

        fetch("http://localhost:8080/api/alunos", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao enviar dados');
                }
                return response.json();
            })
            .then(data => {
                console.log('Cadastro realizado com sucesso:', data);
                // Redirecionar para a página de login
                window.location.href = "http://127.0.0.1:5500/implementacao/front/login/login.html"; // ou ajuste o caminho se for diferente
            })
            .catch(error => {
                alert("Ocorreu um erro ao cadastrar.");
                console.error('Erro:', error);
            });
    });
}

// Máscara de CPF sem jQuery
function aplicarMascaraCPF(input) {
    input.addEventListener("input", () => {
        let valor = input.value.replace(/\D/g, "");
        if (valor.length > 11) valor = valor.slice(0, 11);
        valor = valor.replace(/(\d{3})(\d)/, "$1.$2");
        valor = valor.replace(/(\d{3})(\d)/, "$1.$2");
        valor = valor.replace(/(\d{3})(\d{1,2})$/, "$1-$2");
        input.value = valor;
    });
}

window.onload = function () {
    initMultiStepForm();
    const cpfInput = document.querySelector(".input-cpf");
    aplicarMascaraCPF(cpfInput);
};
