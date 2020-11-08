package com.rewardpoints.event;

/**
 * This represents an observable notification.
 * @param <T> The type of event that is to be observed.
 */
public interface Notifier<T> {
    /**
     * Attach a listener for notification type T.
     * @param listener This is the listener.
     *
     */
    void subscribe(final Listener<T> listener);

    /**
     * Detach a listener.
     * @param listener the listener which needs to be removed.
     */
    void unsubscribe(final Listener<T> listener);

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
    void onError(Exception exception);
    
}
