package DAO;

import Models.Account;

import java.util.List;

public interface IAccountDao {
    Account create(Account account);
    Account edit(Account account);
    List<Account> getAll();
    Account findByMail(String mail);
    Account findById(long id);
    Account findByGoogleId(String googleId);
}
