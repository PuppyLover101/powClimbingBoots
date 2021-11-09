import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class OpenBank extends Task {
    Main main;

    public OpenBank(Main main) {
        super();
        super.name = "OpenBank";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return (Constants.FEROX_TILE.distanceTo(Players.local()) < 10 || Constants.CASTLE_WARS_TILE.distanceTo(Players.local()) < 10)
                && (Inventory.isFull() || Main.restockDueling || Main.restockGames || !Inventory.stream().name("Coins").first().valid())
                && Movement.energyLevel() > 15
                && !Bank.opened();
    }

    @Override
    public void execute() {
        Main.CURRENT_TASK = "OpenBank";
        if (!Equipment.itemAt(Equipment.Slot.RING).name().startsWith("Ring of dueling") && !Main.restockDueling && (Game.tab() == Game.Tab.EQUIPMENT) && !Bank.opened()) {
            System.out.println("Restocking dueling! Doesn't exist!! | " + Equipment.itemAt(Equipment.Slot.RING).name());
            Main.restockDueling = true;
        }
        if (!Equipment.itemAt(Equipment.Slot.NECK).name().startsWith("Games") && !Main.restockGames && (Game.tab() == Game.Tab.EQUIPMENT) && !Bank.opened()) {
            System.out.println("Restocking games necklace! Doesn't exist!! | " + Equipment.itemAt(Equipment.Slot.NECK).name());
            Main.restockGames = true;
        }
        if (Bank.nearest().isRendered() && Bank.open()) {
            System.out.println("Opening bank");
            Condition.wait(Bank::opened, 150, 30);
        } else {
            System.out.println("Walking to bank");
            Movement.step(Bank.nearest());
        }
    }
}
