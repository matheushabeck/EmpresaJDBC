package dao;

import com.empresa.Conexao;
import com.empresa.Pessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    // Inserir uma nova Pessoa no banco
    public boolean inserir(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (nome, email) VALUES (?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getEmail());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Erro: Nenhuma linha inserida.");
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pessoa.setId(generatedKeys.getInt(1));  // seta o ID gerado no objeto
                }
            }
            System.out.println("Pessoa inserida com sucesso! ID: " + pessoa.getId());
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir pessoa: " + e.getMessage());
            return false;
        }
    }

    // Atualizar dados de uma Pessoa existente
    public boolean atualizar(Pessoa pessoa) {
        String sql = "UPDATE pessoa SET nome = ?, email = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getEmail());
            stmt.setInt(3, pessoa.getId());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Erro: Pessoa não encontrada.");
                return false;
            }

            System.out.println("Pessoa atualizada com sucesso!");
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pessoa: " + e.getMessage());
            return false;
        }
    }

    // Excluir uma Pessoa pelo ID
    public boolean excluir(int id) {
        String sql = "DELETE FROM pessoa WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows == 0) {
                System.out.println("Erro: Pessoa não encontrada.");
                return false;
            }

            System.out.println("Pessoa excluída com sucesso!");
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir pessoa: " + e.getMessage());
            return false;
        }
    }

    // Buscar uma Pessoa pelo ID
    public Pessoa buscarPorId(int id) {
        String sql = "SELECT * FROM pessoa WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Pessoa(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email")
                );
            } else {
                System.out.println("Pessoa não encontrada.");
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar pessoa: " + e.getMessage());
            return null;
        }
    }

    // Listar todas as Pessoas do banco
    public List<Pessoa> listarTodos() {
        List<Pessoa> lista = new ArrayList<>();
        String sql = "SELECT * FROM pessoa";
        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Pessoa(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar pessoas: " + e.getMessage());
        }
        return lista;
    }
}
