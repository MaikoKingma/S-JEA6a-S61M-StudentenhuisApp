package JMS.Message;

import java.util.Calendar;

import com.google.gson.Gson;

public class JMSMessage {

    private long id;
    private Calendar date;
    private long groupId;
    private long userId;
    private Events event;
    private String body;

    public JMSMessage(long id, Calendar date, Events event, String body) {
        this.id = id;
        this.date = date;
        this.event = event;
        this.body = body;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Note: Only YEAR, MONTH, DAY_OF_YEAR, HOUR_OF_DAY, MINUTE & SECOND are converted to JSON
     */
    public String toJson() {
        return new Gson().toJson(this);
    }

    public static JMSMessage fromJson(String json) {
        return new Gson().fromJson(json, JMSMessage.class);
    }
}
