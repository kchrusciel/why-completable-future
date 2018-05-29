import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

class S05_FirstResult {

    private static final Logger log = LoggerFactory.getLogger(S05_FirstResult.class);

    @Test
    @DisplayName("Race example")
    public void sjug_6() throws ExecutionException, InterruptedException {
        CompletableFuture<String> task = CompletableFuture.supplyAsync(someTask());
        CompletableFuture<String> otherTask = CompletableFuture.supplyAsync(someOtherTask());

        task.acceptEither(otherTask, log::info)
                .get();
    }


    @Test
    @DisplayName("The Winner Takes It All")
    public void sjug_22() throws ExecutionException, InterruptedException {
        CompletableFuture<String> task = CompletableFuture.supplyAsync(longTask());
        CompletableFuture<String> otherTask = CompletableFuture.supplyAsync(shortTask());

        task.acceptEither(otherTask, log::info)
                .get();
    }

    private Supplier<String> longTask() {
        return () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "long task";
        };
    }

    private Supplier<String> shortTask() {
        return () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "short task";
        };
    }

    private Supplier<String> someTask() {
        return () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "some task";
        };
    }

    private Supplier<String> someOtherTask() {
        return () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "some other task"; };
    }

}
