package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Compra extends Cinema {
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
    public static int delete(Connection conn, String clienteCPF, int ingressoId) throws SQLException {
        String sql = "DELETE FROM Compra WHERE Cliente_CPF = ? AND Ingresso_idIngresso = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, clienteCPF);
            stmt.setInt(2, ingressoId);
            return stmt.executeUpdate();
        }
    }

    public static void menuCompra() throws Exception {
        System.out.println("1 - Registrar Compra | 2 - Buscar Compra Específica | 3 - Listar Todas | 4 - Deletar Compra | 0 - Voltar");
        System.out.print("Opção: ");
        String op = scanner.nextLine();

        switch (op) {
            case "1" -> { // CREATE
                System.out.print("CPF do Cliente: "); String cpf = scanner.nextLine();
                System.out.print("ID do Ingresso Comprado: "); int idIngresso = Integer.parseInt(scanner.nextLine());
                Compra.create(conn, new Compra(cpf, idIngresso));
                System.out.println("Compra registrada com sucesso!");
            }
            case "2" -> { // READ
                System.out.print("CPF do Cliente da Compra: "); String cpf = scanner.nextLine();
                System.out.print("ID do Ingresso da Compra: "); int idIngresso = Integer.parseInt(scanner.nextLine());
                Compra c = Compra.read(conn, cpf, idIngresso);
                System.out.println(c != null ? "Encontrada: " + c : "Não encontrada.");
            }
            case "3" -> { // READ ALL
                List<Compra> compras = Compra.readAll(conn);
                if (compras.isEmpty()) {
                    System.out.println("Nenhuma compra cadastrada no momento.");
                } else {
                    System.out.println("\n--- COMPRAS CADASTRADAS ---");
                    compras.forEach(f -> System.out.println(f));
                }
            }
            case "4" -> { // DELETE
                System.out.print("CPF do Cliente para deletar a Compra: "); String cpf = scanner.nextLine();
                System.out.print("ID do Ingresso para deletar a Compra: "); int idIngresso = Integer.parseInt(scanner.nextLine());
                int linhasAfetadas = Compra.delete(conn, cpf, idIngresso); // usa o retorno da função DELETE para saber se a compra foi ou não apagada

                if (linhasAfetadas > 0) {
                    System.out.println("Compra apagada com sucesso!");
                } else {
                    System.out.println("Erro: Compra não encontrada ou não apagada.");
                }
            }
            case "0" -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    @Override
    public String toString() {
        return "Compra{" +
                "CPF do cliente=" + clienteCPF +
                ", ID do ingresso=" + ingressoId +
                '}';
    }
}
