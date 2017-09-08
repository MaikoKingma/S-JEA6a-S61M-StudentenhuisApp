import JMS.Message.Events;
import JMS.Message.JMSMessage;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

public class JMSMessageTest {

    @Test
    public void jmsMessageJSONTest() throws Exception {
        //Create new dummy message
        JMSMessage oldMessage = new JMSMessage(0, Calendar.getInstance(), Events.ACCOUNT_CREATED, "Test body");
        oldMessage.setUserId(0);
        oldMessage.setGroupId(0);

        //Convert message to json
        String json = oldMessage.toJson();

        //Create new message from json
        JMSMessage newMessage = JMSMessage.fromJson(json);

        //Check if the messages are the same
        Assert.assertEquals(oldMessage.getId(), newMessage.getId());
        Assert.assertEquals(oldMessage.getEvent(), newMessage.getEvent());
        Assert.assertEquals(oldMessage.getBody(), newMessage.getBody());
        Assert.assertEquals(oldMessage.getGroupId(), newMessage.getGroupId());
        Assert.assertEquals(oldMessage.getUserId(), newMessage.getUserId());
        Assert.assertEquals(oldMessage.getDate().get(Calendar.DATE), newMessage.getDate().get(Calendar.DATE));
        Assert.assertEquals(oldMessage.getDate().get(Calendar.HOUR_OF_DAY), newMessage.getDate().get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(oldMessage.getDate().get(Calendar.MINUTE), newMessage.getDate().get(Calendar.MINUTE));
        Assert.assertEquals(oldMessage.getDate().get(Calendar.SECOND), newMessage.getDate().get(Calendar.SECOND));
    }
}
