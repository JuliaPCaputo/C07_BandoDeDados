package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Filme {
    private String titulo;
    private int duracao;
    private String genero;
    private String classificacao; // classificação indicativa
    private String diretor;

    public Filme(String titulo, int duracao, String genero, String classificacao, String diretor) {
        this.titulo = titulo;
        this.duracao = duracao;
        this.genero = genero;
        this.classificacao = classificacao;
        this.diretor = diretor;
    }

    // CREATE
    public static void create(Connection conn, Filme filme) throws SQLException {
        String sql = "INSERT INTO Filme (Titulo, Duracao, Genero, Classificacao, Diretor) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, filme.titulo);
            stmt.setInt(2, filme.duracao);
            stmt.setString(3, filme.genero);
            stmt.setString(4, filme.classificacao);
            stmt.setString(5, filme.diretor);
            stmt.executeUpdate();
        }
    }

    public static Filme read(Connection conn, String titulo) throws SQLException {
        String sql = "SELECT * FROM Filme WHERE Titulo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Filme(
                        rs.getString("Titulo"),
                        rs.getInt("Duracao"),
                        rs.getString("Genero"),
                        rs.getString("Classificacao"),
                        rs.getString("Diretor")
                );
            }
        }
        return null;
    }

    public static List<Filme> readAll(Connection conn) throws SQLException {
        List<Filme> filmes = new ArrayList<>();
        String sql = "SELECT * FROM Filme";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                filmes.add(new Filme(
                        rs.getString("Titulo"),
                        rs.getInt("Duracao"),
                        rs.getString("Genero"),
                        rs.getString("Classificacao"),
                        rs.getString("Diretor")
                ));
            }
        }
        return filmes;
    }

    public static void delete(Connection conn, String titulo) throws SQLException {
        String sql = "DELETE FROM Filme WHERE Titulo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.executeUpdate();
        }
    }

    public String getTitulo() {
        return titulo;
    }


}
