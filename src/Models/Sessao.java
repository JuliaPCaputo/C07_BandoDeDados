package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Sessao {
    private int numSessao;
    private Date data;
    private String horario;
    private String filmeTitulo;
    private int salaId;

    public Sessao(int numSessao, Date data, String horario, String filmeTitulo, int salaId) {
        this.numSessao = numSessao;
        this.data = data;
        this.horario = horario;
        this.filmeTitulo = filmeTitulo;
        this.salaId = salaId;
    }

    public static void create(Connection conn, Sessao s) throws SQLException {
        String sql = """
            INSERT INTO Sessao (numSessao, Data, Horario, Filme_Titulo, Sala_idSala)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, s.numSessao);
            stmt.setDate(2, s.data);
            stmt.setString(3, s.horario);
            stmt.setString(4, s.filmeTitulo);
            stmt.setInt(5, s.salaId);
            stmt.executeUpdate();
        }
    }

    public static Sessao read(Connection conn, int num) throws SQLException {
        String sql = "SELECT * FROM Sessao WHERE numSessao = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, num);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Sessao(
                        rs.getInt("numSessao"),
                        rs.getDate("Data"),
                        rs.getString("Horario"),
                        rs.getString("Filme_Titulo"),
                        rs.getInt("Sala_idSala")
                );
            }
        }
        return null;
    }

    public static List<Sessao> readAll(Connection conn) throws SQLException {
        List<Sessao> sessoes = new ArrayList<>();
        String sql = "SELECT * FROM Sessao";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                sessoes.add(new Sessao(
                        rs.getInt("numSessao"),
                        rs.getDate("Data"),
                        rs.getString("Horario"),
                        rs.getString("Filme_Titulo"),
                        rs.getInt("Sala_idSala")
                ));
            }
        }
        return sessoes;
    }

    public static void delete(Connection conn, int num) throws SQLException {
        String sql = "DELETE FROM Sessao WHERE numSessao = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, num);
            stmt.executeUpdate();
        }
    }
}
