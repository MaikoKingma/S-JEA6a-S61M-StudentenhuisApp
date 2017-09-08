package JMS;

import JMS.Gateway.*;
import JMS.Message.*;

import java.util.Calendar;

public class JMSBrokerGateway {
    private MessageSenderGateway sender;
    private long id = 0;

    public JMSBrokerGateway() {
        sender = new MessageSenderGateway("StudentenhuisappRequestQueue");
    }

    public void sendMessage(String body, Events event, long groupId, long userId) {
        JMSMessage message = new JMSMessage(nextId(), Calendar.getInstance(), event, body);
        message.setGroupId(groupId);
        message.setUserId(userId);
        sendMessage(message);
    }

    public void sendMessage(String body, Events event, long userId) {
        JMSMessage message = new JMSMessage(nextId(), Calendar.getInstance(), event, body);
        message.setUserId(userId);
        sendMessage(message);
    }

    public void sendMessage(String body, Events event) {
        sendMessage(new JMSMessage(nextId(), Calendar.getInstance(), event, body));
    }

    private void sendMessage(JMSMessage message) {
        sender.send(sender.createTextMessage(message.toJson()));
    }

    private long nextId() {
        id = id++;
        return id;
    }
}
