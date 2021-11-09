import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;

public class GrabBoots extends Task {
    Main main;

    public GrabBoots(Main main) {
        super();
        super.name = "GrabBoots";
        this.main = main;
    }

    @Override
    public boolean activate() {

        return Constants.HOUSE_AREA.contains(Players.local())
                && !Inventory.isFull();
    }

    @Override
    public void execute() {
        Main.CURRENT_TASK = "GrabBoots";
        Npc tenzing = Npcs.stream().id(Constants.TENZING).first();

        //Component 162,34 is the chat log widget. It also shows up as
        // visible when chatting with an npc, and has for some reason
        // lowered the # of times the bot interacts with tenzing
        // to start chatting (as chatting dissapears randomly)

        //any tips on how to do this better?

        if (Widgets.component(162, 34).visible() || Chat.chatting()) {
            if (Chat.canContinue() && Chat.clickContinue()) {
                System.out.println("Continuing chat");
                Condition.sleep(Random.nextGaussian(420, 590, 94));
            } else if (Chat.continueChat("Can I buy some Climbing boots?")) {
                System.out.println("Initiating buy (1/2)");
                Condition.sleep(Random.nextGaussian(420, 593, 46));
            } else if (Chat.continueChat("OK, sounds good")) {
                System.out.println("Completing buy (2/2)");
                Condition.sleep(Random.nextGaussian(420, 593, 69));
            }
        } else if (!Widgets.component(162, 34).visible() && !Chat.chatting() && tenzing.interact("Talk-to", true)) {
            System.out.println("Interacting with tenzing");
            Main.CHATTED_COUNT = Main.CHATTED_COUNT + 1;
            Condition.wait(() -> Widgets.component(162, 34).visible() || Chat.chatting(), 150, 15);
        }
    }
}