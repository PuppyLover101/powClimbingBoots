import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class GoToHouse extends Task {
    Main main;

    public GoToHouse(Main main) {
        super();
        super.name = "EnterHouse";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return !Constants.HOUSE_AREA.contains(Players.local())
                && Constants.HOUSE_ENTRANCE_TILE.distanceTo(Players.local()) < 10;
    }

    @Override
    public void execute() {
        Main.CURRENT_TASK = "EnterHouse";
        if (Main.restockGames || Main.restockDueling) {
            Main.restockGames = false;
            Main.restockDueling = false;
        } else if (Objects.stream().filter(item -> item.actions().contains("Open")).id(3725).first().interact("Open", true)) {
            System.out.println("Opening gate");
            Condition.wait(() -> Objects.stream().filter(item -> item.actions().contains("Close")).id(3725).first().valid(), 150, 15);
        } else if (Objects.stream().filter(item -> item.actions().contains("Open")).id(3745).first().interact("Open", true)) {
            System.out.println("Opening door to get to the nig");
            Condition.wait(() -> Constants.HOUSE_AREA.contains(Players.local()), 150, 15);
        }
    }
}
