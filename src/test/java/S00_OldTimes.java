import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 1996
 */
class S00_OldTimes {

    private static final Logger log = LoggerFactory.getLogger(S00_OldTimes.class);

    @Test
    public void test_15() {
        MyThread myThread = new MyThread();
        myThread.run();
        //class extends Thread

        //implements Runnable
        MyRunnable myRunnable = new MyRunnable();
        Thread threadForRunnable = new Thread(myRunnable);
        threadForRunnable.start();
    }

    @Test
    public void test_27() {
        MyRunnableWithFields task = new MyRunnableWithFields();
        Thread threadForRunnable = new Thread(task);
        threadForRunnable.start();

        while(threadForRunnable.isAlive()) {
            log.info("Blocking...");
            log.info("Result: " + task.result);
        }
        log.info("After Blocking...");
        log.info("Result: " + task.result);
    }

}

class MyThread extends Thread {

    private static final Logger log = LoggerFactory.getLogger(MyThread.class);

    @Override
    public void run() {
        log.info("Thread");
    }
}

class MyRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(MyRunnable.class);

    @Override
    public void run() {
        log.info("Runnable");
    }
}

class MyRunnableWithFields implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(MyRunnableWithFields.class);

    String result;

    @Override
    public void run() {
        try {
            log.info("Sleeping...");
            TimeUnit.MILLISECONDS.sleep(10);
            result = "Done";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
