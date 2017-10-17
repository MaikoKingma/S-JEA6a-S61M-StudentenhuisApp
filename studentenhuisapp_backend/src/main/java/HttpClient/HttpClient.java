package HttpClient;

import java.io.*;
import java.net.*;
import java.util.Map;

/**
 * Created by Maiko on 17/10/2017.
 */
public class HttpClient {

    private String sendGet(String url, Map<String, String> headers) throws Exception {

        //Create request
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        for (String header : headers.keySet()) {
            con.setRequestProperty(header, headers.get(header));
        }

        //Read response
        //int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
