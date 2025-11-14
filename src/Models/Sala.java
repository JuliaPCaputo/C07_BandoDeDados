package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Sala {
    private int idSala;
    private int capacidade;
    private String tipoSala; // 2D, 3D etc.

    public Sala(int idSala, int capacidade, String tipoSala) {
        this.idSala = idSala;
        this.capacidade = capacidade;
        this.tipoSala = tipoSala;
    }

    // CREATE
    public static void create(Connection conn, Sala sala) throws SQLException {
        String sql = "INSERT INTO Sala (idSala, Capacidade, tipoSala) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sala.idSala);
            stmt.setInt(2, sala.capacidade);
            stmt.setString(3, sala.tipoSala);
            stmt.executeUpdate();
        }
    }

    // READ
    public static Sala read(Connection conn, int idSala) throws SQLException {
        String sql = "SELECT * FROM Sala WHERE idSala = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSala);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Sala(
                        rs.getInt("idSala"),
                        rs.getInt("Capacidade"),
                        rs.getString("tipoSala")
                );
            }
        }
        return null;
    }

    // READ ALL
    public static List<Sala> readAll(Connection conn) throws SQLException {
        List<Sala> salas = new ArrayList<>();
        String sql = "SELECT * FROM Sala";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                salas.add(new Sala(
                        rs.getInt("idSala"),
                        rs.getInt("Capacidade"),
                        rs.getString("tipoSala")
                ));
            }
        }
        return salas;
    }

    // DELETE
    public static void delete(Connection conn, int idSala) throws SQLException {
        String sql = "DELETE FROM Sala WHERE idSala = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSala);
            stmt.executeUpdate();
        }
    }
}


