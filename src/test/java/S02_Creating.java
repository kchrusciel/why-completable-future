import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 2014
 */
class S02_Creating {

    private static final Logger log = LoggerFactory.getLogger(S02_Creating.class);

    @Test
    public void sjug_16() throws Exception {
        Callable<String> blocking = () -> "toChange";
        String resultOfCall = blocking.call(); //blocking
        resultOfCall.toUpperCase();

        CompletableFuture<String> nonBlocking = CompletableFuture.supplyAsync(() -> "toChange");
        nonBlocking.thenApply(String::toUpperCase); //non-blocking
        nonBlocking.get(); //blocking
    }

    @Test
    public void sjug_27() {
        CompletableFuture.completedFuture("42");
    }

    @Test
    public void sjug_32() {
        //On which thread pool?
        CompletableFuture.supplyAsync(() -> "42");
        CompletableFuture.supplyAsync(() -> "42", executorService());
    }

    @Test
    public void sjug_39() {
        //On which thread pool?
        CompletableFuture.runAsync(() -> log.debug("Runnable"));
        CompletableFuture.runAsync(() -> log.debug("Runnable"), executorService());

        //1.8
        //ForkJoinPool - is a common pool
        //size is number of thread + 1 = 4 cores * 2 + 1 = 9
        //Stream.prrraler()
        //ForkJoinPool - divid and concncure
        // list of 100 numbers
        // 100 / 50 and 50 / 25 and 25 and 25 and 25


    }

    ExecutorService executorService() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat("task-%d")
                .build();

        return Executors.newFixedThreadPool(10, threadFactory);
    }

    String getStringFromWebService() {
        String stringFromCache = getStringFromCache();

        if (StringUtils.isNotBlank(stringFromCache)) {
            return stringFromCache;
        }

        try {
            return WebService.readString();
        } catch (Exception e) {
            return "Default Value";
        }
    }

    //Asynchronous method
    CompletableFuture<String> getStringFromWebServiceAsync() {
        String stringFromCache = getStringFromCache();

        if (StringUtils.isNotBlank(stringFromCache)) {
            //mark `CompletableFuture` as completed
            return CompletableFuture.completedFuture(stringFromCache); //get on this CF
        }

        try {
            CompletableFuture<String> async = new CompletableFuture<>();

            //mark `CompletableFuture` as completed
            async.complete(WebService.readString());

            return async;
        } catch (Exception e) {
            CompletableFuture<String> async = new CompletableFuture<>();

            //wrap exception to `CompletableFuture`
            async.completeExceptionally(e);

            return async;
        }
    }

    String getStringFromCache() {
        return "Sting from cache";
    }

}


class WebService {

    static String readString() {
        return "String from WS";
    }

}