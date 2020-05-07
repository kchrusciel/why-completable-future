import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 2004
 * Java 5 or 1.5
 */
class S01_Future {

	private static final Logger log = LoggerFactory.getLogger(S01_Future.class);

	@Test
	public void howToRun() throws Exception {
		MyTask myTask = new MyTask();
		myTask.call();

		//How to run?
	}

	@Test
	public void executorService() throws Exception {
		MyTask myTask = new MyTask();

		//ExecutorService
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		Future<String> callable = executorService.submit(myTask);
		callable.get(); //blocking
	}

	@Test
	public void threadFactory() throws Exception {
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
		callable.get(5, TimeUnit.SECONDS); //blocking
	}

	@Test
	public void twoResults() throws ExecutionException, InterruptedException {
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
	public void firstResult() {
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
	public String call() {
		log.info("Callable");
		return "Callable";
	}
}
