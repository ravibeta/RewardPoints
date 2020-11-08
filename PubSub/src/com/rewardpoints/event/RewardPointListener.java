package com.rewardpoints.event;

import java.lang.Override;
import java.util.concurrent.ScheduledExecutorService;
// import javax.annotation.concurrent.GuardedBy;
import lombok.Synchronized;

/**
 * Listener for reward points
 */
public class RewardPointListener<T extends Notification> implements Listener<T> {

    private final NotificationSystem system;

    public RewardPointListener(final NotificationSystem notificationSystem) {
        this.system = notificationSystem;
    }

    @java.lang.Override
    public void subscribe(Notifier notifier) {

    }

    @java.lang.Override
    public void unsubscribe(Notifier notifier) {

    }

    @java.lang.Override
    public void onCompleted() {

    }

    @java.lang.Override
    public void onError(Exception exception) {

    }

    @java.lang.Override
    public void onNext(T Notification) {

    }
}
