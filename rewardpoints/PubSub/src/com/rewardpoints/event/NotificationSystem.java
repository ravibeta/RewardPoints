package com.rewardpoints.event;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.Map;
import java.util.HashMap;
// import javax.annotation.concurrent.GuardedBy;
import lombok.Data;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationSystem<T extends Notification> {
    // @GuardedBy("$lock")
    private final Map<String, Notifier<T>> notifierMap = new HashMap<String, Notifier<T>>();
    private final Map<String, Listener<T>> listenerMap = new HashMap<String, Listener<T>>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Synchronized
    public void addListener(final String type,
                                                     final Listener<T> listener) {
        if (!isListenerPresent(listener)) {
            listenerMap.put(type, listener);
        }
    }

    /**
     * This method will notify listeners.
     *
     * @param notification Notification.
     * @param <T>   Type of notification.
     */
    @Synchronized
    public void notify(final T notification) {
        String type = notification.getClass().getSimpleName();
        Listener<T> listener = listenerMap.get(type);
        log.info("Executing listener of type: {} for notification: {}", type, notification);
        // executorService.execute(() -> listener.onNext(notification),
        //         throwable -> listener.onError(throwable),
        //        () -> listener.onCompleted(),
        //        executorService);
    }

    @Synchronized
    public void removeListener(final String type, final Listener<T> listener) {
        listenerMap.remove(type);
    }


    private boolean isListenerPresent(final Listener<T> listener) {
        return listenerMap.values().stream().anyMatch(le -> le.equals(listener));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Synchronized
    public void addNotifier(final String type,
                                                     final Notifier<T> notifier) {
        if (!isNotifierPresent(notifier)) {
            notifierMap.put(type, notifier);
        }
    }

    @Synchronized
    public void removeNotifier(final String type, final Notifier<T> notifier) {
        notifierMap.remove(type);
    }


    private boolean isNotifierPresent(final Notifier<T> notifier) {
        return notifierMap.values().stream().anyMatch(le -> le.equals(notifier));
    }
}
