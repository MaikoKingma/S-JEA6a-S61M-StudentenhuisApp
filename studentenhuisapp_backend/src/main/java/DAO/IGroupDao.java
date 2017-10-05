package DAO;

import Models.Group;

import java.util.List;

public interface IGroupDao {
    Group create(Group group);
    List<Group> getAll();
}
