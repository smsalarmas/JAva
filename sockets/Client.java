package sockets;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

import sockets.conexion.Conexion;

public class Client extends Conexion
{
    public Client() throws IOException{super("cliente");} //Se usa el constructor para cliente de Conexion

    public void startClient() //Método para iniciar el cliente
    {
        try
        {
            Scanner escaner = new Scanner(System.in);
            //Flujo de datos hacia el servidor
            salidaServidor = new DataOutputStream(clientSocket.getOutputStream());

            while(true){
                System.out.println("¿Qué estás pensando?, si deseas salir escribe 'exit'");
                String word = escaner.nextLine();
                
                if(word.equalsIgnoreCase("exit")){
                    clientSocket.close(); //Fin de la conexión
                    escaner.close();
                } else {
                    salidaServidor.writeUTF(word);
                }
            }
            

            //Se enviarán dos mensajes
            /*for (int i = 0; i < 2; i++)
            {
                //Se escribe en el servidor usando su flujo de datos
                salidaServidor.writeUTF("Este es el mensaje número " + (i+1) + "\n");
            }*/

            

            //clientSocket.close();//Fin de la conexión

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}