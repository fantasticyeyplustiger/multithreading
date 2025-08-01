public class FuelGenerator extends OneResourceGenerator {

    FuelGenerator(){
        this.defaultBurnTime = 2;
        this.defaultPowerGeneration = 450;
        this.unitsOfSpace = 12;
        this.fuelUsagePerBurnTime = 20;
        this.fuelType = "Fuel";
        FuelGenerator.totalFuelBeingBurnt += fuelUsagePerBurnTime;
    }
}
