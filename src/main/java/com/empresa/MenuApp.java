package com.empresa;

import java.util.List;
import java.util.Scanner;

public class MenuApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final PessoaDAO pessoaDAO = new PessoaDAO();
    private static final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private static final ProjetoDAO projetoDAO = new ProjetoDAO();

    public static void main(String[] args) {
        boolean rodando = true;

        while (rodando) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            int opcao = sc.nextInt();
            sc.nextLine();

            // navegação dependendo com a opção escolhida
            switch (opcao) {
                case 1: menuCadastrar(); break;
                case 2: menuListar(); break;
                case 3: menuAtualizar(); break;
                case 4: menuExcluir(); break;
                case 0:
                    rodando = false;
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        sc.close();
    }

    // submenu para operações de cadastro
    private static void menuCadastrar() {
        System.out.println("\n-- CADASTRAR --");
        System.out.println("1 - Pessoa");
        System.out.println("2 - Funcionário");
        System.out.println("3 - Projeto");
        System.out.println("0 - Voltar");
        System.out.print("Escolha: ");

        int opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao) {
            case 1: cadastrarPessoa(); break;
            case 2: cadastrarFuncionario(); break;
            case 3: cadastrarProjeto(); break;
            case 0: return;
            default: System.out.println("Opção inválida!");
        }
    }

    // Submenu para listagem de registros
    private static void menuListar() {
        System.out.println("\n-- LISTAR --");
        System.out.println("1 - Pessoas");
        System.out.println("2 - Funcionários");
        System.out.println("3 - Projetos");
        System.out.println("0 - Voltar");
        System.out.print("Escolha: ");

        int opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao) {
            case 1: listarPessoas(); break;
            case 2: listarFuncionarios(); break;
            case 3: listarProjetos(); break;
            case 0: return;
            default: System.out.println("Opção inválida!");
        }
    }

    // submenu para atualização de registros
    private static void menuAtualizar() {
        System.out.println("\n-- ATUALIZAR --");
        System.out.println("1 - Pessoa");
        System.out.println("2 - Funcionário");
        System.out.println("3 - Projeto");
        System.out.println("0 - Voltar");
        System.out.print("Escolha: ");

        int opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao) {
            case 1: atualizarPessoa(); break;
            case 2: atualizarFuncionario(); break;
            case 3: atualizarProjeto(); break;
            case 0: return;
            default: System.out.println("Opção inválida!");
        }
    }

    // submenu para exclusão de registros
    private static void menuExcluir() {
        System.out.println("\n-- EXCLUIR --");
        System.out.println("1 - Pessoa");
        System.out.println("2 - Funcionário");
        System.out.println("3 - Projeto");
        System.out.println("0 - Voltar");
        System.out.print("Escolha: ");

        int opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao) {
            case 1: excluirPessoa(); break;
            case 2: excluirFuncionario(); break;
            case 3: excluirProjeto(); break;
            case 0: return;
            default: System.out.println("Opção inválida!");
        }
    }

    // cadastro básico de Pessoa
    private static void cadastrarPessoa() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setEmail(email);

        if (pessoaDAO.inserir(pessoa)) {
            System.out.println("Pessoa cadastrada com sucesso! ID: " + pessoa.getId());
        }
    }

    // cadastro de Funcionário usando ID da Pessoa já cadastrada
    private static void cadastrarFuncionario() {
        System.out.print("ID da Pessoa: ");
        int idPessoa = sc.nextInt();
        sc.nextLine();

        System.out.print("Matrícula (F + 3 números): ");
        String matricula = sc.nextLine();

        System.out.print("Departamento: ");
        String departamento = sc.nextLine();

        Funcionario funcionario = new Funcionario();
        funcionario.setId(idPessoa);
        funcionario.setMatricula(matricula);
        funcionario.setDepartamento(departamento);

        funcionarioDAO.inserir(funcionario);
    }

    // cadastro de Projeto associando a um funcionário
    private static void cadastrarProjeto() {
        System.out.print("Nome do projeto: ");
        String nome = sc.nextLine();

        System.out.print("Descrição do projeto: ");
        String descricao = sc.nextLine();

        System.out.print("ID do funcionário responsável: ");
        int idFuncionario = sc.nextInt();
        sc.nextLine();

        Projeto projeto = new Projeto();
        projeto.setNome(nome);
        projeto.setDescricao(descricao);
        projeto.setIdFuncionario(idFuncionario);

        projetoDAO.inserir(projeto);
    }

    // mostra todas as pessoas com seus dados e IDs
    private static void listarPessoas() {
        List<Pessoa> pessoas = pessoaDAO.listarTodos();
        System.out.println("Pessoas cadastradas:");
        for (Pessoa p : pessoas) {
            System.out.println(p.getId() + " - " + p.getNome() + " - " + p.getEmail());
        }
    }

    // mostra todos os funcionários com seus dados e IDs
    private static void listarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioDAO.listarTodos();
        System.out.println("Funcionários cadastrados:");
        for (Funcionario f : funcionarios) {
            System.out.println(f.getId() + " - " + f.getNome() + " - " + f.getMatricula() + " - " + f.getDepartamento());
        }
    }

    // mostra todos os projetos com seus dados e ID do responsável
    private static void listarProjetos() {
        List<Projeto> projetos = projetoDAO.listarTodos();
        System.out.println("Projetos cadastrados:");
        for (Projeto p : projetos) {
            System.out.println(p.getId() + " - " + p.getNome() + " - Funcionário ID: " + p.getIdFuncionario());
        }
    }

    // atualização de dados de uma pessoa já cadastrada
    private static void atualizarPessoa() {
        System.out.print("ID da Pessoa a atualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Pessoa pessoa = pessoaDAO.buscarPorId(id);
        if (pessoa == null) {
            System.out.println("Pessoa não encontrada.");
            return;
        }

        System.out.print("Novo nome (" + pessoa.getNome() + "): ");
        String nome = sc.nextLine();
        if (!nome.isEmpty()) pessoa.setNome(nome);

        System.out.print("Novo email (" + pessoa.getEmail() + "): ");
        String email = sc.nextLine();
        if (!email.isEmpty()) pessoa.setEmail(email);

        pessoaDAO.atualizar(pessoa);
    }

    private static void atualizarFuncionario() {
        System.out.print("ID do Funcionário a atualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Funcionario funcionario = funcionarioDAO.buscarPorId(id);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        System.out.print("Nova matrícula (" + funcionario.getMatricula() + "): ");
        String matricula = sc.nextLine();
        if (!matricula.isEmpty()) funcionario.setMatricula(matricula);

        System.out.print("Novo departamento (" + funcionario.getDepartamento() + "): ");
        String departamento = sc.nextLine();
        if (!departamento.isEmpty()) funcionario.setDepartamento(departamento);

        funcionarioDAO.atualizar(funcionario);
    }

    private static void atualizarProjeto() {
        System.out.print("ID do Projeto a atualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Projeto projeto = projetoDAO.buscarPorId(id);
        if (projeto == null) {
            System.out.println("Projeto não encontrado.");
            return;
        }

        System.out.print("Novo nome (" + projeto.getNome() + "): ");
        String nome = sc.nextLine();
        if (!nome.isEmpty()) projeto.setNome(nome);

        System.out.print("Nova descrição (" + projeto.getDescricao() + "): ");
        String descricao = sc.nextLine();
        if (!descricao.isEmpty()) projeto.setDescricao(descricao);

        System.out.print("Novo ID do funcionário responsável (" + projeto.getIdFuncionario() + "): ");
        String idFuncStr = sc.nextLine();
        if (!idFuncStr.isEmpty()) {
            try {
                int idFunc = Integer.parseInt(idFuncStr);
                projeto.setIdFuncionario(idFunc);
            } catch (NumberFormatException e) {
                System.out.println("ID inválido, valor não alterado.");
            }
        }

        projetoDAO.atualizar(projeto);
    }

    // exclusão de pessoa por ID
    private static void excluirPessoa() {
        System.out.print("ID da Pessoa a excluir: ");
        int id = sc.nextInt();
        sc.nextLine();

        pessoaDAO.excluir(id);
    }

    private static void excluirFuncionario() {
        System.out.print("ID do Funcionário a excluir: ");
        int id = sc.nextInt();
        sc.nextLine();

        funcionarioDAO.excluir(id);
    }

    private static void excluirProjeto() {
        System.out.print("ID do Projeto a excluir: ");
        int id = sc.nextInt();
        sc.nextLine();

        projetoDAO.excluir(id);
    }
}
