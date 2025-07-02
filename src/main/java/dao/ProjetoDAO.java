package dao;

import com.empresa.Conexao;
import com.empresa.Funcionario;
import com.empresa.Projeto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    // Inserir projeto (regra: deve ter funcionário existente)
    public boolean inserir(Projeto projeto) {
        // verifica se funcionário existe
        Funcionario funcionario = funcionarioDAO.buscarPorId(projeto.getIdFuncionario());
        if (funcionario == null) {
            System.out.println("Erro: Funcionário com ID " + projeto.getIdFuncionario() + " não existe.");
            return false;
        }

        String sql = "INSERT INTO projeto (nome, descricao, id_funcionario) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setInt(3, projeto.getIdFuncionario());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Erro: Não foi possível inserir o projeto.");
                return false;
            }

            // pega o id gerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    projeto.setId(generatedKeys.getInt(1));
                }
            }

            System.out.println("Projeto inserido com sucesso! ID: " + projeto.getId());
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir projeto: " + e.getMessage());
            return false;
        }
    }

    // atualiza projeto
    public boolean atualizar(Projeto projeto) {
        String sql = "UPDATE projeto SET nome = ?, descricao = ?, id_funcionario = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setInt(3, projeto.getIdFuncionario());
            stmt.setInt(4, projeto.getId());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Erro: Projeto não encontrado.");
                return false;
            }

            System.out.println("Projeto atualizado com sucesso!");
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar projeto: " + e.getMessage());
            return false;
        }
    }

    // exclui projeto
    public boolean excluir(int id) {
        String sql = "DELETE FROM projeto WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows == 0) {
                System.out.println("Erro: Projeto não encontrado.");
                return false;
            }

            System.out.println("Projeto excluído com sucesso!");
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir projeto: " + e.getMessage());
            return false;
        }
    }

    // busca projeto por ID
    public Projeto buscarPorId(int id) {
        String sql = "SELECT * FROM projeto WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Projeto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("id_funcionario")
                );
            } else {
                System.out.println("Projeto não encontrado.");
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar projeto: " + e.getMessage());
            return null;
        }
    }

    // lista todos os projetos
    public List<Projeto> listarTodos() {
        List<Projeto> lista = new ArrayList<>();
        String sql = "SELECT * FROM projeto";
        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Projeto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("id_funcionario")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar projetos: " + e.getMessage());
        }
        return lista;
    }
}
