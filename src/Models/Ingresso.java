package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Ingresso {
    private int idIngresso;
    private int poltrona;
    private String tipoIngresso; // meia/inteira
    private double preco;
    private boolean status; // TRUE = disponível, FALSE = vendido
    private int sessaoNum; // FK Sessao_numSessao

    public Ingresso(int idIngresso, int poltrona, String tipoIngresso, double preco, boolean status, int sessaoNum) {
        this.idIngresso = idIngresso;
        this.poltrona = poltrona;
        this.tipoIngresso = tipoIngresso;
        this.preco = preco;
        this.status = status;
        this.sessaoNum = sessaoNum;
    }

    // CREATE
    public static int create(Connection conn, Ingresso ingresso) throws SQLException {
        String sql = """
            INSERT INTO ingresso (idIngresso, poltrona, tipoIngresso, preco, status, Sessao_numSessao)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, ingresso.idIngresso);
            stmt.setInt(2, ingresso.poltrona);
            stmt.setString(3, ingresso.tipoIngresso);
            stmt.setDouble(4, ingresso.preco);
            stmt.setBoolean(5, ingresso.status);
            stmt.setInt(6, ingresso.sessaoNum);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    // READ
    public static Ingresso read(Connection conn, int idIngressoParam) throws SQLException {
        String sql = "SELECT * FROM ingresso WHERE idIngresso = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idIngressoParam);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Ingresso(
                        rs.getInt("idIngresso"),
                        rs.getInt("poltrona"),
                        rs.getString("tipoIngresso"),
                        rs.getDouble("preco"),
                        rs.getBoolean("status"),
                        rs.getInt("Sessao_numSessao")
                );
            }
        }

        return null;
    }

    // ---------- READ ALL ----------
    public static List<Ingresso> readAll(Connection conn) throws SQLException {
        List<Ingresso> ingressos = new ArrayList<>();
        String sql = "SELECT * FROM ingresso";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ingressos.add(
                        new Ingresso(
                                rs.getInt("idIngresso"),
                                rs.getInt("poltrona"),
                                rs.getString("tipoIngresso"),
                                rs.getDouble("preco"),
                                rs.getBoolean("status"),
                                rs.getInt("Sessao_numSessao")
                        )
                );
            }
        }

        return ingressos;
    }

    // ---------- DELETE ----------
    public static void delete(Connection conn, int idIngressoParam) throws SQLException {
        String sql = "DELETE FROM ingresso WHERE idIngresso = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idIngressoParam);
            stmt.executeUpdate();
        }
    }
}
