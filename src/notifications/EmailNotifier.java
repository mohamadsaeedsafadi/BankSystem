package notifications;

public class EmailNotifier implements NotificationObserver {

    private String emailAddress;

    public EmailNotifier(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public void update(String message) {
        System.out.println("[Email to " + emailAddress + "] " + message);
    }
}
