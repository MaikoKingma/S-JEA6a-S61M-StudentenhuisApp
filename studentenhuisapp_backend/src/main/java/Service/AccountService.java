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
import java.util.List;

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
        Account account = null;
        try {
            GoogleTokenResponse tokenResponse = oAuthService.getAccessToken(authorizationCode);
            GoogleUserInfo userInfo = googleController.getUserInfo(tokenResponse.getTokenType() + " " + tokenResponse.getAccessToken());
            account = findByGoogleId(userInfo.getId());
//            if (account == null) {
//                account = accountDao.create(new Account())
//            }
        } catch (IOException e) {
            throw new NullPointerException(e.getMessage());
        }

        jmsBroker.sendMessage("User logged in", Events.ACCOUNT_LOGGED_IN, account.getId());
        return account;
    }

    public Account findByGoogleId(String googleId) {
        return accountDao.findByGoogleId(googleId);
    }
}
