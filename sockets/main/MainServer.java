package sockets.main;

import java.io.IOException;
import sockets.server.Server;
//import sockets.client.Client;


//Clase principal que hará uso del servidor
public class MainServer
{
    public static void main(String[] args) throws IOException
    {
        Server serv = new Server(); //Se crea el servidor
        //Client cli = new Client(); //Init client
        serv.startServer(); //Se inicia el servidor
        //cli.startClient();cle
    }
}