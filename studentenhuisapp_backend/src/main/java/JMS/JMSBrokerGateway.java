package JMS;

import JMS.Gateway.MessageSenderGateway;
import JMS.Message.*;

//import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.util.Calendar;

@Stateless
public class JMSBrokerGateway {
    private MessageSenderGateway sender;
    private long id = 0;

    public JMSBrokerGateway() {
        sender = new MessageSenderGateway("StudentenhuisappRequestQueue");
        JMSMessage message = new JMSMessage(nextId(), Calendar.getInstance(), Events.SERVER_START, "Studentenapp_backend started");
        message.setGroupId(-1);
        message.setUserId(-1);
        sendMessage(message);
    }

//    @PostConstruct
//    public void init() {
//        sender = new MessageSenderGateway("StudentenhuisappRequestQueue");
//        sendMessage("Studentenhuisapp_backend started", Events.SERVER_START);
//    }

    public void sendMessage(String body, Events event, long groupId, long userId) {
        JMSMessage message = new JMSMessage(nextId(), Calendar.getInstance(), event, body);
        message.setGroupId(groupId);
        message.setUserId(userId);
        sendMessage(message);
    }

    public void sendMessage(String body, Events event, long userId) {
        JMSMessage message = new JMSMessage(nextId(), Calendar.getInstance(), event, body);
        message.setGroupId(-1);
        message.setUserId(userId);
        sendMessage(message);
    }

    public void sendMessage(String body, Events event) {
        JMSMessage message = new JMSMessage(nextId(), Calendar.getInstance(), event, body);
        message.setGroupId(-1);
        message.setUserId(-1);
        sendMessage(message);
    }

    private void sendMessage(JMSMessage message) {
        sender.send(sender.createTextMessage(message.toJson()));
    }

    private long nextId() {
        id = id++;
        return id;
    }
}
