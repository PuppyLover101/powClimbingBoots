import org.powbot.api.rt4.Game;

public class EquipmentChecker extends Task {
    Main main;

    public EquipmentChecker(Main main) {
        super();
        super.name = "EquipmentChecker";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return !(Game.tab() == Game.Tab.EQUIPMENT)
                && !Main.startMuling;
    }

    @Override
    public void execute() {
        Main.CURRENT_TASK="EquipmentChecker";
        if (Game.tab(Game.Tab.EQUIPMENT)) {
            System.out.println("Switching to equipment tab");
        }
    }
}
