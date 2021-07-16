import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Config
{  
    public static void main(String[]args) throws IOException,ParseException,NullPointerException{
        Config config = new Config();
       
            System.out.println(config.getArray("extensiones"));
            System.out.println(config.getArray("carpetas"));
            System.out.println(config.getArray("archivos"));
            System.out.println(config.getArray("ubicacion"));
            System.out.println("_____________________________________________________________");

       
    }
   
    public ArrayList<String> getArray(String arrayName) throws IOException, ParseException,NullPointerException
    {
        //JSON parser object para convertir a objeto
        JSONParser jsonParser = new JSONParser();

        FileReader reader = new FileReader("config/config.json");

            Object obj = jsonParser.parse(reader);
           
            JSONArray configList = new JSONArray();
            configList.add(obj);            
            JSONObject employeeObject = (JSONObject) configList.get(0);

            Object temp = employeeObject.get("configuracion");

            JSONObject jsonResult = (JSONObject) temp;

            //Get employee object within list
            JSONArray extensiones = (JSONArray) jsonResult.get(arrayName);

            ArrayList<String> extensionsArray = new ArrayList<String>();

            for(int i = 0; i < extensiones.size(); i++){
                String tempVar = (String) extensiones.get(i);
                extensionsArray.add(tempVar);
                
            }

            System.out.println("_____________________________________________________________");

        return extensionsArray;
    }
}