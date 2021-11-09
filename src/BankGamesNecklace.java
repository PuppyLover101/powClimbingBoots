import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.mobile.script.ScriptManager;

import java.util.List;
import java.util.stream.Collectors;

public class BankGamesNecklace extends Task {
    Main main;

    public BankGamesNecklace(Main main) {
        super();
        super.name = "BankGamesNecklace";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Main.restockGames
                && !Main.restockDueling
                && Bank.opened()
                && Inventory.stream().name("Coins").first().valid()
                && !Inventory.stream().id(Constants.CLIMBING_BOOTS).first().valid();
    }

    @Override
    public void execute() {
        Main.CURRENT_TASK="BankGamesNecklace";
        Item necklace_in_bank = Bank.stream().id(Constants.GAMES_NECKLACE).first();
        Item necklace_in_inventory = Inventory.stream().id(Constants.GAMES_NECKLACE).first();
        if (Inventory.isFull()) {
            List<Item> inventoryList = (Inventory.stream().list()).stream().distinct().collect(Collectors.toList());
            for (int i = 0; i < inventoryList.size(); i++) {
                int finalI = i;
                if (!inventoryList.get(finalI).name().contains("Coins") && Inventory.stream().name(inventoryList.get(finalI).name()).first().interact("Deposit-All")) {
                    System.out.println("Deposited all of " + inventoryList.get(i).name());
                    Condition.wait(() -> !Inventory.stream().name(inventoryList.get(finalI).name()).first().valid(), 150, 30);
                }
            }
        } else if (!necklace_in_inventory.valid()) {
            if (necklace_in_bank.valid() && necklace_in_bank.interact("Withdraw-1")) {
                System.out.println("Withdrawing necklace_in_bank");
                Condition.wait(() -> Inventory.stream().id(Constants.GAMES_NECKLACE).first().valid(), 150, 30);
            } else if (!necklace_in_bank.valid()) {
                System.out.println("Out of dueling...");
                ScriptManager.INSTANCE.stop();
            }
        } else {
            if (necklace_in_inventory.interact("Wear")) {
                Main.GAMES_NECKLACE_BANKED = necklace_in_bank.getStack();
                System.out.println("Wore necklace");
                Main.restockGames = false;
            }
        }

    }
}
