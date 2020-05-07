import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

/**
 * Created by CodeCouple.pl
 */
class S06_All {

    private static final Logger log = LoggerFactory.getLogger(S06_All.class);

    @Test
    public void allOf() {
        CompletableFuture<String> janusz = CompletableFuture.supplyAsync(() -> "janusz");
        CompletableFuture<String> grazyna = CompletableFuture.supplyAsync(() -> "grazyna");

        //Result type?
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(janusz, grazyna);
        //Very nice java API ;(
    }

    @Test
    public void allOfResult() {
        CompletableFuture<String> janusz = CompletableFuture.supplyAsync(() -> "janusz");
        CompletableFuture<String> grazyna = CompletableFuture.supplyAsync(() -> "grazyna");

        //Result type?
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(janusz, grazyna);
        //Very nice java API ;(
    }

    @Test
    public void anyOf() {
        CompletableFuture<String> janusz = CompletableFuture.supplyAsync(() -> "janusz");
        CompletableFuture<String> grazyna = CompletableFuture.supplyAsync(() -> "grazyna");

        //Result type?
        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(janusz, grazyna);
        //Very nice java API ;(
    }

    @Test
    public void anyOfResult() {
        CompletableFuture<String> janusz = CompletableFuture.supplyAsync(() -> "janusz");
        CompletableFuture<String> grazyna = CompletableFuture.supplyAsync(() -> "grazyna");

        //Result type?
        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(janusz, grazyna);
        //Very nice java API ;(
        if (objectCompletableFuture.join() instanceof String string) {
            log.info(string);
        }

    }


}
