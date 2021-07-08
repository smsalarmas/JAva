package sockets.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Server {

    /**
     * Puerto 
     */
    private final static int PORT = 5051;
    
    /**
     * @param args the command line arguments
     * @throws SQLException
     * @throws ClassNotFoundExceptionJava
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            ResultSet resultSet;
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/inventario?user=javaTest&password=123456&zeroDateTimeBehavior=convertToNull");
            Statement statement = connection.createStatement();
            //Socket de servidor para esperar peticiones de la red
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("SERVER --> Servidor iniciado");    
            System.out.println("SERVER --> En espera de cliente...");    
            //Socket de cliente
            Socket clientSocket;
            while(true){
                 
                //en espera de conexion, si existe la acepta
                clientSocket = serverSocket.accept();
                //Para leer lo que envie el cliente
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //para imprimir datos de salida                
                PrintStream output = new PrintStream(clientSocket.getOutputStream());
                //se lee la decision del cliente
                int decision  = input.read();                
                switch (decision) {
                    case 1: //INSERT
                    //se lee peticion del cliente
                    String request = input.readLine();
                    System.out.println("CLIENT --> QUERY: [" + request +  "]");
                    PreparedStatement preparedStatement = connection.prepareStatement(request);
                    int countResp = preparedStatement.executeUpdate();
                    if(countResp > 0){
                        output.flush();
                        //Se envía al cliente
                        output.println("Registro creado satisfactoriamente.");
                        //Log para el server
                        System.out.println("Registro creado satisfactoriamente.");
                    } else {
                        output.flush();
                        //Se envía al cliente
                        output.println("Ha ocurrido un error al realizar el query.");
                        //Log para el server
                        System.out.println("Ha ocurrido un error al realizar el query.");
                    } 
                        //cierra conexion
                        clientSocket.close();
                        break;
                    case 2: //SELECT
                    //se lee peticion del cliente
                    request = input.readLine();
                    System.out.println("CLIENT --> QUERY: [" + request +  "]");
                    resultSet = statement.executeQuery(request);  

                    System.out.println("\nEjecutando...");
                    System.out.println("SERVER --> Resultado de la petición.");                    
                    String strOutput = "\t" + "id" + "\t" + "Nombre" + "\t\t" + "Fecha Creacion" + "\t\t" + "Fecha Modificacion" + "\t" + "Extension" + "\t" + "Ubicacion" + "\t\t\t" + "Peso" + "\n";
                    output.flush();
                    output.println("Resultado de la petición.");
                    output.flush();
                    output.println(strOutput);  

                    System.out.println("SERVER -->" + "\tid" + "\t" + "Nombre" + "\t\t" + "Fecha Creacion" + "\t\t" + "Fecha Modificacion" + "\t" + "Extension" + "\t" + "Ubicacion" + "\t\t\t" + "Peso" + "\n");
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String filename = resultSet.getString("nombre");
                        Timestamp created_at = resultSet.getTimestamp("created_at");
                        Timestamp updated_at = resultSet.getTimestamp("updated_at");            
                        String extension = resultSet.getString("extension");
                        String ubicacion = resultSet.getString("ubicacion");
                        Double peso = resultSet.getDouble("size");
                        //se procesa la peticion y se espera resultado
                        String queryResponse = "\t" + id + "\t" + filename + "\t" + created_at + "\t" + updated_at + "\t\t" + extension + "\t" + ubicacion + "\t" + peso;                
                        //Se imprime en consola "servidor"                
                        System.out.println("SERVER -->" + queryResponse);
                        //se imprime en cliente
                        output.flush();//vacia contenido
                        output.println(queryResponse);              
                    }
                        //cierra conexion
                        clientSocket.close();
                        break;
                    default:
                        output.flush();
                        output.println("Se debe seleccionar una opcion correcta 1 o 2.");
                        break;
                }
            }    
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}