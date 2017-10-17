package REST.DataTransferObject;

import Models.Group;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class newGroupInfo {
    private Group group;
    private long accountId;

    public newGroupInfo() { }

    public newGroupInfo(Group group, long accountId) {
        this.group = group;
        this.accountId = accountId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
