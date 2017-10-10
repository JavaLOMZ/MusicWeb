package junior.academy.domain;

import java.util.List;

public class Message {

    private int messageId;
    private String messageText;
    private List<User> communicatingUsers;

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public List<User> getCommunicatingUsers() {
        return communicatingUsers;
    }

    public void setCommunicatingUsers(List<User> communicatingUsers) {
        this.communicatingUsers = communicatingUsers;
    }
}
