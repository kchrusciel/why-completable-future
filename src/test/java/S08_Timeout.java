import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class S08_Timeout {

	private static final Logger log = LoggerFactory.getLogger(S08_Timeout.class);

	// http://iteratrlearning.com/java9/2016/09/13/java9-timeouts-completablefutures.html

	ScheduledExecutorService delayer = Executors.newScheduledThreadPool(5);

	public <T> CompletableFuture<T> timeoutAfter(long timeout, TimeUnit unit) {
		CompletableFuture<T> result = new CompletableFuture<T>();
		delayer.schedule(() -> result.completeExceptionally(new TimeoutException()), timeout, unit);
		return result;
	}

	@Test
	public void before8() {

		// CompletableFuture.supplyAsync(() -> "42").get() - blocking
		// CompletableFuture.supplyAsync(() -> "42").get(5, TimeUnit.SECONDS) - blocking

		CompletableFuture.supplyAsync(() -> "42")
				.acceptEither(timeoutAfter(5, TimeUnit.SECONDS), log::info);
	}

	@Test
	public void orTimeout() {
		// Java 9
		var async = CompletableFuture.supplyAsync(() -> {
						try {
							Thread.sleep(10_000);
						} catch(InterruptedException e) {
							e.printStackTrace();
						}
						return "abc";
					})
				.thenApply(x -> x + "3")
				.orTimeout(5, TimeUnit.SECONDS)
				.join();
	}

	@Test
	public void completeOnTimeout() {
		// Java 9
		var async = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(10_000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			return "abc";
		});
		var result = async.thenApply(x -> x + "d")
				.completeOnTimeout("default", 5, TimeUnit.SECONDS)
				.join();

		log.info(result);
	}

}
