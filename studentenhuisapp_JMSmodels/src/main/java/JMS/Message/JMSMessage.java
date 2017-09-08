package JMS.Message;

import java.util.Calendar;

public class JMSMessage {

    private long id;
    private Calendar date;
    private long groupId;
    private long userId;
    private Events event;

    public JMSMessage(long id, Calendar date, Events event) {
        this.id = id;
        this.date = date;
        this.event = event;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Events getEvent() {
        return event;
    }

    public void setEvent(Events event) {
        this.event = event;
    }
}
