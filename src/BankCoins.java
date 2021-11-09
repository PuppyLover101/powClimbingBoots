import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;

public class BankCoins extends Task {
    Main main;

    public BankCoins(Main main) {
        super();
        super.name = "BankCoins";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Bank.opened()
                && !Inventory.stream().name("Coins").first().valid();
    }

    @Override
    public void execute() {
        Main.CURRENT_TASK="BankCoins";
        Item coins_in_bank = Inventory.stream().id(Constants.CLIMBING_BOOTS).first();
        if (coins_in_bank.interact("Withdraw-All")) {
            System.out.println("Withdrew coins");
            Condition.wait(() -> Inventory.stream().name("Coins").first().valid(), 150, 30);
        }
    }
}
