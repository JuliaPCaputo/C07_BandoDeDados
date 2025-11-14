import DataBase.ConnectionFactory;
import Models.*; // Para importar todas as classes do package Models
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import java.sql.Date;

public class Main {
    private static Scanner scanner;
    private static Connection conn;

    public static void main(String[] args) {
        try {
            conn = ConnectionFactory.getConnection();
            scanner = new Scanner(System.in);

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
                    case "1" -> menuFilme();
                    case "2" -> menuSala();
                    case "3" -> menuCliente();
                    case "4" -> menuSessao();
                    case "5" -> menuIngresso();
                    case "6" -> menuCompra();
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

    // --- CHAMANDO AS FUNÇÕES ---

    private static void menuFilme() throws Exception {
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
                filmes.forEach(f -> System.out.println(f));
            }
            case "4" -> { // DELETE
                System.out.print("Título do Filme a deletar: "); String titulo = scanner.nextLine();
                Filme.delete(conn, titulo);
                System.out.println("Filme deletado.");
            }
            case "0" -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void menuSala() throws Exception {
        System.out.println("1 - Adicionar | 2 - Buscar por ID | 3 - Listar Todos | 4 - Deletar | 0 - Voltar");
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
                salas.forEach(s -> System.out.println(s));
            }
            case "4" -> { // DELETE
                System.out.print("ID da Sala a deletar: "); int idSala = Integer.parseInt(scanner.nextLine());
                Sala.delete(conn, idSala);
                System.out.println("Sala deletada.");
            }
            case "0" -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void menuCliente() throws Exception {
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
                clientes.forEach(c -> System.out.println(c));
            }
            case "4" -> { // DELETE
                System.out.print("CPF do Cliente a deletar: "); String cpf = scanner.nextLine();
                Cliente.delete(conn, cpf);
                System.out.println("Cliente deletado.");
            }
            case "0" -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void menuSessao() throws Exception {
        System.out.println("1 - Adicionar | 2 - Buscar por Número | 3 - Listar Todos | 4 - Deletar | 0 - Voltar");
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
                sessoes.forEach(s -> System.out.println(s));
            }
            case "4" -> { // DELETE
                System.out.print("Número da Sessão a deletar: "); int num = Integer.parseInt(scanner.nextLine());
                Sessao.delete(conn, num);
                System.out.println("Sessão deletada.");
            }
            case "0" -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void menuIngresso() throws Exception {
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
                    // Se o seu ID for inserido manualmente (e não auto-incrementado)
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
                ingressos.forEach(i -> System.out.println(i));
            }
            case "4" -> { // DELETE
                System.out.print("ID do Ingresso a deletar: "); int id = Integer.parseInt(scanner.nextLine());
                Ingresso.delete(conn, id);
                System.out.println("Ingresso deletado.");
            }
            case "0" -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void menuCompra() throws Exception {
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
                compras.forEach(c -> System.out.println(c));
            }
            case "4" -> { // DELETE
                System.out.print("CPF do Cliente para deletar a Compra: "); String cpf = scanner.nextLine();
                System.out.print("ID do Ingresso para deletar a Compra: "); int idIngresso = Integer.parseInt(scanner.nextLine());
                Compra.delete(conn, cpf, idIngresso);
                System.out.println("Compra deletada.");
            }
            case "0" -> {}
            default -> System.out.println("Opção inválida.");
        }
    }
}