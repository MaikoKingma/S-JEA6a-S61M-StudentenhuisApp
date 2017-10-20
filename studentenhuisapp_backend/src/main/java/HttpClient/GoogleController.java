package HttpClient;

import DataTransferObject.GoogleUserInfo;
import com.google.gson.Gson;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Maiko on 20/10/2017.
 */
@Stateless
public class GoogleController {

    @Inject
    private HttpClient httpClient;

    private final String USERINFO_URL = "https://www.googleapis.com/userinfo/v2/me";

    public GoogleController() { }

    public GoogleUserInfo getUserInfo(String authorizationHeader) {
        try {
            //Send request to Google
            URL url = new URL(USERINFO_URL);
            Map<String, String> headers = new HashMap<>(1);
            headers.put("Authorization", authorizationHeader);

            //Convert json to returntype
            return new Gson().fromJson(httpClient.sendGet(url, headers), GoogleUserInfo.class);

        } catch (IOException e) {
            throw new NullPointerException(e.getMessage());
        }
    }
}
