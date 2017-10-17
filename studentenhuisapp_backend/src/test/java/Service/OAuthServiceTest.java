package Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

/**
 * Created by Maiko on 17/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class OAuthServiceTest {

    private final List<String> redirectUris = Arrays.asList("http://localhost:8080/studentenhuisapp/");

    @Mock
    private GoogleAuthorizationCodeFlow authorizationCodeFlow;

    @InjectMocks
    private OAuthService oAuthService = new OAuthService(authorizationCodeFlow, redirectUris);

    @Test
    public void getAuthorizationUriTest() throws Exception {
        GoogleAuthorizationCodeRequestUrl url = new GoogleAuthorizationCodeRequestUrl(
                "https://accounts.google.com/o/oauth2/auth",
                "ClientId",
                "",
                Arrays.asList("https://www.googleapis.com/auth/plus.login")
        );
        Mockito.when(authorizationCodeFlow.newAuthorizationUrl())
                .thenReturn(url);
        String uri = oAuthService.getAuthorizationUri().toString();
        uri = uri.split("&redirect_uri=")[1];
        //Check if redirectUri is set
        Assert.assertTrue(uri.startsWith(redirectUris.get(0)));
    }
}
