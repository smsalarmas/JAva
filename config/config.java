import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Config{  
    
    public static void main(String[] args) 
    
    {
        //JSON parser object para convertir a objeto
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("config/config.json"))
        {
            //Leer JSON
            Object obj = jsonParser.parse(reader);
            //Crear array de json (EXTENSIONES, CARPETAS Y ARCHIVOS SON ARRAYS)
            JSONArray configList = new JSONArray();

            //Se agrega OBJ que es un JSONParser al JSONARRAY
            configList.add(obj);            
            //Se crea nuevo JSONObject para acceder a la propiedad 0 del JSON ("configuracion")
            JSONObject employeeObject = (JSONObject) configList.get(0);

            Object temp = employeeObject.get("configuracion");

            JSONObject jsonResult = (JSONObject) temp;
            System.out.println(jsonResult.size());

            //Get employee object within list
            JSONArray extensiones = (JSONArray) jsonResult.get("extensiones");

            ArrayList<String> Extensions = new ArrayList<String>();

            for(int i = 0; i < extensiones.size(); i++){
                String tempVar = (String) extensiones.get(i);
                Extensions.add(tempVar);
            }

            System.out.println(Extensions);

            //setExtensiones(jsonResult.get("extensiones"));
         
 
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