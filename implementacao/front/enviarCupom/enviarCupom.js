document.getElementById('enviarCupomForm').addEventListener('submit', async function(event) {
    event.preventDefault();
    const alunoId = document.getElementById('alunoId').value;
    const vantagemId = document.getElementById('vantagemId').value;
    const mensagemDiv = document.getElementById('mensagem');
    mensagemDiv.textContent = 'Enviando...';

    try {
        const response = await fetch(`http://localhost:8080/api/trocas/${vantagemId}?alunoId=${alunoId}`, {
            method: 'POST',
        });
        if (response.ok) {
            const data = await response.text();
            mensagemDiv.textContent = 'Cupom enviado com sucesso!\n' + data;
        } else {
            const error = await response.text();
            mensagemDiv.textContent = 'Erro ao enviar cupom: ' + error;
        }
    } catch (err) {
        mensagemDiv.textContent = 'Erro de conexão com o servidor.';
    }
}); 