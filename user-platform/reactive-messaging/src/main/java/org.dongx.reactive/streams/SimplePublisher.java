package org.dongx.reactive.streams;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class SimplePublisher<T> implements Publisher<T> {
	
	private List<Subscriber> subscribers = new LinkedList<>();
	
	@Override
	public void subscribe(Subscriber<? super T> s) {
		SubscriptionAdapter subscription = new SubscriptionAdapter(s);
		s.onSubscribe(subscription);
		subscribers.add(subscription.getSourceSubscriber());
	}
}
