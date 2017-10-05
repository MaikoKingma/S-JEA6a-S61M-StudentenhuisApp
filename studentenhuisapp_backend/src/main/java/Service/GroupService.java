package Service;

import DAO.IGroupDao;
import JMS.JMSBrokerGateway;
import JMS.Message.Events;
import Models.Group;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class GroupService {

    @Inject
    private IGroupDao groupDao;
    @Inject
    private JMSBrokerGateway jmsBroker;

    public Group create(Group group) {
        Group newGroup = groupDao.create(group);
        jmsBroker.sendMessage("New Group Created", Events.GROUP_CREATED, newGroup.getId(), newGroup.getAccounts().get(0).getId());
        return newGroup;
    }
}
