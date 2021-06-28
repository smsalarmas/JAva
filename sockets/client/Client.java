package sockets.client;

import java.io.DataOutputStream;
import java.io.IOException;

import sockets.conexion.Conexion;

public class Client extends Conexion
{
    public Client() throws IOException{super("cliente");} //Se usa el constructor para cliente de Conexion

    public void startClient(String word) //Método para iniciar el cliente
    {
        try
        {
            //Flujo de datos hacia el servidor
            salidaServidor = new DataOutputStream(clientSocket.getOutputStream());

            while(true){
                if(word.equalsIgnoreCase("exit")){
                    clientSocket.close(); //Fin de la conexión
                } else {
                    salidaServidor.writeUTF(word);
                    continue;
                }
            }
            
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}