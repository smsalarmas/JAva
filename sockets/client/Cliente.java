package sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

public class Cliente {

    private final static int PORT = 5051;

    private final static String SERVER = "localhost";
    
    public void runClient() throws ClassNotFoundException, SQLException {
        boolean decision = true;
    	boolean exit=false;//variable para controlar ciclo del programa
        Socket socket;//Socket para la comunicacion cliente servidor        
        try {            
            System.out.println("CLIENT --> Iniciando...");  
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
                            System.out.println("CLIENT --> ESCRIBA SU INSERT");  
                            //captura instrucción escrita por el usuario
                            String request = brRequest.readLine(); 
                            //manda peticion al servidor
                            output.println(request);
                            //captura respuesta e imprime
                            while(true) {
                                String st = input.readLine();
                                if(st == null) {
                                    decision = false;
                                    exit = true;
                                    break;
                                }
                                System.out.println("SERVER --> " + st );     
                            }

                            if(request.equals("exit")){//terminar aplicacion
                                exit=true;                  
                                System.out.println("CLIENT --> Fin de programa");    
                            } 
                        
                            break;
                        //SI EL CLIENTE SELECCIONA EL SELECT
                        case 2: //SELECT
                            output.write(seleccion); 
                            //Limpio el Buffer
                            output.flush();
                            System.out.println("CLIENT --> ESCRIBA SU SELECT");  
                            //captura instrucción escrita por el usuario
                            request = brRequest.readLine(); 
                            //manda peticion al servidor
                            output.println(request);
                            //captura respuesta e imprime
                            while(true) {
                                String st = input.readLine();
                                if(st == null) {
                                    decision = false;
                                    exit = true;
                                    break;
                                }
                                System.out.println("SERVER --> " + st );     
                            }

                            if(request.equals("exit")){//terminar aplicacion
                                exit=true;                  
                                System.out.println("CLIENT --> Fin de programa");    
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