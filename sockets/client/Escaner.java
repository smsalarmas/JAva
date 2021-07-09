package sockets.client;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;


import org.json.simple.*;

public class Escaner {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, ParseException, org.json.simple.parser.ParseException {
        Cliente cliente = new Cliente(); 

        //First Employee
        JSONArray listaArchivos = new JSONArray();
        int contador = 0;
        try {
            //Se almacenan en un arreglo ya que pueden ser varios archivos
            File folder = new File(System.getProperty("user.dir"));
            if(folder.exists()){
                File[] files = folder.listFiles();
                for (File f : files) {
                    
                    System.out.println("------------------------------ REGISTRO NUMERO " + contador + " ------------------------------");
                    
                    String extension = "";
                    int i = f.getName().lastIndexOf('.');
                    int p = Math.max(f.getName().lastIndexOf('/'), f.getName().lastIndexOf('\\'));

                    if (i > p) {
                        extension = f.getName().substring(i+1);
                    }
                    
                    if(!f.isDirectory()){
                        // Peso del archivo (se almacena como kilobytes)
                        long bytes = f.length();
                        long kilobytes = (bytes/1024);
                        String nombre = f.getName();
                        String ubicacion = f.getCanonicalPath();
                        System.out.println("Nombre del Archivo ----> " + nombre);
                        System.out.println("Ubicacion ----> " + ubicacion);
                        System.out.println("Extension ----> " + "."+extension);    
                        System.out.println("Peso ----> " + kilobytes + " KB\n");

                        JSONObject propiedades = new JSONObject();
                        propiedades.put("nombre", nombre);
                        propiedades.put("ubicacion", ubicacion);
                        propiedades.put("extension", extension);
                        propiedades.put("peso", kilobytes);

                        JSONObject archivo = new JSONObject();

                        //archivo.put(propiedades);

                        listaArchivos.add(propiedades);
                        contador++;
                    } else {
                        String nombreCarpeta = f.getName();
                        String ubicacionCarpeta = f.getCanonicalPath();
                        long peso = sizeCarpeta(f);

                        System.out.println("Nombre de la Carpeta ----> " + nombreCarpeta);
                        System.out.println("Ubicacion ----> " + ubicacionCarpeta);
                        System.out.println("Extension ----> Carpeta" );
                        System.out.println("Peso ----> " + peso + " KB\n");

                        JSONObject propiedades = new JSONObject();
                        propiedades.put("nombre", nombreCarpeta);
                        propiedades.put("ubicacion", ubicacionCarpeta);
                        propiedades.put("extension", "Carpeta");
                        propiedades.put("peso", peso);

                        JSONObject archivo = new JSONObject();

                        //archivo.put(propiedades);

                        listaArchivos.add(propiedades);
                        contador++;
                    }
                }
            } else {
                System.out.println("La ruta no existe");
            }   
        } catch(Error e){
            System.out.println("Ruta no encontrada");
        }

        cliente.runClient(listaArchivos, contador);
    }
    //Método para Obtener el peso en caso de ser (Recursividad al obtener el tamaño de subdirectorios y archivos dentro de la carpeta evaluada)
    public static long sizeCarpeta(File directory) {
        long tamano = 0;
        
        for (File file : directory.listFiles()) {
            if(file != null){
                if (file.isFile())
                    tamano += file.length();
                else
                    tamano += sizeCarpeta(file);
            }
        }

        long kb = (tamano / 1024);

        return kb;
    }
}