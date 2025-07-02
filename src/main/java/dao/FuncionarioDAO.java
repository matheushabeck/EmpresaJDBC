package dao;

import com.empresa.Conexao;
import com.empresa.Funcionario;
import com.empresa.Pessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    private PessoaDAO pessoaDAO = new PessoaDAO();

    // inserir funcionário (regra: pessoa deve existir)
    public boolean inserir(Funcionario funcionario) {
        // verifica se pessoa existe
        Pessoa pessoa = pessoaDAO.buscarPorId(funcionario.getId());
        if (pessoa == null) {
            System.out.println("Erro: Pessoa com ID " + funcionario.getId() + " não existe.");
            return false;
        }

        // Valida matrícula (deve começar com 'F' e ter 4 caracteres)
        if (!funcionario.getMatricula().matches("F\\d{3}")) {
            System.out.println("Erro: Matrícula inválida. Deve ser no formato 'F' seguido de 3 números, ex: F007.");
            return false;
        }

        String sql = "INSERT INTO funcionario (id, matricula, departamento) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, funcionario.getId());
            stmt.setString(2, funcionario.getMatricula());
            stmt.setString(3, funcionario.getDepartamento());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Erro: Não foi possível inserir o funcionário.");
                return false;
            }

            System.out.println("Funcionário inserido com sucesso!");
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir funcionário: " + e.getMessage());
            return false;
        }
    }

    // atualizar o funcionário
    public boolean atualizar(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET matricula = ?, departamento = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getMatricula());
            stmt.setString(2, funcionario.getDepartamento());
            stmt.setInt(3, funcionario.getId());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Erro: Funcionário não encontrado.");
                return false;
            }

            System.out.println("Funcionário atualizado com sucesso!");
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar funcionário: " + e.getMessage());
            return false;
        }
    }

    // excluir o funcionário (proibido se ele estiver vinculado a algum projeto)
    public boolean excluir(int id) {
        // verificar vínculo com projetos
        if (temProjetoVinculado(id)) {
            System.out.println("Erro: Funcionário vinculado a projeto(s), exclusão proibida.");
            return false;
        }

        String sql = "DELETE FROM funcionario WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows == 0) {
                System.out.println("Erro: Funcionário não encontrado.");
                return false;
            }

            System.out.println("Funcionário excluído com sucesso!");
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir funcionário: " + e.getMessage());
            return false;
        }
    }

    // buscar funcionário por ID
    public Funcionario buscarPorId(int id) {
        String sql = "SELECT f.id, p.nome, p.email, f.matricula, f.departamento " +
                "FROM funcionario f JOIN pessoa p ON f.id = p.id WHERE f.id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("matricula"),
                        rs.getString("departamento")
                );
            } else {
                System.out.println("Funcionário não encontrado.");
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar funcionário: " + e.getMessage());
            return null;
        }
    }

    // listar todos os funcionários
    public List<Funcionario> listarTodos() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT f.id, p.nome, p.email, f.matricula, f.departamento " +
                "FROM funcionario f JOIN pessoa p ON f.id = p.id";

        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("matricula"),
                        rs.getString("departamento")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }
        return lista;
    }

    // verificar se funcionário tem projeto vinculado
    private boolean temProjetoVinculado(int idFuncionario) {
        String sql = "SELECT COUNT(*) AS total FROM projeto WHERE id_funcionario = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao verificar vínculo com projeto: " + e.getMessage());
        }
        return false;
    }
}
