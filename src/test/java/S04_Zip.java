import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Created by CodeCouple.pl
 */
class S04_Zip {

    private static final Logger log = LoggerFactory.getLogger(S04_Zip.class);

    @Test
    public void thenCombine() throws ExecutionException, InterruptedException {
        CompletableFuture<String> stringResult = CompletableFuture.supplyAsync(someTask(), executorService());

        CompletableFuture<Integer> integerResult = CompletableFuture.supplyAsync(someIntegerTask(), executorService());


        CompletableFuture<String> result = stringResult
                .thenCombine(integerResult, (string, integer) -> string + " :and: " + integer);

        log.info(result.get()); //blocking

        //Functional programming is a ZIP operator
        //http://reactivex.io/documentation/operators/zip.html
    }

    @Test
    public void thenAcceptBoth() throws ExecutionException, InterruptedException {
        CompletableFuture<String> stringResult = CompletableFuture.supplyAsync(someTask(), executorService());

        CompletableFuture<Integer> integerResult = CompletableFuture.supplyAsync(someIntegerTask(), executorService());

        CompletableFuture<Void> result = stringResult
                .thenAcceptBoth(integerResult, (string, integer) -> log.info(string + " :and: " + integer));

        result.get(); //blocking
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

    private Supplier<Integer> someIntegerTask() {
        return () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        };
    }

}
