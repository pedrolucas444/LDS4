function initMultiStepForm() {
    const submitBtn = document.querySelector(".submit");

    submitBtn.addEventListener("click", function(event) {
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
            alert("Cadastro realizado com sucesso!");
            console.log('Success:', data);
            // Redirecionar ou limpar campos se necessário
        })
        .catch(error => {
            alert("Ocorreu um erro ao cadastrar.");
            console.error('Erro:', error);
        });
    });
}

window.onload = function() {
    initMultiStepForm();
};

// Máscara de CPF
$(document).ready(function(){
    $(".input-cpf").mask("000.000.000-00");
});
