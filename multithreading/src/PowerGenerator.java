public abstract class PowerGenerator implements Runnable {

    static final double maxOverclockSpeed = 2.5;

    // In minutes.
    double defaultBurnTime;
    double defaultPowerGeneration; // In megawatts.
    double overclockSpeed; // Numerical multiplier. Not a percentage.

    boolean isRunning = false;

    /**
     * Burns the set fuel every getBurnTime() minutes.
     */
    abstract void burnFuel();

    /**
     * If there isn't enough fuel, power generator automatically shuts off.
     */
    abstract void shutOff();

    /**
     * @return How many megawatts of energy it makes when on. If it isn't running, returns zero.
     */
    double getTotalPower(){
        return (isRunning) ? (defaultPowerGeneration * overclockSpeed) : 0.0;
    }

    /**
     * @return How much fuel this generator should burn per minute, regardless if it's running or not.
     */
    double getBurnTime(){
        return defaultBurnTime * overclockSpeed;
    }

    /**
     * Sets overclock speed to the new overclock speed.
     * @param newOverclockSpeed The new speed. Will be set to zero if negative and set to max overclock speed if over the limit.
     */
    void setOverclockSpeed(double newOverclockSpeed){

        newOverclockSpeed = Math.min(newOverclockSpeed, maxOverclockSpeed);
        newOverclockSpeed = Math.max(newOverclockSpeed, 0.0);

        overclockSpeed = newOverclockSpeed;
    }

}
