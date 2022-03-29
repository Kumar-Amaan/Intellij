package resources;

import io.restassured.path.json.*;

import java.io.*;
import java.util.*;

public class Utils {
    public static JsonPath rawToJson(String resp) {
        JsonPath js1=new JsonPath(resp);
        return js1;
    }
    public static String getGlobalValue(String key) throws IOException
    {
        Properties prop =new Properties();
        FileInputStream fis =new FileInputStream("C:\\Users\\Aman.1.Kumar\\Documents\\JiraAPI_Intellij\\global.properties");
        prop.load(fis);
        return prop.getProperty(key);



    }
}
