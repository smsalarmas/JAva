package javaapplication1;
import Java.io.FileNotFoundException;
import Java.io.FileReader;
import Java.io.IOException;
import Java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JavaApplication1 {
    
        JSONParser parser = new JSONParser();

        try {     
            Object obj = parser.parse(new FileReader("C://Users//USUARIO//Documents//VS code//JavaScanFile//config//config.json");

            JSONObject jsonObject =  (JSONObject) obj;

            String name = (String) jsonObject.get("name");
            System.out.println(name);

            String city = (String) jsonObject.get("city");
            System.out.println(city);

            String job = (String) jsonObject.get("job");
            System.out.println(job);

            // loop array
            JSONArray ubicacion = (JSONArray) jsonObject.get("ubicacion");
            Iterator<String> iterator = ubicacion.iterator();
            while (iterator.hasNext()) {
             System.out.println(iterator.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}