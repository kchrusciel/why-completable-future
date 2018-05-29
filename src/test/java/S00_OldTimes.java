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
    public void sjug_8() {
        MyThread myThread = new MyThread();
        myThread.run();

        MyRunnable myRunnable = new MyRunnable();
        Thread threadForRunnable = new Thread(myRunnable);
        threadForRunnable.start();
    }

    @Test
    public void sjug_25() throws InterruptedException {
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

    String result;

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(10);
            result = "Done";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}