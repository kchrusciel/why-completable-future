import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by CodeCouple.pl
 */
class S07_Exceptions {

	private static final Logger log = LoggerFactory.getLogger(S07_Exceptions.class);

	@Test
	public void failedFuture() {
		var failedFuture = CompletableFuture.failedFuture(new IllegalStateException());
		System.out.println(failedFuture.join());
	}

	@Test
	public void isCompletedExceptionally() {
		var failedFuture = CompletableFuture.failedFuture(new IllegalStateException());
		System.out.println(failedFuture.isCompletedExceptionally());
	}

	@Test
	public void exceptionally() {
		var task = CompletableFuture.failedFuture(new IllegalStateException())
				.exceptionally((x) -> "default value")
				.exceptionallyAsync((x) -> "default value"); //Java 12

		System.out.println(task.join());
	}

	@Test
	public void completeExceptionally() throws ExecutionException, InterruptedException {
		var task = CompletableFuture.supplyAsync(() -> "value")
				.thenApply(x -> {
					try {
						Thread.sleep(10_000);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
					return "new value";
				});

		task.completeExceptionally(new IllegalStateException());
//		task.obtrudeException(new IllegalStateException());

		System.out.println(task.get());
	}

	@Test
	@DisplayName("handle")
	public void handle() throws ExecutionException, InterruptedException {
		var task = CompletableFuture.failedFuture(new IllegalStateException())
				.handle((x, throwable) -> {
					if(throwable != null) {
						return "default value";
					}
					return "x";
				});

		log.info(task.get());
	}

	@Test
	public void whenComplete() throws ExecutionException, InterruptedException {
		var task = CompletableFuture.failedFuture(new IllegalStateException())
				.whenComplete((x, throwable) -> {
					if(throwable != null) {
						System.out.println("Exception occurs");
					}
					System.out.println(x);
				});

		task.join();
	}

	@Test
	public void exceptionallyCompose() throws ExecutionException, InterruptedException {
		CompletableFuture<Integer> completableFuture = CompletableFuture
				.supplyAsync(() -> 10 / 0);
		CompletableFuture<Integer> completableFuture2 = CompletableFuture
				.supplyAsync(() -> 1);

		CompletableFuture<Integer> exceptionallyCompose = completableFuture
				.exceptionallyCompose(throwable -> {
					System.err.println("exception: " + throwable);
					return completableFuture2;
				});
		exceptionallyCompose.thenApply(i -> i * 3)
				.thenAccept(System.out::println);
	}
}
