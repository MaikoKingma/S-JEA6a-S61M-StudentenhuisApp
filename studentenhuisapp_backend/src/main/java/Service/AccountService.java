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

    @Deprecated
    public Account login(String mail) {
        Account account = accountDao.findByMail(mail);
        jmsBroker.sendMessage("User logged in", Events.ACCOUNT_LOGGED_IN, account.getId());
        return account;
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

        } catch (IOException e) {
            throw new NullPointerException(e.getMessage());
        }

        jmsBroker.sendMessage("User logged in", Events.ACCOUNT_LOGGED_IN, account.getId());
        return account;
    }
}
