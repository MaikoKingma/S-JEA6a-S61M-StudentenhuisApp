package DAO;

import Models.Account;

import java.util.List;

public interface IAccountDao {
    Account create(Account account);
    Account edit(Account account);
    List<Account> getAll();
    Account findByMail(String mail);
}
