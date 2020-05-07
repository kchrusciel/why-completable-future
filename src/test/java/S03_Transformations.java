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
    public void thenApply() {
        CompletableFuture<String> stringResult = CompletableFuture.supplyAsync(someTask(), executorService());

        CompletableFuture<Integer> integerResult = stringResult.thenApply(String::length); //non-blocking

        integerResult.thenApply(integer -> integer + 10);
    }

    @Test
    public void thenAccept() throws ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(someTask(), executorService())
            .thenAccept(log::info)
            .get();
    }

    @Test
    @DisplayName("On which thread in few transformations?")
    public void test_36() throws ExecutionException, InterruptedException {
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

    CompletableFuture<Integer> integerResult1 (int value) {
        return CompletableFuture.completedFuture(value);
    }
    CompletableFuture<Integer> integerResult2 (int value) {
        return CompletableFuture.completedFuture(value);
    }
    CompletableFuture<Integer> integerResult3 (int value) {
        return CompletableFuture.completedFuture(value);
    }
    CompletableFuture<Integer> integerResult4 (int value) {
        return CompletableFuture.completedFuture(value);
    }
    CompletableFuture<Integer> integerResult5 (int value) {
        return CompletableFuture.completedFuture(value);
    }

    @Test
    @DisplayName("Flat map")
    public void thenCompose() {
        integerResult1(10).thenApply(x -> integerResult2(x));

        integerResult1(10) //callback hell
                .thenAccept(x -> integerResult2(x)
                        .thenAccept(y -> integerResult3(y)));

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
