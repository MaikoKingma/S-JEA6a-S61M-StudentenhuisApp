package HttpClient;

import javax.ejb.Stateless;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created by Maiko on 17/10/2017.
 */
@Stateless
public class HttpClient {

    public HttpClient() { }

    public String sendGet(URL url) throws IOException {
        Map<String, String> headers = new HashMap<>();
        return sendGet(url, headers);
    }

    public String sendGet(URL url, Map<String, String> headers) throws IOException {

        //Create request
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
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
