import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.print.DocFlavor.STRING;

import com.mysql.cj.util.DnsSrv.SrvRecord;
import com.mysql.cj.xdevapi.JsonArray;
import com.mysql.cj.xdevapi.JsonParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.graalvm.compiler.core.common.Fields.ObjectTransformer;
import org.graalvm.compiler.nodes.java.NewArrayNode;
import org.jsom.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;


public class config {
    
    public static void main (String[] args){

        /* for(int i=0;i<N;i++){
        System.out,println(escaner[i]+" ");
        }*/

       /*  System.out.println(".");
            Scanner entrada = new Scanner(System.in);
            String nombre, ubicacion, extension;
            System.out.println("ingrese nombre del archivo: ");
            nombre = entrada.nextLine();
            System.out.println("ingrese nombre de la ubicacion: ");
            ubicacion = entrada.nextLine();
            System.out.println("ingrese nombre de la extension: ");
            extension = entrada.nextLine(); */


            guardar();
            leer();

    }

    private static void guardar(){

        JSONParser  JSONParser = new JSONParser();

        try (FileReader reader = new FileReader("config.json")){
            Object obj  = JsonParser.parse(reader);

            JSONArray pclista = (JSONArray) obj;
            System.out.println("El archivo abtiene el siguente JSON:  ");
            System.out.println(pclista);

            for (Object pc: pclista){
                mostrarinformacionpc((JSONObject) pc  );
                
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ParseException e){
            e.printStackTrace();
        }



    }

    private static void mostrarinformacionpc(org.json.simple.JSONObject pc) {
    }

    private static void leer(){

        JSONObject pc1 = New JSONObject();
        pc1.put("ubucacion", "C://Users//USUARIO//Documents//VS code//JavaScanFile ");
        pc1.put("extension", ".txt");
        pc1.put("nombre", "readme" );


        JSONObject datospc = New JSONObject();
        datospc.put("pc", pc1);

        JsonArray listapc = new JsonArray();
        listapc.add(datospc);

        try(FileWriter file = new FileWriter("config.json")) {
            file.wait(listapc.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
            
        }
        
        



        
    }

    private static void (JSONObject jsonobject) {
        JSONObject pc = (JSONObject) jsonobject.get(pc);

        System.out.println("PC's EN EL JSON: ");
        String ubucacion  (String) pc.get("ubucacion");
        System.out.println("ubucacion:  " + ubucacion);

        String extension  (String) pc.get("extension");
        System.out.println("extension:  " + extension);

        String nombre  (String) pc.get("nombre");
        System.out.println("nombre:  " + nombre);
        






    }
}
