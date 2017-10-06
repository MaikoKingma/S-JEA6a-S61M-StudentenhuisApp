package Service;

import DAO.IGroupDao;
import JMS.JMSBrokerGateway;
import JMS.Message.Events;
import Models.*;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class GroupService {

    @Inject
    private IGroupDao groupDao;
    @Inject
    private AccountService accountService;
    @Inject
    private JMSBrokerGateway jmsBroker;

    public Group create(Group group, long creatorId) {
        Group newGroup = groupDao.create(group);
        Account creator = accountService.findById(creatorId);
        creator.addGroup(newGroup);
        Account updatedCreator = accountService.edit(creator);
        newGroup = findById(newGroup.getId());
        jmsBroker.sendMessage("New Group Created", Events.GROUP_CREATED, newGroup.getId(), updatedCreator.getId());
        return group;
    }

    public Group findById(long id) {
        return groupDao.findById(id);
    }
}
