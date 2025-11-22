package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Ingresso extends Cinema {
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

    // READ ALL
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

    // DELETE
    public static int delete(Connection conn, int idIngressoParam) throws SQLException {
        String sql = "DELETE FROM ingresso WHERE idIngresso = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idIngressoParam);
            return stmt.executeUpdate();
        }
    }

    public static void menuIngresso() throws Exception {
        System.out.println("1 - Adicionar | 2 - Buscar por ID | 3 - Listar Todos | 4 - Deletar | 0 - Voltar");
        System.out.print("Opção: ");
        String op = scanner.nextLine();

        switch (op) {
            case "1" -> { // CREATE
                System.out.print("ID do Ingresso: "); int idIngresso = Integer.parseInt(scanner.nextLine());
                System.out.print("Poltrona: "); int poltrona = Integer.parseInt(scanner.nextLine());
                System.out.print("Tipo (Meia/Inteira): "); String tipoIngresso = scanner.nextLine();
                System.out.print("Preço: "); double preco = Double.parseDouble(scanner.nextLine());
                System.out.print("Status (1=Disponível, 0=Vendido): "); boolean status = Integer.parseInt(scanner.nextLine()) == 1;
                System.out.print("Número da Sessão: "); int sessaoNum = Integer.parseInt(scanner.nextLine());

                int novoId = Ingresso.create(conn, new Ingresso(idIngresso, poltrona, tipoIngresso, preco, status, sessaoNum));
                if (novoId != -1) {
                    System.out.println("Ingresso criado com ID: " + novoId);
                } else {
                    System.out.println("Ingresso criado com sucesso!");
                }
            }
            case "2" -> { // READ
                System.out.print("ID do Ingresso a buscar: "); int id = Integer.parseInt(scanner.nextLine());
                Ingresso i = Ingresso.read(conn, id);
                System.out.println(i != null ? "Encontrado: " + i : "Não encontrado.");
            }
            case "3" -> { // READ ALL
                List<Ingresso> ingressos = Ingresso.readAll(conn);
                if (ingressos.isEmpty()) {
                    System.out.println("Nenhum ingresso cadastrado no momento.");
                } else {
                    System.out.println("\n--- INGRESSOS CADASTRADOS ---");
                    ingressos.forEach(f -> System.out.println(f));
                }
            }
            case "4" -> { // DELETE
                System.out.print("ID do Ingresso a deletar: "); int id = Integer.parseInt(scanner.nextLine());
                int linhasAfetadas = Ingresso.delete(conn, id); // usa o retorno da função DELETE para saber se o ingresso foi ou não apagado

                if (linhasAfetadas > 0) {
                    System.out.println("Ingresso '" + id + "' apagado com sucesso!");
                } else {
                    System.out.println("Erro: Ingresso '" + id + "' não encontrado ou não apagado.");
                }
            }
            case "0" -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    @Override
    public String toString() {
        return "Ingresso{" +
                "ID=" + idIngresso +
                ", Poltrona=" + poltrona +
                ", Tipo='" + tipoIngresso +
                ", Preco=" + preco +
                ", Status=" + status +
                ", SessaoNum=" + sessaoNum +
                '}';
    }
}
