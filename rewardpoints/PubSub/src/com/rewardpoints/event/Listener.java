package com.rewardpoints.event;

/**
 * Listener interface for receiving notifications.
 * @param <T> Notification type.
 */
// @FunctionalInterface
public interface Listener<T> {
    /**
     * Attach a notifier for notification type T.
     * @param notifier This is the notifier.
     *
     */
    void subscribe(final Notifier<T> notifier);

    /**
     * Detach a notifier.
     */
    void unsubscribe();

    /**
     * finished notifying.
     */
    void onCompleted();

    /**
     * regular event processing.
     */
    void onNext(T notification);

    /**
     * failed event processing.
     */
    void onError(Throwable exception);

}
