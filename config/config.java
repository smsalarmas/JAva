import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Config
{    
    public static void main(String[] args)
    {
 
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("config/config.json"))
        
        {
           
            Object obj = jsonParser.parse(reader);
           
            JSONArray configList = new JSONArray();

            configList.add(obj);            
           
            JSONObject employeeObject = (JSONObject) configList.get(0);

            Object temp = employeeObject.get("configuracion"); //Aqui accedes a config.json ("configuracion" antes "nombreJson")

            JSONObject jsonResult = (JSONObject) temp;
            //System.out.println(jsonResult.size());

            System.out.println("_____________________________________________________________");
            

         
            JSONArray extensiones = (JSONArray) jsonResult.get("extensiones"); //Aqui accedes al arreglo extensiones

            ArrayList<String> Extensions = new ArrayList<String>();

            for(int i = 0; i < extensiones.size(); i++){
                String tempVar = (String) extensiones.get(i);
                Extensions.add(tempVar);
            }

            System.out.println(Extensions);

            System.out.println("_____________________________________________________________");
            


            JSONArray carpetas = (JSONArray) jsonResult.get("carpetas"); //Aqui accedes al arreglo carpetas

            ArrayList<String> Fold = new ArrayList<String>();

            for(int i = 0; i < carpetas.size(); i++){
                String tempVar1 = (String) carpetas.get(i);
                Fold.add(tempVar1);
            }

            System.out.println(Fold);

            System.out.println("_____________________________________________________________");
            



            JSONArray archivos = (JSONArray) jsonResult.get("archivos"); //Aqui accedes al arreglo archivos

            ArrayList<String> File = new ArrayList<String>();

            for(int i = 0; i < archivos.size(); i++){
                String tempVar2 = (String) archivos.get(i);
                File.add(tempVar2);
            }

            System.out.println(File);

            System.out.println("_____________________________________________________________");


            JSONArray ubicacion = (JSONArray) jsonResult.get("ubicacion"); //Aqui accedes al arreglo ubicacion

            ArrayList<String> Loc = new ArrayList<String>();

            for(int i = 0; i < ubicacion.size(); i++){
                String tempVar3 = (String) ubicacion.get(i);
                Loc.add(tempVar3);
            }

            System.out.println(Loc);

            System.out.println("_____________________________________________________________");







            

 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

     //"ubicacion":["C://Users//USUARIO//Documents//VS code//JavaScanFile","extension":".txt","nombre":"readme"]

}