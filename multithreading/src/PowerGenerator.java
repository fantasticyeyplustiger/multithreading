public abstract class PowerGenerator {

    static final double maxOverclockSpeed = 2.5;

    static double totalFuelBeingBurnt = 0.0;
    static double totalFuelAvailable = 0.0;

    String fuelType;

    // In seconds.
    double defaultBurnTime;
    double defaultPowerGeneration; // In megawatts.
    double overclockSpeed = 1.0; // Numerical multiplier. Not a percentage. Probably not going to deal with it until final build.
    double currentTime = 0.0; // How much time has passed during the process of burning fuel.

    double fuelUsagePerBurnTime;

    int unitsOfSpace;

    boolean isRunning = true;

    PowerGenerator(){}

    /**
     * Burns the set amount of fuel.
     */
    abstract void burnFuel(double delta);

    /**
     * @return How many megawatts of power it makes when on.
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
     * @param amount Adds this much to the total fuel available.
     */
    public static void addFuel(double amount){
        totalFuelAvailable += amount;
    }

    /**
     * @return Class of the generator, its production speed, and the total power it's currently making.
     */
    @Override
    public String toString(){
        String name = this.getClass().getName();

        return name + ", Production Speed: " + overclockSpeed + "x, Total Power: " + getTotalPower();
    }

}
