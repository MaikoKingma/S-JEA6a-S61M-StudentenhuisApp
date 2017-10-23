package Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;

import javax.ejb.Stateless;
import java.io.*;
import java.net.URI;
import java.util.*;

/**
 * Created by Maiko on 17/10/2017.
 */
@Stateless
public class OAuthService {

    //All endpoints we want to use
    private final List<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/plus.login");
    //Where user access tokens should be stored
    private MemoryDataStoreFactory dataStoreFactory;
    //basically does everything for you concerning oAuth2
    private GoogleAuthorizationCodeFlow authorizationCodeFlow;
    private List<String> redirectUris;

    /**
     * Constructor used by UnitTests to mock the GoogleAuthorizationCodeFlow
     */
    public OAuthService(GoogleAuthorizationCodeFlow authorizationCodeFlow, List<String> redirectUris) {
        this.authorizationCodeFlow = authorizationCodeFlow;
        this.redirectUris = redirectUris;
    }

    public OAuthService() {
        //ToDo Store in database
        dataStoreFactory = new MemoryDataStoreFactory();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        //Load oAuth properties
        try(InputStreamReader stream = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("/client_secrets.json"))) {
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, stream);
            redirectUris = clientSecrets.getDetails().getRedirectUris();

            //Create AuthorizationFlow
            authorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), jsonFactory, clientSecrets, SCOPES)
                    .setDataStoreFactory(dataStoreFactory)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public URI getAuthorizationUri() {
        GoogleAuthorizationCodeRequestUrl url = authorizationCodeFlow.newAuthorizationUrl();
        url.setRedirectUri(redirectUris.get(0));
        return url.toURI();
    }

    public Credential getCredentials(String userId) throws IOException {
        return authorizationCodeFlow.loadCredential(userId);
    }

    public GoogleTokenResponse getAccessToken(String authorizationCode) throws IOException {
        return authorizationCodeFlow
                .newTokenRequest(authorizationCode)
                .setRedirectUri(redirectUris.get(0))
                .execute();
    }

    public Credential storeCredentials(GoogleTokenResponse response, String userId) throws  IOException {
        return authorizationCodeFlow.createAndStoreCredential(response, userId);
    }
}
