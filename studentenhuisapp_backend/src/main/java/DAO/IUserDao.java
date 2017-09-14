package DAO;

import Models.User;

public interface IUserDao {
    User create(User user);
    User edit(User user);
}
