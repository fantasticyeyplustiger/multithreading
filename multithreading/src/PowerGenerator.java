public abstract class PowerGenerator {

    static final double maxOverclockSpeed = 2.5;

    static double totalFuelBeingBurnt = 0.0;

    String fuelType;

    // In minutes.
    double defaultBurnTime;
    double defaultPowerGeneration; // In megawatts.
    double overclockSpeed = 1.0; // Numerical multiplier. Not a percentage.

    double fuelUsagePerBurnTime;

    int unitsOfSpace;

    boolean isRunning = false;

    PowerGenerator(){}

    /**
     * Burns the set amount of fuel.
     */
    abstract void burnFuel();

    /**
     * If there isn't enough fuel, power generator will shut off.
     */
    abstract void shutOff();

    /**
     * @return How many megawatts of power it makes when on. If it isn't running, returns zero.
     */
    public double getTotalPower(){
        return defaultPowerGeneration * overclockSpeed;
    }

    /**
     * @return How long it takes for this generator to burn through fuel in minutes.
     */
    public double getBurnTime(){
        return defaultBurnTime / overclockSpeed;
    }

    /**
     * Sets overclock speed to the new overclock speed.
     * @param newOverclockSpeed The new speed. Will be set to zero if negative and set to max overclock speed if over the limit.
     */
    public void setOverclockSpeed(double newOverclockSpeed){

        newOverclockSpeed = Math.min(newOverclockSpeed, maxOverclockSpeed);
        newOverclockSpeed = Math.max(newOverclockSpeed, 0.0);

        overclockSpeed = newOverclockSpeed;
    }

    /**
     * @return Class of the generator, its overclock speed, and the total power it's currently making.
     */
    @Override
    public String toString(){
        String name = this.getClass().getName();

        return name + ", Production Speed: " + overclockSpeed + "x, Total Power: " + getTotalPower();
    }

}
