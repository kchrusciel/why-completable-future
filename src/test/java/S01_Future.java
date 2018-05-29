import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 2004
 * Java 5 or 1.5
 */
class S01_Future {

    private static final Logger log = LoggerFactory.getLogger(S01_Future.class);

    @Test
    public void sjug_7() throws Exception {
        MyTask myTask = new MyTask();
        myTask.call();

        //How to run?
    }

    @Test
    public void sjug_21() throws Exception {
        MyTask myTask = new MyTask();

        //ExecutorService
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> callable = executorService.submit(myTask);
        callable.get(); //blocking
    }

    @Test
    @DisplayName("ThreadFactory")
    public void sjug_34() throws Exception {
        MyTask myTask = new MyTask();

        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat("import-task-%d")
                .build();

        //pool-n-thread-m

        //ExecutorService
        ExecutorService executorService = Executors.newFixedThreadPool(10, threadFactory);
        Future<String> callable = executorService.submit(myTask);
        callable.get(); //blocking
    }

    @Test
    public void sjug_51() throws Exception {
        MyTask resultFromFirstDB = new MyTask();
        MyTask doSomethingOnResultFromDB = new MyTask();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> firstResult = executorService.submit(resultFromFirstDB);

        String resultFromFirstFuture = firstResult.get(); //blocking

        Future<String> secondResult = executorService.submit(doSomethingOnResultFromDB);

        String resultFromSecondFuture = secondResult.get(); //blocking

        //How to compose transformations?
    }

    @Test
    public void sjug_66() throws Exception {
        MyTask resultFromFirstDB = new MyTask();
        MyTask resultFromSecondDB = new MyTask();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> firstResult = executorService.submit(resultFromFirstDB);
        Future<String> secondResult = executorService.submit(resultFromSecondDB);

//        String resultFromFirstFuture = firstResult.get(); //blocking
//        String resultFromSecondFuture = secondResult.get(); //blocking


        //How to get first result?
    }

}

class MyTask implements Callable<String> {

    private static final Logger log = LoggerFactory.getLogger(MyTask.class);

    @Override
    public String call() throws Exception {
        log.info("Callable");
        return "Callable";
    }
}
