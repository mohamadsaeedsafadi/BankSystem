package test.notifications;

import notifications.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class NotificationCenterTest {

    private NotificationCenter center;
    private NotificationObserver observer1;
    private NotificationObserver observer2;

    @BeforeEach
    void setup() {
        center = new NotificationCenter();
        observer1 = mock(NotificationObserver.class);
        observer2 = mock(NotificationObserver.class);
    }

    @Test
    void testAddAndNotifyObservers() {
        center.addObserver(observer1);
        center.addObserver(observer2);

        center.notifyAllObservers("Hello!");

        verify(observer1, times(1)).update("Hello!");
        verify(observer2, times(1)).update("Hello!");
    }

    @Test
    void testRemoveObserver() {
        center.addObserver(observer1);
        center.addObserver(observer2);
        center.removeObserver(observer1);

        center.notifyAllObservers("Update!");

        verify(observer1, never()).update(anyString());
        verify(observer2, times(1)).update("Update!");
    }

    @Test
    void testNoObservers() {
        center.notifyAllObservers("Test"); // لا يجب أن يحدث أي شيء أو خطأ
    }
}
