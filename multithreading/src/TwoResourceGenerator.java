public class TwoResourceGenerator extends PowerGenerator {

    static double otherTotalFuelAvailable = 0.0;
    double otherFuelUsagePerBurnTime = 0.0;

    String otherFuelType;

    @Override
    void burnFuel(double delta) {

        if (!isRunning) { return; }

        currentTime += delta;

        if (!(currentTime >= getBurnTime())) { return; }

        currentTime = 0.0;

        if (totalFuelAvailable < fuelUsagePerBurnTime && otherTotalFuelAvailable < otherFuelUsagePerBurnTime){
            isRunning = false;
        }
        else{
            totalFuelAvailable -= fuelUsagePerBurnTime;
            otherTotalFuelAvailable -= otherFuelUsagePerBurnTime;
        }

    }
}
