package com.rewardpoints.event;

import java.lang.Override;
import java.util.concurrent.ScheduledExecutorService;
// import javax.annotation.concurrent.GuardedBy;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
/**
 * Listener for reward points
 */
@Slf4j
public class RewardPointListener<T extends Notification> implements Listener<T> {

    private final NotificationSystem system;
    private Notifier notifier;
    private final String type;

    public RewardPointListener(final NotificationSystem notificationSystem) {
        this.system = notificationSystem;
        this.type = "Notification";
    }

    @java.lang.Override
    public void subscribe(Notifier notifier) {
        if (notifier != null){
            this.notifier = notifier;
            system.addNotifier(type, notifier);
        }
    }

    @java.lang.Override
    public void unsubscribe() {
        if (this.notifier != null) {
            system.removeNotifier(type, notifier);
            this.notifier = null;
        }
    }

    @java.lang.Override
    public void onCompleted() {
        this.unsubscribe();
    }

    @java.lang.Override
    public void onError(Throwable exception) {
        log.error("Notifier: ", notifier);
    }

    @java.lang.Override
    public void onNext(T notification) {
        log.info("Received reward point notification: ", notification);
    }
}
