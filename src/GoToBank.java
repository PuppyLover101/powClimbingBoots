import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class GoToBank extends Task {
    Main main;

    public GoToBank(Main main) {
        super();
        super.name = "GoToBank";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return (Inventory.isFull() || Main.restockGames || Main.restockDueling || !Inventory.stream().name("Coins").first().valid() || Main.startMuling)
                && Constants.CASTLE_WARS_TILE.distanceTo(Players.local()) > 30
                && Constants.FEROX_TILE.distanceTo(Players.local()) > 30
                && Movement.energyLevel() > 15;
    }

    @Override
    public void execute() {
        Main.CURRENT_TASK="GoToBank";
        if (Game.tab(Game.Tab.EQUIPMENT) && Equipment.itemAt(Equipment.Slot.RING).interact("Castle Wars")) {
            System.out.println("Teleporting to Castle Wars");
            Condition.wait(() -> Constants.CASTLE_WARS_TILE.distanceTo(Players.local()) < 5, 150, 15);
        } else {
            System.out.println("Error when teleporting to Castle Wars");
        }
    }
}
