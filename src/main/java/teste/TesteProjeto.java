package teste;

import com.empresa.Funcionario;
import com.empresa.Pessoa;
import com.empresa.Projeto;
import dao.FuncionarioDAO;
import dao.PessoaDAO;
import dao.ProjetoDAO;

import java.util.List;

public class TesteProjeto {
    public static void main(String[] args) {
        ProjetoDAO projetoDAO = new ProjetoDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        PessoaDAO pessoaDAO = new PessoaDAO();

        // cria pessoa para vincular funcionário (caso não exista)
        Pessoa p = new Pessoa();
        p.setNome("Ana Maria");
        p.setEmail("ana.maria@email.com");

        if (pessoaDAO.inserir(p)) {
            System.out.println("Pessoa criada com ID: " + p.getId());
        }

        // cria funcionário vinculado a pessoa criada
        Funcionario f = new Funcionario();
        f.setId(p.getId());
        f.setMatricula("F124");
        f.setDepartamento("Marketing");

        if (funcionarioDAO.inserir(f)) {
            System.out.println("Funcionário criado com sucesso!");
        }

        // cria projeto vinculado ao funcionário
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto X");
        projeto.setDescricao("Descrição do Projeto X");
        projeto.setIdFuncionario(f.getId());

        if (projetoDAO.inserir(projeto)) {
            System.out.println("Projeto criado com sucesso!");
        }

        // listar todos os projetos
        List<Projeto> projetos = projetoDAO.listarTodos();
        System.out.println("Projetos cadastrados:");
        for (Projeto pjt : projetos) {
            System.out.println(pjt.getId() + " - " + pjt.getNome() + " - Funcionário ID: " + pjt.getIdFuncionario());
        }
    }
}
