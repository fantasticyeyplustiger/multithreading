public class Stopwatch {

    private long startTime = -1;
    private long elapsedNanoTime = -1;
    private boolean isRunning = false;

    /**
     * Starts the stopwatch. Elapsed time begins to increase from this point.
     * @throws RuntimeException If stopwatch has already started.
     */
    public void start(){
        if (isRunning){
            throw new RuntimeException("Stopwatch is already running!");
        }

        startTime = System.nanoTime();
        isRunning = true;
    }

    /**
     * Stops the stopwatch and starts it again.
     * @throws RuntimeException If stopwatch hasn't already started.
     */
    public void restart(){
        stop();
        start();
    }

    /**
     * Calculates the elapsed time from starting time to current time in nanoseconds.
     * Doesn't calculate if the stopwatch isn't running.
     */
    private void calculateElapsedNanoTime(){
        // If it isn't running, don't calculate.
        // stop() already calculates the last elapsed time.
        if (!isRunning){
            return;
        }

        long endTime = System.nanoTime();
        elapsedNanoTime = endTime - startTime;
    }

    /**
     * Stops the stopwatch. Elapsed time no longer increases.
     * @throws RuntimeException If stopwatch hasn't been started.
     */
    public void stop(){
        if (!isRunning){
            throw new RuntimeException("Stopwatch is not running. Use start() before stopping or restarting!");
        }
        calculateElapsedNanoTime();
        isRunning = false;
    }

    /**
     * Prints the amount of time that has elapsed from starting time to current time in nanoseconds.
     */
    public void printElapsedNanoTime(){
        calculateElapsedNanoTime();
        System.out.println("Nanoseconds elapsed: " + elapsedNanoTime);
    }

    /**
     * Prints the amount of time that has elapsed from starting time to current time in seconds.
     */
    public void printElapsedSeconds(){
        calculateElapsedNanoTime();
        System.out.println("Seconds elapsed: " + ((double) elapsedNanoTime / 1_000_000_000));
    }

    /**
     * Prints the amount of time that has elapsed from starting time to current time in milliseconds.
     */
    public void printElapsedMilliseconds(){
        calculateElapsedNanoTime();
        System.out.println("Milliseconds elapsed: " + ((double) elapsedNanoTime / 1_000_000));
    }

}
