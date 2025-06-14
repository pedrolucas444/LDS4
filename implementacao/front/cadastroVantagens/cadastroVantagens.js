document.addEventListener("DOMContentLoaded", async () => {
    const empresaSelect = document.getElementById("empresaId");
    const formulario = document.getElementById("cadastroVantagensForm");

    
    async function carregarEmpresas() {
        try {
            const response = await fetch("http://localhost:8080/api/empresa");
            if (!response.ok) {
                throw new Error("Erro ao carregar empresas");
            }
            const empresas = await response.json();

            
            empresas.forEach(empresa => {
                const option = document.createElement("option");
                option.value = empresa.id; 
                option.textContent = empresa.nome; 
                empresaSelect.appendChild(option);
            });
        } catch (error) {
            console.error("Erro ao carregar empresas:", error);
        }
    }

    
    formulario.addEventListener("submit", async (event) => {
        event.preventDefault(); 

        const dados = {
            empresa: {
                id: parseInt(empresaSelect.value) 
            },
            descricao: document.getElementById("descricao").value, 
            custo: parseInt(document.getElementById("custo").value),
            foto: document.getElementById("imagemUrl").value 
        };

        try {
            const response = await fetch("http://localhost:8080/api/vantagens", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(dados)
            });

            if (!response.ok) {
                throw new Error("Erro ao cadastrar vantagem");
            }

            alert("Vantagem cadastrada com sucesso!");
            formulario.reset(); 
        } catch (error) {
            console.error("Erro:", error);
            alert("Erro ao cadastrar vantagem. Tente novamente.");
        }
    });

    
    await carregarEmpresas();
}); 