import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

/**
 * Created by CodeCouple.pl
 */
class S06_All {

    @Test
    public void sjug_7() {
        CompletableFuture<String> janusz = CompletableFuture.supplyAsync(() -> "janusz");
        CompletableFuture<String> grazyna = CompletableFuture.supplyAsync(() -> "grazyna");

        //Result type?
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(janusz, grazyna);
        //Very nice java API ;(
    }

    @Test
    public void sjug_19() {
        CompletableFuture<String> janusz = CompletableFuture.supplyAsync(() -> "janusz");
        CompletableFuture<String> grazyna = CompletableFuture.supplyAsync(() -> "grazyna");

        //Result type?
        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(janusz, grazyna);
        //Very nice java API ;(
    }


}
