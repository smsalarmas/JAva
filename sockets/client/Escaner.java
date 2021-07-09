package sockets.client;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

//Se importa la libreria json.simple
import org.json.simple.*;

public class Escaner {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, ParseException, org.json.simple.parser.ParseException {
        Cliente cliente = new Cliente(); 

        //Se crea instancia listaArchivos es del Tipo JSONAarray
        JSONArray listaArchivos = new JSONArray();
        int contador = 0;
        try {
            //Se almacenan en un arreglo ya que pueden ser varios archivos
            //System.GetProperty("user.dir") Va a obtener el Directorio actual donde esta el usuario
            File folder = new File(System.getProperty("user.dir"));

            //Valida si el archivo carpeta Existe
            if(folder.exists()){

                //Se asigna a la variable files la lista de archivos que contiene la carpeta
                File[] files = folder.listFiles();
                for (File f : files) {
                    
                    //Se recorre cada archivo que exista dentro de la carpeta
                    System.out.println("------------------------------ REGISTRO NUMERO " + contador + " ------------------------------");
                    
                    //Se obtiene la extension del archivo
                    String extension = "";
                    int i = f.getName().lastIndexOf('.');
                    int p = Math.max(f.getName().lastIndexOf('/'), f.getName().lastIndexOf('\\'));

                    if (i > p) {
                        extension = f.getName().substring(i+1);
                    }
                    
                    //Se valida si es carpeta o un archivo
                    if(!f.isDirectory()){
                        // Peso del archivo (se almacena como kilobytes f.length(Obtiene el peso del archivo evaluado))
                        long bytes = f.length();
                        long kilobytes = (bytes/1024);

                        //f.GetName Obtiene el nombre del archivo
                        String nombre = f.getName();

                        //f.getCanonicalPath Obtiene la ubicacion del archivo
                        String ubicacion = f.getCanonicalPath();

                        //Se manda el Resultado a Consola
                        System.out.println("Nombre del Archivo ----> " + nombre);
                        System.out.println("Ubicacion ----> " + ubicacion);
                        System.out.println("Extension ----> " + "."+extension);    
                        System.out.println("Peso ----> " + kilobytes + " KB\n");
                        
                        //Se crea la instancia propiedades del objeto JSONObject
                        JSONObject propiedades = new JSONObject();

                        //El metodo put asigna un dato del tipo (key, valor)
                        propiedades.put("nombre", nombre);
                        propiedades.put("ubicacion", ubicacion);
                        propiedades.put("extension", extension);
                        propiedades.put("peso", kilobytes);

                         //Agregamos las propiedades actuales a listaArchivos que es un JSONArray
                        listaArchivos.add(propiedades);

                        //aumenta el contador
                        contador++;

                    } else {

                        //Se crean las variables en caso de que sea una Carpeta
                        String nombreCarpeta = f.getName();
                        String ubicacionCarpeta = f.getCanonicalPath();
                        long peso = sizeCarpeta(f);
                        long pesoCarpeta = (peso/1024);
                        System.out.println("Nombre de la Carpeta ----> " + nombreCarpeta);
                        System.out.println("Ubicacion ----> " + ubicacionCarpeta);
                        System.out.println("Extension ----> Carpeta" );
                        System.out.println("Peso ----> " + pesoCarpeta + " KB\n");

                        JSONObject propiedades = new JSONObject();
                        propiedades.put("nombre", nombreCarpeta);
                        propiedades.put("ubicacion", ubicacionCarpeta);
                        propiedades.put("extension", "Carpeta");
                        propiedades.put("peso", pesoCarpeta);

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

        //Se inicializa la variable tamano de tipo long
        long tamano = 0;
        
        for (File file : directory.listFiles()) {

            //Se recorre los archivos dentro de la carpeta recibida
            if(file != null){
                if (file.isFile()){
                    //Se utiliza el metodo length ya que es un archivo para obtener el peso
                    tamano += file.length();
                } else {
                    //Se utiliza la funcion en caso de haber mas carpetas
                    tamano += sizeCarpeta(file);
                }
                    
            }
        }


        return tamano;
    }
}