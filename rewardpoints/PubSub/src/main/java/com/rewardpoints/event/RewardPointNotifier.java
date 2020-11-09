package com.rewardpoints.event;

import java.lang.Override;
import java.util.concurrent.ScheduledExecutorService;
// import javax.annotation.concurrent.GuardedBy;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

/**
 * Notifier for reward points
 */
@Slf4j
public class RewardPointNotifier<T extends Notification> implements Notifier<T> {

    private final NotificationSystem system;
    private final String type;
    private Listener listener;

    public RewardPointNotifier(final NotificationSystem notificationSystem) {
        this.system = notificationSystem;
        this.type = "Notification";
    }

    @java.lang.Override
    public void subscribe(Listener listener) {
        this.listener = listener;
    }

    @java.lang.Override
    public void unsubscribe() {
        if (this.listener != null) {
            this.system.removeListener(type, this.listener);
            this.listener = null;
        }
    }

    @java.lang.Override
    public void onCompleted() {
        this.unsubscribe();
    }

    @java.lang.Override
    public void onError(Throwable exception) {
        log.error("RewardPointNotifier encountered: ", exception);
    }

    @java.lang.Override
    public void onNext(T notification) {
        log.info("RewardPointNotifier sent:", notification);
    }
}
