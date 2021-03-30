package org.dongx.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Subscription 与 Subscriber 是一一对应
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class DefaultSubscription implements Subscription {

    private boolean canceled = false;

    private final Subscriber subscriber;

    public DefaultSubscription(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void request(long n) {

    }

    @Override
    public void cancel() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }
}
