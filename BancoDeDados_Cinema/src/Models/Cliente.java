package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Cinema{
    private String CPF;
    private String nome;
    private String email;
    private String telefone;

    public Cliente(String CPF, String nome, String email, String telefone) {
        this.CPF = CPF;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    // CREATE
    public static void create(Connection conn, Cliente c) throws SQLException {
        String sql = "INSERT INTO Cliente (CPF, Nome, Email, Telefone) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.CPF);
            stmt.setString(2, c.nome);
            stmt.setString(3, c.email);
            stmt.setString(4, c.telefone);
            stmt.executeUpdate();
        }
    }

    // READ
    public static Cliente read(Connection conn, String cpf) throws SQLException {
        String sql = "SELECT * FROM Cliente WHERE CPF = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getString("CPF"),
                        rs.getString("Nome"),
                        rs.getString("Email"),
                        rs.getString("Telefone")
                );
            }
        }
        return null;
    }

    // READ ALL
    public static List<Cliente> readAll(Connection conn) throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getString("CPF"),
                        rs.getString("Nome"),
                        rs.getString("Email"),
                        rs.getString("Telefone")
                ));
            }
        }
        return clientes;
    }

    // DELETE
    public static int delete(Connection conn, String cpf) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE CPF = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            return stmt.executeUpdate();
        }
    }

    public static void menuCliente() throws Exception {
        System.out.println("1 - Adicionar | 2 - Buscar por CPF | 3 - Listar Todos | 4 - Deletar | 0 - Voltar");
        System.out.print("Opção: ");
        String op = scanner.nextLine();

        switch (op) {
            case "1" -> { // CREATE
                System.out.print("CPF: "); String cpf = scanner.nextLine();
                System.out.print("Nome: "); String nome = scanner.nextLine();
                System.out.print("Email: "); String email = scanner.nextLine();
                System.out.print("Telefone: "); String telefone = scanner.nextLine();
                Cliente.create(conn, new Cliente(cpf, nome, email, telefone));
                System.out.println("Cliente criado com sucesso!");
            }
            case "2" -> { // READ
                System.out.print("CPF do Cliente a buscar: "); String cpf = scanner.nextLine();
                Cliente c = Cliente.read(conn, cpf);
                System.out.println(c != null ? "Encontrado: " + c : "Não encontrado.");
            }
            case "3" -> { // READ ALL
                List<Cliente> clientes = Cliente.readAll(conn);
                if (clientes.isEmpty()) {
                    System.out.println("Nenhum cliente cadastrado no momento.");
                } else {
                    System.out.println("\n--- CLIENTES CADASTRADOS ---");
                    clientes.forEach(f -> System.out.println(f));
                }
            }
            case "4" -> { // DELETE
                System.out.print("CPF do Cliente a deletar: "); String cpf = scanner.nextLine();
                int linhasAfetadas = Cliente.delete(conn, cpf); // usa o retorno da função DELETE para saber se o cliente foi ou não apagado

                if (linhasAfetadas > 0) {
                    System.out.println("Cliente '" + cpf + "' apagado com sucesso!");
                } else {
                    System.out.println("Erro: Cliente '" + cpf + "' não encontrado ou não apagado.");
                }
            }
            case "0" -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "CPF=" + CPF +
                ", Nome=" + nome +
                ", Email='" + email +
                ", Telefone='" + telefone +
                '}';
    }
}
