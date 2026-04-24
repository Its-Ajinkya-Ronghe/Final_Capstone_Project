package utils;

/**
 * Simple wait utility to avoid depending on Selenium's Waits at this stage.
 */
public class WaitUtil {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
