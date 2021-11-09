import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class GoToFerox extends Task {
    Main main;

    public GoToFerox(Main main) {
        super();
        super.name = "Stamina";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return (Inventory.isFull() || Main.restockGames || Main.restockDueling || !Inventory.stream().name("Coins").first().valid())
                && Constants.CASTLE_WARS_TILE.distanceTo(Players.local()) > 30
                && Constants.FEROX_TILE.distanceTo(Players.local()) > 30
                && Movement.energyLevel() <= 15;
    }

    @Override
    public void execute() {
        Main.CURRENT_TASK="GoToFerox";
        GameObject refreshment = Objects.stream().id(Constants.CHOCOLATE_FOUNTAIN).first();
        if (!refreshment.valid() || !refreshment.isRendered()) {
            System.out.println("Walking to refreshment_pool");
            Movement.walkTo(Constants.FEROX_TILE);
        } else if (!refreshment.inViewport()) {
            System.out.println("Moving camera to pool");
            Camera.turnTo(refreshment);
            Condition.wait(refreshment::inViewport, 150, 30);
        } else if (refreshment.interact("Drink", false)) {
            System.out.println("Drank to regenerate energy!");
            Condition.wait(() -> Movement.energyLevel() == 100, 150, 50);
        }
    }
}
