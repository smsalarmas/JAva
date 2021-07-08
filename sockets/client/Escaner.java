package sockets.client;
import java.sql.SQLException;

public class Escaner {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Cliente cliente = new Cliente();
        cliente.runClient();
    }
}
