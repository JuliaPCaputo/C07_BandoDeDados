package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String CPF;
    private String nome;
    private String email;
    private String telefone;

    public Cliente(String CPF, String nome, String email, String telefone) {
        this.CPF = CPF;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    // CREATE
    public static void create(Connection conn, Cliente c) throws SQLException {
        String sql = "INSERT INTO Cliente (CPF, Nome, Email, Telefone) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.CPF);
            stmt.setString(2, c.nome);
            stmt.setString(3, c.email);
            stmt.setString(4, c.telefone);
            stmt.executeUpdate();
        }
    }

    // READ
    public static Cliente read(Connection conn, String cpf) throws SQLException {
        String sql = "SELECT * FROM Cliente WHERE CPF = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getString("CPF"),
                        rs.getString("Nome"),
                        rs.getString("Email"),
                        rs.getString("Telefone")
                );
            }
        }
        return null;
    }

    // READ ALL
    public static List<Cliente> readAll(Connection conn) throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getString("CPF"),
                        rs.getString("Nome"),
                        rs.getString("Email"),
                        rs.getString("Telefone")
                ));
            }
        }
        return clientes;
    }

    // DELETE
    public static void delete(Connection conn, String cpf) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE CPF = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        }
    }
}
