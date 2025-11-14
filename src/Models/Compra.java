package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Compra {
    private String clienteCPF; // Fazendo referência à tabela Cliente
    private int ingressoId; // Fazendo referência à tabela Ingresso

    public Compra(String clienteCPF, int ingressoId) {
        this.clienteCPF = clienteCPF;
        this.ingressoId = ingressoId;
    }

    // CREATE (Inserir uma nova compra)
    public static void create(Connection conn, Compra c) throws SQLException {
        String sql = "INSERT INTO Compra (Cliente_CPF, Ingresso_idIngresso) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.clienteCPF);
            stmt.setInt(2, c.ingressoId);
            stmt.executeUpdate();
        }
    }

    // READ (Buscar uma compra específica pela chave composta)
    public static Compra read(Connection conn, String clienteCPF, int ingressoId) throws SQLException {
        String sql = "SELECT * FROM Compra WHERE Cliente_CPF = ? AND Ingresso_idIngresso = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, clienteCPF);
            stmt.setInt(2, ingressoId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Compra(
                        rs.getString("Cliente_CPF"),
                        rs.getInt("Ingresso_idIngresso")
                );
            }
        }
        return null;
    }

    // READ ALL (Buscar todas as compras)
    public static List<Compra> readAll(Connection conn) throws SQLException {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT * FROM Compra";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                compras.add(new Compra(
                        rs.getString("Cliente_CPF"),
                        rs.getInt("Ingresso_idIngresso")
                ));
            }
        }
        return compras;
    }

    // DELETE (Deletar uma compra pela chave composta)
    public static void delete(Connection conn, String clienteCPF, int ingressoId) throws SQLException {
        String sql = "DELETE FROM Compra WHERE Cliente_CPF = ? AND Ingresso_idIngresso = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, clienteCPF);
            stmt.setInt(2, ingressoId);
            stmt.executeUpdate();
        }
    }

    public String getClienteCPF() {
        return clienteCPF;
    }

    public int getIngressoId() {
        return ingressoId;
    }
}
