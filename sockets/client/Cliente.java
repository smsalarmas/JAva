package sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import io.github.cdimascio.dotenv.Dotenv;

import sockets.server.Server;

public class Cliente {

    private final static int PORT = 5051;

    private final static String SERVER = "localhost";
    
    public void runClient(JSONArray data, int totalRows) throws ClassNotFoundException, SQLException, ParseException {
        boolean decision = true;
    	boolean exit=false;//variable para controlar ciclo del programa
        Socket socket;//Socket para la comunicacion cliente servidor        

        try {            
            Dotenv dotenv = Dotenv.load();
            JSONParser parser = new JSONParser();
            String datos = data.toString();

            Object obj = parser.parse(datos);
            JSONArray arrTemp = (JSONArray)obj;  

            System.out.println("CLIENT --> Iniciando...");  
            /*------------------------------------CLIENTE INICIADO--------------------------------------*/ 
            Scanner escaner = new Scanner(System.in);

            while( !exit ){//ciclo repetitivo                                
                socket = new Socket(SERVER, PORT);//abre socket                
                //Para leer lo que envie el servidor      
                BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));                
                //para imprimir datos del servidor
                PrintStream output = new PrintStream(socket.getOutputStream());                
                //Para leer lo que escriba el usuario            
                BufferedReader brRequest = new BufferedReader(new InputStreamReader(System.in));            

                while(decision){
                    System.out.println("CLIENT --> QUE INSTRUCCIÓN DESEA EJECUTAR");
                    System.out.println("CLIENT --> 1. INSERT \t 2.SELECT");
                    int seleccion = escaner.nextInt();

                    switch (seleccion) {
                        case 1: //INSERT    
                            output.write(seleccion);  
                            //Limpio el Buffer
                            output.flush();
                            System.out.println("CLIENT --> ENVIANDO QUERY AL SERVIDOR...");  
                            
                            for(int i = 0; i < totalRows; i++){
                                
                                JSONObject jsonObject = (JSONObject) arrTemp.get(i);
                                //Asignacion de Variables
                                String nombre = (String) jsonObject.get("nombre");
                                String extension = (String) jsonObject.get("extension");
                                String ubicacion = (String) jsonObject.get("ubicacion");
                                long peso = (Long) jsonObject.get("peso");
                                //-----------------------------CREACIÓN DEL QUERY----------------------------//
                                String request = "INSERT INTO " + dotenv.get("DB_NOMBRE") + "." + dotenv.get("DB_TABLA") + "(nombre,extension,ubicacion,size) VALUES("+ "'" + nombre + "'" +","+ "'" +extension+ "'" + ","+ "'" + ubicacion + "'" + ","+peso+")"; 
                                System.out.println(request);
                                //----------------------------MANDA PETICIÓN AL SERVIDOR-----------------------//
                                output.println(request);
                                
                                //-----------------------------RESPUESTA DEL SERVIDOR----------------------------//
                                String st = input.readLine();

                                if(st != null){
                                    System.out.println("SERVER --> " + st );     
                                }

                                if(i == totalRows){
                                    break;
                                }
                            }
                        
                            break;
                        //SI EL CLIENTE SELECCIONA EL SELECT
                        case 2: //SELECT
                            output.write(seleccion); 
                            //Limpio el Buffer
                            output.flush();
                            System.out.println("CLIENT --> ENVIANDO QUERY AL SERVIDOR");  
                            //----------------------ENVIANDO EL SELECT POR DEFAULT---------------------//
                            String request = "SELECT * from "+dotenv.get("DB_NOMBRE")+"."+dotenv.get("DB_TABLA");
                            //----------------------MANDANDO PETICIÓN AL SERVIDOR---------------------//
                            output.println(request);
                            //-------------------------CAPTURA RESPUESTA E IMPRIME--------------------//
                            while(true) {
                                String st = input.readLine();
                                if(st == null) {
                                    decision = false;
                                    exit = true;
                                    break;
                                }
                                System.out.println("SERVER --> " + st );     
                            }

                            if(socket.isClosed()){
                                decision = false;
                                exit = true;
                            }
                            break;
                        default:
                            System.out.println("Seleccione una opción correcta 1 o 2");
                            break;
                    }
                    socket.close(); 
                }
            }//end while                                    
       } catch (IOException ex) {        
         System.err.println("CLIENT --> " + ex.getMessage());   
       }
    }
    
}