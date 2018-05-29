import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by CodeCouple.pl
 */
class S07_Exceptions {

    @Test
    @DisplayName("exceptionally")
    public void sjug_14() throws ExecutionException, InterruptedException {
        CompletableFuture<String> task = CompletableFuture.supplyAsync(() -> "some task")
                .exceptionally((x)->"default value");
        //Todo
    }

    @Test
    @DisplayName("handle")
    public void sjug_16() throws ExecutionException, InterruptedException {
        CompletableFuture<String> task = CompletableFuture.supplyAsync(() -> "some task");

        task.handle((x, throwable) -> {
            if (throwable != null) {
                return "default value";
            }
            return x + "some value";
        });
    }

    @Test
    @DisplayName("whenComplete")
    public void sjug_27() throws ExecutionException, InterruptedException {
        CompletableFuture<String> task = CompletableFuture.supplyAsync(() -> "some task");

        task.whenComplete((x, throwable) -> {
            if (throwable != null) {
                System.out.println("Exception occur");
            }
            System.out.println(x);
        });
    }
}
