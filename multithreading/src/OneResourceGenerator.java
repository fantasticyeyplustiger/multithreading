public class OneResourceGenerator extends PowerGenerator{

    /**
     * Burns fuel if available and fuel isn't already burning.
     * @param delta Adds to currentTime. Starts the generator burning once getBurnTime() is over.
     */
    @Override
    void burnFuel(double delta) {

        if (!isRunning) { return; }

        currentTime += delta;

        if (!(currentTime >= getBurnTime())) { return; }

        currentTime = 0.0;

        if (totalFuelAvailable < fuelUsagePerBurnTime){
            isRunning = false;
        }
        else{
            totalFuelAvailable -= fuelUsagePerBurnTime;
        }
    }
}
