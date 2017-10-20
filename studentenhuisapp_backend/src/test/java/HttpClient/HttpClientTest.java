package HttpClient;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.URL;

/**
 * Created by Maiko on 20/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class HttpClientTest {

    private final String TEST_URL = "https://www.googleapis.com/auth/surveys";

    @InjectMocks
    private HttpClient httpClient;

    @Test
    public void sendGetTest() throws Exception {
        URL url = new URL(TEST_URL);
        String response = httpClient.sendGet(url);

        Assert.assertEquals("HttpClient gave an incorrect response.",
                "surveys", response);
    }
}
