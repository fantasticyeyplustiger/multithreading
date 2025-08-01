import java.util.ArrayList;

public class GeneratorThread implements Runnable{

    private Thread loopThread;
    private boolean isRunning = false;

    PowerGenerator[] powerGenerators;

    Stopwatch stopwatch = new Stopwatch();

    GeneratorThread(PowerGenerator[] powerGenerators){
        this.powerGenerators = powerGenerators;
    }

    /**
     * Starts the thread's physics loop.
     */
    public synchronized void startThread(){
        stopwatch.start();
        isRunning = true;
        loopThread = new Thread(this, "LoopThread");
        loopThread.start();
    }

    /**
     * Stops the thread entirely.
     */
    public synchronized void stopThread() {
        isRunning = false;
        try {
            loopThread.join(); // Waits for the thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("--- Thread was interrupted! ---");
        }
    }

    /**
     * A "Physics loop". Attempts to run exactly 60 times per second until stopped.
     */
    @Override
    public void run() {

        final double frames = 60.0;
        final double framesPerSecond = 1_000_000_000 / frames;

        long lastTime = System.nanoTime();
        double delta = 0.0;

        while (isRunning){
            long currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / framesPerSecond;
            lastTime = currentTime;

            // Updates generators 'frames' times per second.
            while (delta >= 1){
                updateAllGenerators(delta);
                delta--;
            }
        }
    }

    /**
     * Updates all generators and attempts to have them burn fuel.
     * @param delta The time added to all generators.
     */
    public void updateAllGenerators(double delta){
        boolean allGensNotRunning = true;

        for (PowerGenerator generator : powerGenerators){
            generator.burnFuel(delta);

            if (allGensNotRunning && generator.isRunning){
                allGensNotRunning = false;
            }
        }

        calculateFibonacci(powerGenerators.length * 25);

        if (allGensNotRunning){
            System.out.println(powerGenerators.getClass().getCanonicalName() + " Thread stopped! ");
            stopwatch.stop();
            stopwatch.printElapsedMilliseconds();
            System.out.println("Amount of gens: " + powerGenerators.length);
            System.out.println();
            stopThread();
        }
    }

    private void calculateFibonacci(int n){
        long firstTerm = 0;
        long secondTerm = 1;

        for (int i = 0; i < n; i++) {
            long nextTerm = firstTerm + secondTerm;
            firstTerm = secondTerm;
            secondTerm = nextTerm;
        }
    }
}
