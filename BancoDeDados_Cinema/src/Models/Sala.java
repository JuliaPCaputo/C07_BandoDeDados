package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Sala extends Cinema {
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
    public static int delete(Connection conn, int idSala) throws SQLException {
        String sql = "DELETE FROM Sala WHERE idSala = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSala);
            return stmt.executeUpdate();
        }
    }

    public static void menuSala() throws Exception {
        System.out.println("1 - Adicionar | 2 - Buscar por ID | 3 - Listar Todas | 4 - Deletar | 0 - Voltar");
        System.out.print("Opção: ");
        String op = scanner.nextLine();

        switch (op) {
            case "1" -> { // CREATE
                System.out.print("ID da Sala: "); int idSala = Integer.parseInt(scanner.nextLine());
                System.out.print("Capacidade: "); int capacidade = Integer.parseInt(scanner.nextLine());
                System.out.print("Tipo de Sala (2D/3D/IMAX): "); String tipoSala = scanner.nextLine();
                Sala.create(conn, new Sala(idSala, capacidade, tipoSala));
                System.out.println("Sala criada com sucesso!");
            }
            case "2" -> { // READ
                System.out.print("ID da Sala a buscar: "); int idSala = Integer.parseInt(scanner.nextLine());
                Sala s = Sala.read(conn, idSala);
                System.out.println(s != null ? "Encontrada: " + s : "Não encontrada.");
            }
            case "3" -> { // READ ALL
                List<Sala> salas = Sala.readAll(conn);
                if (salas.isEmpty()) {
                    System.out.println("Nenhuma sala cadastrada no momento.");
                } else {
                    System.out.println("\n--- SALAS CADASTRADOS ---");
                    salas.forEach(f -> System.out.println(f));
                }
            }
            case "4" -> { // DELETE
                System.out.print("ID da Sala a deletar: "); int idSala = Integer.parseInt(scanner.nextLine());
                int linhasAfetadas = Sala.delete(conn, idSala); // usa o retorno da função DELETE para saber se a sala foi ou não apagada

                if (linhasAfetadas > 0) {
                    System.out.println("Sala '" + idSala + "' apagado com sucesso!");
                } else {
                    System.out.println("Erro: Sala '" + idSala + "' não encontrada ou não apagada.");
                }
            }
            case "0" -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    @Override
    public String toString() {
        return "Sala{" +
                "ID=" + idSala +
                ", Capacidade=" + capacidade + " assentos" +
                ", Tipo='" + tipoSala +
                '}';
    }
}
