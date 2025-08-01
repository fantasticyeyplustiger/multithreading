import java.util.ArrayList;

public class GeneratorThread implements Runnable{

    private final Stopwatch stopwatch = new Stopwatch();

    private boolean isRunning = false;

    PowerGenerator[] powerGenerators;

    /**
     * Runs a physics loop that 'ticks' 60 times per second.
     * Burns all the fuel in its generators when available and simulates other work per generator.
     * @param powerGenerators The generators that are burning fuel.
     */
    GeneratorThread(PowerGenerator[] powerGenerators){
        this.powerGenerators = powerGenerators;
    }

    /**
     * A "Physics loop". Attempts to run exactly 60 times per second until stopped.
     */
    @Override
    public void run() {
        stopwatch.start();

        final double frames = 60.0;
        final double framesPerSecond = 1_000_000_000 / frames;

        long lastTime = System.nanoTime();
        double delta = 0.0;

        isRunning = true;

        while (isRunning){
            long currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / framesPerSecond;
            lastTime = currentTime;

            // Simulate work
            calculateFibonacci(150);

            // Updates generators 'frames' times per second.
            while (delta >= 1 && isRunning){
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

        // Simulate other activities for the amount of power generators there are
        for (int i = 0; i < powerGenerators.length * 5000; i++) {
            calculateFibonacci(100);
        }

        if (allGensNotRunning){
            stopwatch.stop();

            double power = 0.0;
            for (PowerGenerator generator : powerGenerators){
                power += generator.getTotalPower();
            }

            stopwatch.printElapsedMilliseconds();

            // All in one print statement because I don't want to deal with synchronizing all the threads yet
            System.out.println(powerGenerators.getClass().getCanonicalName() + " Thread stopped!\n" +
                    "Amount of gens: " + powerGenerators.length + ", " +
                    powerGenerators.getClass().getCanonicalName() + "'s Total Power: " + power + "\n");

            isRunning = false;
        }
    }

    /**
     * Literally just calculates the nth term of the Fibonacci Sequence.
     * @param n The nth term being calculated.
     */
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
