package Service;

import DAO.IAccountDao;
import JMS.JMSBrokerGateway;
import JMS.Message.Events;
import Models.Account;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.*;
import java.net.URI;
import java.util.*;

@Stateless
public class AccountService {

    //All endpoints we want to use
    private final List<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/plus.login");
    //Where user access tokens should be stored
    private MemoryDataStoreFactory dataStoreFactory;
    //basically does everything for you concerning oAuth2
    private GoogleAuthorizationCodeFlow authorizationCodeFlow;
    private List<String> redirectUris;

    @Inject
    private IAccountDao accountDao;
    @Inject
    private JMSBrokerGateway jmsBroker;

    /**
     * Constructor used by UniTests to mock the GoogleAuthorizationCodeFlow
     */
    public AccountService(GoogleAuthorizationCodeFlow authorizationCodeFlow, List<String> redirectUris) {
        this.authorizationCodeFlow = authorizationCodeFlow;
        this.redirectUris = redirectUris;
    }

    public AccountService() {
        //ToDo Store in database
        dataStoreFactory = new MemoryDataStoreFactory();
        JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

        //Load oAuth properties
        try(InputStreamReader stream = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("/client_secrets.json"))) {
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, stream);
            redirectUris = clientSecrets.getDetails().getRedirectUris();

            //Create AuthorizationFlow
            authorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), JSON_FACTORY, clientSecrets, SCOPES)
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

    public Credential loadCredentials(String userId) throws IOException {
        return authorizationCodeFlow.loadCredential(userId);
    }

    public Account create(Account account) {
        List<Account> currentAccounts = accountDao.getAll();
        for(Account a : currentAccounts) {
            if (a.getMail().equals(account.getMail())) {
                throw new NullPointerException();
            }
        }

        account.setActive(true);
        Account newAccount = accountDao.create(account);
        jmsBroker.sendMessage("New Account Created", Events.ACCOUNT_CREATED, newAccount.getId());
        return newAccount;
    }

    public Account edit(Account account) {
        account.setActive(true);
        Account newAccount = accountDao.edit(account);
        jmsBroker.sendMessage("Account modified", Events.ACCOUNT_MODIFIED, newAccount.getId());
        return newAccount;
    }

    public Account login(String mail) {
        Account account = accountDao.findByMail(mail);
        jmsBroker.sendMessage("User logged in", Events.ACCOUNT_LOGGED_IN, account.getId());
        return account;
    }

    public Account findById(long id) {
        return accountDao.findById(id);
    }
}
