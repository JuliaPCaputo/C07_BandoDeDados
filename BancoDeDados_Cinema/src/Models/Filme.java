package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class Filme extends Cinema {
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

    // READ
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

    // READ ALL
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

    // DELETE
    public static int delete(Connection conn, String titulo) throws SQLException {
        String sql = "DELETE FROM Filme WHERE Titulo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            return stmt.executeUpdate();
        }
    }

    public static void menuFilme() throws Exception {
        System.out.println("1 - Adicionar | 2 - Buscar por Título | 3 - Listar Todos | 4 - Deletar | 0 - Voltar");
        System.out.print("Opção: ");
        String op = scanner.nextLine();

        switch (op) {
            case "1" -> { // CREATE
                System.out.print("Título: "); String titulo = scanner.nextLine();
                System.out.print("Duração (min): "); int duracao = Integer.parseInt(scanner.nextLine());
                System.out.print("Gênero: "); String genero = scanner.nextLine();
                System.out.print("Classificação: "); String classificacao = scanner.nextLine();
                System.out.print("Diretor: "); String diretor = scanner.nextLine();
                Filme.create(conn, new Filme(titulo, duracao, genero, classificacao, diretor));
                System.out.println("Filme criado com sucesso!");
            }
            case "2" -> { // READ
                System.out.print("Título do Filme a buscar: "); String titulo = scanner.nextLine();
                Filme f = Filme.read(conn, titulo);
                System.out.println(f != null ? "Encontrado: " + f : "Não encontrado.");
            }
            case "3" -> { // READ ALL
                List<Filme> filmes = Filme.readAll(conn);
                if (filmes.isEmpty()) {
                    System.out.println("Nenhum filme cadastrado no momento.");
                } else {
                    System.out.println("\n--- FILMES CADASTRADOS ---");
                    filmes.forEach(f -> System.out.println(f));
                }
            }
            case "4" -> { // DELETE
                System.out.print("Título do Filme a deletar: "); String titulo = scanner.nextLine();

                int linhasAfetadas = Filme.delete(conn, titulo); // usa o retorno da função DELETE para saber se o filme foi ou não apagado

                if (linhasAfetadas > 0) {
                    System.out.println("Filme '" + titulo + "' apagado com sucesso!");
                } else {
                    System.out.println("Erro: Filme '" + titulo + "' não encontrado ou não apagado.");
                }
            }
            case "0" -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    @Override
    public String toString() {
        return "Filme{" +
                "Título='" + titulo +
                ", Duração=" + duracao + " min" +
                ", Gênero='" + genero +
                ", Classificação='" + classificacao +
                ", Diretor='" + diretor +
                '}';
    }
}
