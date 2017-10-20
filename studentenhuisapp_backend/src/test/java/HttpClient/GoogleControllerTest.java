package HttpClient;

import DataTransferObject.GoogleUserInfo;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by Maiko on 20/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GoogleControllerTest {

    private final String JSON_RESPONSE = "{ \"family_name\": \"familyName\", \"name\": \"name\", \"picture\": \"imageUrl\", \"locale\": \"en-GB\", \"link\": \"linkToUser\", \"given_name\": \"givenName\", \"id\": \"id\" }";

    @Mock
    private HttpClient httpClient;

    @InjectMocks
    private GoogleController controller;

    @Test
    public void getUserInfoTest() throws Exception {
        Mockito.when(httpClient.sendGet(Mockito.any(), Mockito.anyMap()))
                .thenReturn(JSON_RESPONSE);
        GoogleUserInfo info = controller.getUserInfo("authorizationHeader");
        Assert.assertEquals("Wrong user returned.",
                "familyName", info.getFamily_name());
        Assert.assertEquals("Wrong user returned.",
                "name", info.getName());
        Assert.assertEquals("Wrong user returned.",
                "imageUrl", info.getPicture());
        Assert.assertEquals("Wrong user returned.",
                "en-GB", info.getLocale());
        Assert.assertEquals("Wrong user returned.",
                "linkToUser", info.getLink());
        Assert.assertEquals("Wrong user returned.",
                "givenName", info.getGiven_name());
        Assert.assertEquals("Wrong user returned.",
                "id", info.getId());
    }
}
