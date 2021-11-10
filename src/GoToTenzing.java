import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class GoToTenzing extends Task {
    Main main;

    public GoToTenzing(Main main) {
        super();
        super.name = "GoToTenzing";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Constants.HOUSE_ENTRANCE_TILE.distanceTo(Players.local()) >= 5
                && !Constants.HOUSE_AREA.contains(Players.local())
                && !Inventory.isFull()
                && Inventory.stream().name("Coins").first().valid()
                && !Main.restockDueling
                && !Main.restockGames
                && Movement.energyLevel() > 15;
    }

    @Override
    public void execute() {
        Main.CURRENT_TASK = "GoToTenzing";
        if (Bank.opened()) {
            Bank.close();
            Condition.wait(() -> !Bank.opened(), 150, 50);
        } else {
            if (!Constants.BURTHOPE_AREA.contains(Players.local())) {
                if (Equipment.itemAt(Equipment.Slot.NECK).name().startsWith("Games")) {
                    if (Equipment.itemAt(Equipment.Slot.NECK).interact("Burthorpe", true)) {
                        System.out.println("Teleporting to Burthope");
                        Condition.wait(() -> Constants.BURTHOPE_TILE.distanceTo(Players.local()) < 30, 150, 50);
                    } else {
                        System.out.println("Error when teleporting to Burthope");
                    }
                } else {
                    System.out.println("PROBLEM with interacting with Games Necklace");
                }
            } else {
                Movement.walkTo(Constants.TENZING_TILE);
            }
        }
    }
}
