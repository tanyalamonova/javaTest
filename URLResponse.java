import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;

public class URLResponse {
    public static void main(String []args) throws Exception {


        //an example of getting a status
        String URLString = "https://vk.com/";
        System.out.println(getResponseCode(URLString));
    }

    //the method that gets a website's status
    public static int getResponseCode(String urlString) throws Exception {
        URL url = new URL(urlString); 
        HttpURLConnection huc =  (HttpURLConnection)url.openConnection(); 
        huc.setRequestMethod("GET"); 
        huc.connect(); 
        return huc.getResponseCode();
    }
}