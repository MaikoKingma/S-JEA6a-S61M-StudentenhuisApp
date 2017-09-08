package JMS;

import JMS.Gateway.MessageReceiverGateway;
import JMS.Message.JMSMessage;

import javax.ejb.Stateless;
import javax.jms.*;

@Stateless
public class JMSBrokerGateway {
    private MessageReceiverGateway receiver;

    public JMSBrokerGateway() {
        receiver = new MessageReceiverGateway("StudentenhuisappRequestQueue");
        receiver.setListener(message -> {
            if (message instanceof TextMessage)
            {
                try {
                    String json = ((TextMessage)message).getText();
                    onMessageArrived(json);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void onMessageArrived(String json) {
        JMSMessage message = JMSMessage.fromJson(json);

        //ToDo show message in frontend
        //UserStory: https://trello.com/c/3khUSScw
    }
}
