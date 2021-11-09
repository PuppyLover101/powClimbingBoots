import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.mobile.script.ScriptManager;

import java.util.List;
import java.util.stream.Collectors;

public class BankDueling extends Task {
    Main main;

    public BankDueling(Main main) {
        super();
        super.name = "BankDueling";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return (Main.restockDueling)
                && Bank.opened()
                && Inventory.stream().name("Coins").first().valid()
                && !Inventory.stream().id(Constants.CLIMBING_BOOTS).first().valid();
    }

    @Override
    public void execute() {
        Main.CURRENT_TASK = "BankDueling";
        Item rod_in_bank = Bank.stream().id(Constants.ROD).first();
        if (Inventory.isFull()) {
            List<Item> inventoryList = (Inventory.stream().list()).stream().distinct().collect(Collectors.toList());
            for (int i = 0; i < inventoryList.size(); i++) {
                int finalI = i;
                if (!inventoryList.get(finalI).name().contains("Coins") && Inventory.stream().name(inventoryList.get(finalI).name()).first().interact("Deposit-All")) {
                    System.out.println("Deposited all of " + inventoryList.get(i).name());
                    Condition.wait(() -> !Inventory.stream().name(inventoryList.get(finalI).name()).first().valid(), 150, 30);
                }
            }
        } else if (!Inventory.stream().id(Constants.ROD).first().valid()) {
            if (rod_in_bank.valid() && rod_in_bank.interact("Withdraw-1")) {
                System.out.println("Withdrawing rod_in_bank");
                Condition.wait(() -> Inventory.stream().id(Constants.ROD).first().valid(), 150, 30);
            } else if (!Bank.stream().id(Constants.ROD).first().valid()) {
                System.out.println("Out of dueling...");
                ScriptManager.INSTANCE.stop();
            }
        } else {
            if (Inventory.stream().id(Constants.ROD).first().interact("Wear")) {
                Main.ROD_BANKED = rod_in_bank.getStack();
                System.out.println("Wore RoD");
                Main.restockDueling = false;
            }
        }
    }
}
