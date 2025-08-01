public class CoalGenerator extends TwoResourceGenerator {

    CoalGenerator(){
        this.defaultBurnTime = 1;
        this.defaultPowerGeneration = 150;
        this.unitsOfSpace = 5;
        this.fuelUsagePerBurnTime = 12;
        this.otherFuelUsagePerBurnTime = 25;
        this.fuelType = "Coal";
        this.otherFuelType = "Water";
        CoalGenerator.totalFuelBeingBurnt += fuelUsagePerBurnTime;
    }
}
