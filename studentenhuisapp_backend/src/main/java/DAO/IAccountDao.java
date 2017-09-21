package DAO;

import Models.Account;

public interface IAccountDao {
    Account create(Account account);
    Account edit(Account account);
}
