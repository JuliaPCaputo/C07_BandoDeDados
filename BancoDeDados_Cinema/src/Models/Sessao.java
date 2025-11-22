package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Sessao extends Cinema{
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

    public static int delete(Connection conn, int num) throws SQLException {
        String sql = "DELETE FROM Sessao WHERE numSessao = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, num);
            return stmt.executeUpdate();
        }
    }

    public static void menuSessao() throws Exception {
        System.out.println("1 - Adicionar | 2 - Buscar por Número | 3 - Listar Todas | 4 - Deletar | 0 - Voltar");
        System.out.print("Opção: ");
        String op = scanner.nextLine();

        switch (op) {
            case "1" -> { // CREATE
                System.out.print("Número da Sessão: "); int numSessao = Integer.parseInt(scanner.nextLine());
                System.out.print("Data (AAAA-MM-DD): "); Date data = Date.valueOf(scanner.nextLine());
                System.out.print("Horário (HH:MM): "); String horario = scanner.nextLine();
                System.out.print("Título do Filme: "); String filmeTitulo = scanner.nextLine();
                System.out.print("ID da Sala: "); int salaId = Integer.parseInt(scanner.nextLine());
                Sessao.create(conn, new Sessao(numSessao, data, horario, filmeTitulo, salaId));
                System.out.println("Sessão criada com sucesso!");
            }
            case "2" -> { // READ
                System.out.print("Número da Sessão a buscar: "); int num = Integer.parseInt(scanner.nextLine());
                Sessao s = Sessao.read(conn, num);
                System.out.println(s != null ? "Encontrada: " + s : "Não encontrada.");
            }
            case "3" -> { // READ ALL
                List<Sessao> sessoes = Sessao.readAll(conn);
                if (sessoes.isEmpty()) {
                    System.out.println("Nenhuma sessão cadastrada no momento.");
                } else {
                    System.out.println("\n--- SESSÕES CADASTRADAS ---");
                    sessoes.forEach(f -> System.out.println(f));
                }
            }
            case "4" -> { // DELETE
                System.out.print("Número da Sessão a deletar: "); int num = Integer.parseInt(scanner.nextLine());
                int linhasAfetadas = Sessao.delete(conn, num); // usa o retorno da função DELETE para saber se a sessão foi ou não apagada

                if (linhasAfetadas > 0) {
                    System.out.println("Sessão '" + num + "' apagado com sucesso!");
                } else {
                    System.out.println("Erro: Sessão '" + num + "' não encontrado ou não apagado.");
                }
            }
            case "0" -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    @Override
    public String toString() {
        return "Sessao{" +
                "Número da sessão=" + numSessao +
                ", Data=" + data +
                ", Horário='" + horario +
                ", Filme_Titulo='" + filmeTitulo +
                ", Sala_idSala=" + salaId +
                '}';
    }
}
