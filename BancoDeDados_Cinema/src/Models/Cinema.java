// Super classe

package Models;

import java.sql.Connection;
import java.util.Scanner;

public abstract class Cinema {
    String nome;
    protected static Scanner scanner;
    protected static Connection conn;

    public Cinema() {
        this.nome = nome;
    }

    // Esse método tem como função configurar as entradas de suas classes filhas, visto que esses parâmetros são, inicialmente, null, o que causa erro
    public static void configurarDependencias(Scanner s, Connection c) {
        Cinema.scanner = s;
        Cinema.conn = c;
    }
}
