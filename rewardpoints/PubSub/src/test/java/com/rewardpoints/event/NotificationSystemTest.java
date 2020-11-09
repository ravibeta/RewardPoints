import com.rewardpoints.event;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class NotificationSystemTest {
    @Test
    public void testCreation() {
        NotificationSystem notificationSystem = new NotificationSystem();
        RewardPointNotifier notifier = new RewardPointNotifier(notificationSystem);
        RewardPointSubscriber listener = new RewardPointNotifier(notificationSystem);
        notifier.subscribe("Notification", listener);
        listener.subscribe("Notification", notifier);
        Assert.assertEquals(notificationSystem.isNotifierPresent(), true);
        Assert.assertEquals(notificationSystem.isListenerPresent(), true);
    }
}