package edu.farmingdale.csc311.fleet;

/**
 * A passenger car: a Vehicle plus a door count.
 *
 * @author Christopher Gonzalez
 */
public class Car extends Vehicle {

    /* ------------------------------------------------------------------
     * TODO-06     commit: TODO-06: implement Car
     *
     * 1. Add a private int field doors.
     * 2. The super(...) call is written for you and has to stay first.
     *    After it, check doors (must be 2, 3, 4 or 5) and store it.
     * 3. Fill in getDoors() and setDoors(int) with the same check.
     * 4. category()      returns "Car"
     *    rangeInMiles()  getFuelCapacity() * getFuelType().getMilesPerUnit()
     *    hornSound()     returns "Beep beep!"
     * 5. toString() returns:
     *
     *      Car -> <what Vehicle.toString() gives>, doors=4, range=442.4 mi
     *
     *    Call super.toString() for the middle part. Do not retype the
     *    parent's format string. Use category() instead of the literal
     *    "Car". Both numbers print with one decimal.
     * ------------------------------------------------------------------ */

    // Stores the number of doors this car has.
    private int doors;

    public Car(String vin, String make, String model, int year, String color,
               int wheels, double engineSize, FuelType fuelType, double fuelCapacity, int doors) {

        // Calls the Vehicle constructor to initialize all inherited fields.
        // This must remain the first statement in the constructor.
        super(vin, make, model, year, color, wheels, engineSize, fuelType, fuelCapacity);

        // Uses the setter so the door validation is only written once.
        setDoors(doors);
    }

    /**
     * Returns the number of doors on this car.
     */
    public int getDoors() {
        return doors;
    }

    /**
     * Changes the number of doors.
     * A car can only have 2, 3, 4, or 5 doors.
     */
    public void setDoors(int doors) {

        // Reject any door count outside the allowed range.
        if (doors < 2 || doors > 5) {
            throw new IllegalArgumentException(
                    "doors has invalid value: [" + doors + "]");
        }

        // Store the valid door count.
        this.doors = doors;
    }

    /**
     * Returns the type/category of this vehicle.
     */
    @Override
    public String category() {
        return "Car";
    }

    /**
     * Calculates how many miles the car can travel on a full tank
     * or full battery.
     */
    @Override
    public double rangeInMiles() {

        // Range = fuel capacity multiplied by the fuel's miles per unit.
        return getFuelCapacity() * getFuelType().getMilesPerUnit();
    }

    /**
     * Returns the horn sound used by a Car.
     */
    @Override
    public String hornSound() {
        return "Beep beep!";
    }

    /**
     * Returns the Vehicle information along with the car-specific
     * door count and driving range.
     */
    @Override
    public String toString() {

        // Reuse Vehicle's toString() instead of repeating its format.
        // The range is displayed with one decimal place.
        return String.format(
                "%s -> %s, doors=%d, range=%.1f mi",
                category(),
                super.toString(),
                doors,
                rangeInMiles()
        );
    }
}