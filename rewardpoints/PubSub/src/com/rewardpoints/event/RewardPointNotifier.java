package com.rewardpoints.event;

import java.lang.Override;
import java.util.concurrent.ScheduledExecutorService;
// import javax.annotation.concurrent.GuardedBy;
import lombok.Synchronized;

/**
 * Notifier for reward points
 */
public class RewardPointNotifier<T extends Notification> implements Notifier<T> {

    private final NotificationSystem system;

    public RewardPointNotifier(final NotificationSystem notificationSystem) {
        this.system = notificationSystem;
    }

    @java.lang.Override
    public void subscribe(Listener listener) {

    }

    @java.lang.Override
    public void unsubscribe(Listener listener) {

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
