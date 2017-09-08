package JMS;

import JMS.Gateway.*;
import JMS.Message.*;

import javax.ejb.Stateless;
import java.util.Calendar;

@Stateless
public class JMSBrokerGateway {
    private MessageSenderGateway sender;
    private long id = 0;

    public JMSBrokerGateway() {
        sender = new MessageSenderGateway("StudentenhuisappRequestQueue");
        sendMessage(new JMSMessage(nextId(), Calendar.getInstance(), Events.SERVER_START, "Studentenhuisapp_backend started"));
    }

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