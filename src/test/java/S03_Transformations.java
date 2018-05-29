import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by CodeCouple.pl
 */
class S03_Transformations {

    private static final Logger log = LoggerFactory.getLogger(S03_Transformations.class);

    @Test
    @DisplayName("Then apply")
    public void sjug_16() throws ExecutionException, InterruptedException {
        CompletableFuture<String> stringResult = CompletableFuture.supplyAsync(someTask(), executorService());

        CompletableFuture<Integer> integerResult = stringResult.thenApply(string -> string.length());

        integerResult.thenApply(integer -> integer + 10);
    }

    @Test
    @DisplayName("Then accept")
    public void sjug_27() throws ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(someTask(), executorService())
            .thenAccept(log::info)
            .get();
    }

    @Test
    @DisplayName("On which thread in few transformations?")
    public void sjug_37() throws ExecutionException, InterruptedException {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(getString(), executorService());
        stringCompletableFuture.thenApply(getLength())
            .thenApply(addTen())
            .thenApply(addTen())
            .thenApply(addTen())
            .thenApply(addTen())
            .thenApply(addTen())
            .get();
        //Perfomance
    }

    private Supplier<String> getString() {
        return () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("a");
            return "42";
        };
    }

    private Function<String, Integer> getLength() {
        return (x) -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("b");
            return x.length();
        };
    }

    private Function<Integer, Integer> addTen() {
        return (y) -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("c");
            return y+10;
        };
    }

    ExecutorService executorService() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat("task-%d")
                .build();

        return Executors.newFixedThreadPool(10, threadFactory);
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
}
