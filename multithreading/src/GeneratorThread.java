import java.util.ArrayList;

public class GeneratorThread implements Runnable{

    private Thread loopThread;
    private boolean isRunning = false;

    ArrayList<PowerGenerator> powerGenerators = new ArrayList<>();

    /**
     * Starts the thread's physics loop.
     */
    public synchronized void startThread(){
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

        if (allGensNotRunning){
            System.out.println("Generator Thread stopped!");
            stopThread();
        }
    }
}
