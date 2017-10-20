package Service;

import DAO.IAccountDao;
import DataTransferObject.GoogleUserInfo;
import HttpClient.GoogleController;
import JMS.JMSBrokerGateway;
import JMS.Message.Events;
import Models.Account;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;

@Stateless
public class AccountService {

    @Inject
    private IAccountDao accountDao;
    @Inject
    private JMSBrokerGateway jmsBroker;
    @Inject
    private OAuthService oAuthService;
    @Inject
    private GoogleController googleController;

    public AccountService() { }

    public Account edit(Account account) {
        account.setActive(true);
        Account newAccount = accountDao.edit(account);
        jmsBroker.sendMessage("Account modified", Events.ACCOUNT_MODIFIED, newAccount.getId());
        return newAccount;
    }

    public Account findById(long id) {
        return accountDao.findById(id);
    }

    public URI requestLogin() {
        return oAuthService.getAuthorizationUri();
    }

    public Account accountAuthorized(String authorizationCode) {
        Account account;
        try {
            GoogleTokenResponse tokenResponse = oAuthService.getAccessToken(authorizationCode);
            GoogleUserInfo userInfo = googleController.getUserInfo(tokenResponse.getTokenType() + " " + tokenResponse.getAccessToken());
            account = findByGoogleId(userInfo.getId());
            if (account == null) {
                account = accountDao.create(new Account(userInfo.getName(), userInfo.getId()));
            }
            Object credentials = oAuthService.getCredentials(account.getId() + "");
            if (credentials == null) {
                oAuthService.storeCredentials(tokenResponse, account.getId() + "");
            }
        } catch (IOException e) {
            throw new NullPointerException(e.getMessage());
        }

        jmsBroker.sendMessage("User logged in", Events.ACCOUNT_LOGGED_IN, account.getId());
        return account;
    }

    public Account findByGoogleId(String googleId) {
        return accountDao.findByGoogleId(googleId);
    }

    public String getAccessToken(long userId) {
        try {
            return "Bearer " + oAuthService.getCredentials(userId + "").getAccessToken();
        } catch (IOException e) {
            throw new NullPointerException(e.getMessage());
        }
    }
}
