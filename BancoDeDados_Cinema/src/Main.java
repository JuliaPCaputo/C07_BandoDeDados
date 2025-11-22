import DataBase.ConnectionFactory;
import Models.*; // Para importar todas as classes do package Models
import java.sql.Connection;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;
    private static Connection conn;

    public static void main(String[] args) {
        try {
            conn = ConnectionFactory.getConnection();
            scanner = new Scanner(System.in);
            Cinema.configurarDependencias(scanner, conn);

            System.out.println("Conexão com o banco de dados estabelecida!");

            exibirMenuPrincipal();

        } catch (Exception e) {
            System.err.println("Erro ao tentar conexão com o banco de dados: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (scanner != null) scanner.close();
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }

    // --- MÉTODOS DE MENU ---

    private static void exibirMenuPrincipal() {
        while (true) {
            System.out.println("\n--- MENU PRINCIPAL: ESCOLHA UMA OPÇÃO ---");
            System.out.println("1 - Gerenciar Filmes");
            System.out.println("2 - Gerenciar Salas");
            System.out.println("3 - Gerenciar Clientes");
            System.out.println("4 - Gerenciar Sessões");
            System.out.println("5 - Gerenciar Ingressos");
            System.out.println("6 - Gerenciar Compras");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            String op = scanner.nextLine();

            try {
                switch (op) {
                    case "1" -> Filme.menuFilme();
                    case "2" -> Sala.menuSala();
                    case "3" -> Cliente.menuCliente();
                    case "4" -> Sessao.menuSessao();
                    case "5" -> Ingresso.menuIngresso();
                    case "6" -> Compra.menuCompra();
                    case "0" -> {
                        System.out.println("Encerrando o sistema.");
                        return;
                    }
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.err.println("Ocorreu um erro na operação: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}