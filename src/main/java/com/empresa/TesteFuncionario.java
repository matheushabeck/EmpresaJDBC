package com.empresa;

import java.util.List;

public class TesteFuncionario {
    public static void main(String[] args) {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        // cria uma pessoa para vincular ao funcionário (caso não exista)
        PessoaDAO pessoaDAO = new PessoaDAO();
        Pessoa p = new Pessoa();
        p.setNome("João Silva");
        p.setEmail("joao.silva@email.com");

        if (pessoaDAO.inserir(p)) {
            System.out.println("Pessoa criada com ID: " + p.getId());
        }

        // Cria um funcionário vinculado a pessoa criada
        Funcionario f = new Funcionario();
        f.setId(p.getId());
        f.setMatricula("F123");
        f.setDepartamento("TI");

        // insere funcionário
        boolean inseriu = funcionarioDAO.inserir(f);
        if (inseriu) {
            System.out.println("Funcionário inserido com sucesso!");
        }

        // lista todos os funcionários
        List<Funcionario> funcionarios = funcionarioDAO.listarTodos();
        System.out.println("Funcionários cadastrados:");
        for (Funcionario func : funcionarios) {
            System.out.println(func.getId() + " - " + func.getNome() + " - " + func.getMatricula() + " - " + func.getDepartamento());
        }
    }
}
