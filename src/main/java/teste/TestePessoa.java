package teste;

import com.empresa.Pessoa;
import dao.PessoaDAO;

import java.util.List;

public class TestePessoa {
    public static void main(String[] args) {
        PessoaDAO pessoaDAO = new PessoaDAO();

        // criar uma nova pessoa
        Pessoa p = new Pessoa();
        p.setNome("Matheus Howe Habeck");
        p.setEmail("matheus_habeck@estudante.sesisenai.org.br");

        // inserir no banco
        boolean inseriu = pessoaDAO.inserir(p);
        if (inseriu) {
            System.out.println("Pessoa inserida com sucesso! ID: " + p.getId());
        }

        // listar todas as pessoas
        List<Pessoa> pessoas = pessoaDAO.listarTodos();
        System.out.println("Lista de pessoas cadastradas:");
        for (Pessoa pessoa : pessoas) {
            System.out.println(pessoa.getId() + " - " + pessoa.getNome() + " - " + pessoa.getEmail());
        }
    }
}
