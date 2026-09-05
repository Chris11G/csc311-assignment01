package edu.farmingdale.csc311.fleet;

/**
 * Driver. This is the only class that prints a report.
 *
 * @author Christopher Gonzalez
 */
public class Main {

    /* ------------------------------------------------------------------
     * TODO-10     commit: TODO-10: build the fleet demo in Main
     *
     * Print exactly the output listed in the assignment handout. Break the
     * work into private static helper methods, one per section. A 120 line
     * main() loses points.
     *
     * 1. Build a Fleet named "Farmingdale Motor Pool" and add these five in
     *    this order. Every value matters.
     *
     *    Car   1HGCM82633A004352 Honda  Accord  2023 Blue   4 2.0 GASOLINE 15.8 4 doors
     *    Car   5YJ3E1EA7PF123456 Tesla  Model 3 2024 Red    4 0.0 ELECTRIC 75.0 4 doors
     *    Car   JTDKARFU2J3061234 Toyota Prius   2020 Silver 4 1.8 HYBRID   11.3 5 doors
     *    Truck 1FT8W3BT5MEC12345 Ford   F-350   2021 White  6 6.7 DIESEL   40.0 3500.0 kg
     *    Truck 3C6UR5DL9JG123456 Ram    2500    2019 Black  4 6.4 GASOLINE 31.0 1800.0 kg
     *
     * 2. Inventory: loop over sortedByYear() and println each one. Declare
     *    the loop variable as Vehicle, not Car and not Truck. One loop
     *    prints both kinds. No instanceof anywhere in this file.
     *
     * 3. Sound check: loop over sortedByYear() again with the loop variable
     *    declared as Honkable and call honk(). Then find the Accord with
     *    findByVin and honk 3 times.
     *
     * 4. Report, using these exact printf formats:
     *        "%-20s: %d%n"                     vehicle count
     *        "%-20s: %.1f L%n"                 average engine size
     *        "%-20s: %d %s %s (%.1f mi)%n"     longest range
     *        "  %-9s: %d%n"                    one line per fuel
     *    Get the fuel lines by looping over FuelType.values() and calling
     *    countWithFuelType.
     *
     * 5. Guard rails, first three lines with "%-23s: %s%n":
     *        a. add the Accord a second time, print what add() returned
     *        b. removeByVin the Prius, print what it returned
     *        c. print size() afterwards
     *    Then three separate try/catch blocks, each catching
     *    IllegalArgumentException and printing "Caught: " + e.getMessage():
     *        d. build a Car with fuel ELECTRIC and engine size 2.0
     *        e. FuelType.fromLabel("Steam")
     *        f. honk(0) on any vehicle
     *    Catch IllegalArgumentException, not Exception. No empty catch.
     * ------------------------------------------------------------------ */

    public static void main(String[] args) {

        // Build the fleet and add all five required vehicles.
        Fleet fleet = buildFleet();

        // Print the program title using the fleet's name.
        System.out.println("=== " + fleet.getName() + " ===");
        System.out.println();

        // Print each required section of the program.
        printInventory(fleet);
        printSoundCheck(fleet);
        printFleetReport(fleet);
        printGuardRails(fleet);
    }

    /**
     * Creates the Farmingdale Motor Pool and adds the five
     * vehicles specified in the assignment.
     */
    private static Fleet buildFleet() {

        // Create the fleet with the required name.
        Fleet fleet = new Fleet("Farmingdale Motor Pool");

        // Add the Honda Accord.
        fleet.add(new Car(
                "1HGCM82633A004352",
                "Honda",
                "Accord",
                2023,
                "Blue",
                4,
                2.0,
                FuelType.GASOLINE,
                15.8,
                4
        ));

        // Add the Tesla Model 3.
        fleet.add(new Car(
                "5YJ3E1EA7PF123456",
                "Tesla",
                "Model 3",
                2024,
                "Red",
                4,
                0.0,
                FuelType.ELECTRIC,
                75.0,
                4
        ));

        // Add the Toyota Prius.
        fleet.add(new Car(
                "JTDKARFU2J3061234",
                "Toyota",
                "Prius",
                2020,
                "Silver",
                4,
                1.8,
                FuelType.HYBRID,
                11.3,
                5
        ));

        // Add the Ford F-350.
        fleet.add(new Truck(
                "1FT8W3BT5MEC12345",
                "Ford",
                "F-350",
                2021,
                "White",
                6,
                6.7,
                FuelType.DIESEL,
                40.0,
                3500.0
        ));

        // Add the Ram 2500.
        fleet.add(new Truck(
                "3C6UR5DL9JG123456",
                "Ram",
                "2500",
                2019,
                "Black",
                4,
                6.4,
                FuelType.GASOLINE,
                31.0,
                1800.0
        ));

        return fleet;
    }

    /**
     * Prints all vehicles sorted from oldest to newest.
     * Vehicles with the same year are sorted by make.
     */
    private static void printInventory(Fleet fleet) {

        System.out.println(
                "-- Inventory (" + fleet.size()
                        + " vehicles, sorted by year then make) --"
        );

        // Vehicle is used as the loop variable so both Cars and Trucks
        // can be handled by the exact same loop.
        for (Vehicle vehicle : fleet.sortedByYear()) {
            System.out.println(vehicle);
        }

        System.out.println();
    }

    /**
     * Demonstrates polymorphism by honking every vehicle.
     */
    private static void printSoundCheck(Fleet fleet) {

        System.out.println("-- Sound check --");

        // Each object decides how honk() behaves.
        // Cars honk once while Trucks override honk() and honk twice.
        for (Honkable vehicle : fleet.sortedByYear()) {
            vehicle.honk();
        }

        System.out.println();
        System.out.println("-- Impatient Accord --");

        // Find the Accord by VIN.
        Vehicle accord = fleet.findByVin("1HGCM82633A004352");

        // Make the Accord honk exactly three times.
        accord.honk(3);

        System.out.println();
    }

    /**
     * Prints statistics about the current fleet.
     */
    private static void printFleetReport(Fleet fleet) {

        System.out.println("-- Fleet report --");

        // Print the total number of vehicles.
        System.out.printf(
                "%-20s: %d%n",
                "Vehicles",
                fleet.size()
        );

        // Print the average engine size.
        // Electric vehicles are excluded by averageEngineSize().
        System.out.printf(
                "%-20s: %.1f L%n",
                "Average engine size",
                fleet.averageEngineSize()
        );

        // Find the vehicle with the greatest calculated range.
        Vehicle longest = fleet.longestRange();

        // Print the longest-range vehicle using the required format.
        System.out.printf(
                "%-20s: %d %s %s (%.1f mi)%n",
                "Longest range",
                longest.getYear(),
                longest.getMake(),
                longest.getModel(),
                longest.rangeInMiles()
        );

        System.out.println("Fuel mix:");

        // Loop through every FuelType instead of writing four separate lines.
        for (FuelType fuel : FuelType.values()) {
            System.out.printf(
                    "  %-9s: %d%n",
                    fuel.getLabel(),
                    fleet.countWithFuelType(fuel)
            );
        }

        System.out.println();
    }

    /**
     * Demonstrates duplicate protection, removal, and exception handling.
     */
    private static void printGuardRails(Fleet fleet) {

        System.out.println("-- Guard rails --");

        // Find the Accord that is already stored in the fleet.
        Vehicle accord = fleet.findByVin("1HGCM82633A004352");

        // Trying to add the Accord again should fail.
        // add() returns false, so ! converts that into true for
        // the "Duplicate VIN rejected" output shown in the handout.
        boolean duplicateRejected = !fleet.add(accord);

        System.out.printf(
                "%-23s: %s%n",
                "Duplicate VIN rejected",
                duplicateRejected
        );

        // Remove the Prius by its VIN.
        boolean removedPrius =
                fleet.removeByVin("JTDKARFU2J3061234");

        System.out.printf(
                "%-23s: %s%n",
                "Removed the Prius",
                removedPrius
        );

        // The fleet should now contain four vehicles.
        System.out.printf(
                "%-23s: %s%n",
                "Fleet size now",
                fleet.size()
        );

        // Test an invalid electric car.
        // Electric vehicles must have an engine size of exactly 0.0.
        try {
            new Car(
                    "1HGCM82633A004352",
                    "Test",
                    "Electric",
                    2024,
                    "Blue",
                    4,
                    2.0,
                    FuelType.ELECTRIC,
                    75.0,
                    4
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // Test looking up a fuel type that does not exist.
        try {
            FuelType.fromLabel("Steam");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // Test an invalid honk count.
        // honk(int) requires at least one honk.
        try {
            accord.honk(0);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}