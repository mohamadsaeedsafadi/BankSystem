package transactions;

import java.time.LocalDateTime;

public class AuditLogger {

    public static void log(String message) {
        System.out.println("[AUDIT - " + LocalDateTime.now() + "] " + message);
    }
}
