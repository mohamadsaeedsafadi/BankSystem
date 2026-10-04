package notifications;

public class AppNotifier implements NotificationObserver {

    private String username;

    public AppNotifier(String username) {
        this.username = username;
    }

    @Override
    public void update(String message) {
        System.out.println("[App Notification to " + username + "] " + message);
    }
}
