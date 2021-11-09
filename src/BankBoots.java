import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.mobile.script.ScriptManager;

import java.util.List;
import java.util.stream.Collectors;

public class BankBoots extends Task {
    Main main;

    public BankBoots(Main main) {
        super();
        super.name = "BankBoots";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Bank.opened()
                && Inventory.stream().name("Coins").first().valid()
                && Inventory.stream().id(Constants.CLIMBING_BOOTS).first().valid();
    }

    @Override
    public void execute() {
        Main.CURRENT_TASK="BankBoots";

        //Bank.depositAllExcept() does provide true / false
        //values for successful returns properly in my experience

        List<Item> inventoryList = (Inventory.stream().list()).stream().distinct().collect(Collectors.toList());
        for (int i = 0; i < inventoryList.size(); i++) {
            int finalI = i;
            if (!inventoryList.get(finalI).name().contains("Coins") && Inventory.stream().name(inventoryList.get(finalI).name()).first().interact("Deposit-All")) {
                System.out.println("Deposited all of " + inventoryList.get(i).name());
                Condition.wait(() -> !Inventory.stream().name(inventoryList.get(finalI).name()).first().valid(), 150, 30);
            }
        }
    }
}
