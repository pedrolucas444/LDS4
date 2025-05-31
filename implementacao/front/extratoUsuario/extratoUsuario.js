async function buscarExtrato() {
  const userId = document.getElementById("userId").value;

  if (!userId) {
      alert("Por favor, insira o ID do usuário.");
      return;
  }

  try {
      const alunoResponse = await fetch(`http://localhost:8080/api/alunos/${userId}`);

      if (!alunoResponse.ok) {
          const errorText = await alunoResponse.text();
          console.error("Erro ao buscar extrato:", errorText);
          alert("Erro ao buscar o extrato. Verifique o ID e tente novamente.");
          return;
      }

      const aluno = await alunoResponse.json();

      const extratoList = document.getElementById("extratoList");
      extratoList.innerHTML = "";

      const saldoItem = document.createElement("li");
      saldoItem.textContent = `Saldo de Moedas: ${aluno.saldoMoedas}`;
      extratoList.appendChild(saldoItem);

      const transacoesResponse = await fetch(`http://localhost:8080/api/transacoes`);

      if (!transacoesResponse.ok) {
          const errorText = await transacoesResponse.text();
          console.error("Erro ao buscar transações:", errorText);
          alert("Erro ao buscar as transações. Tente novamente.");
          return;
      }

      const transacoes = await transacoesResponse.json();

      const transacoesDoAluno = transacoes.filter(transacao => transacao.aluno.id === aluno.id);

      if (transacoesDoAluno && transacoesDoAluno.length > 0) {
          transacoesDoAluno.forEach(transacao => {
              const listItem = document.createElement("li");
              listItem.textContent = `Data: ${new Date(transacao.data).toLocaleDateString()} - Tipo: ${transacao.tipo} - Montante: ${transacao.montante}`;
              extratoList.appendChild(listItem);
          });
      } else {
          const listItem = document.createElement("li");
          listItem.textContent = "Nenhuma transação encontrada.";
          extratoList.appendChild(listItem);
      }
  } catch (error) {
      console.error("Erro:", error);
      alert("Ocorreu um erro ao buscar o extrato.");
  }
}