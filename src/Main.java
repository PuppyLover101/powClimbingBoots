import org.powbot.api.script.AbstractScript;
import org.powbot.api.script.ScriptManifest;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;

import java.util.ArrayList;
import java.util.concurrent.Callable;

@ScriptManifest(
        name = "pClimbingBoots",
        description = "pClimbingBoots",
        version = "0.0.1"
)
public class Main extends AbstractScript {
    public static boolean restockDueling = false;
    public static boolean restockGames = false;
    public static boolean startMuling = false;
    public static long ROD_BANKED = 0;
    public static long GAMES_NECKLACE_BANKED = 0;
    public static String CURRENT_TASK = "";
    public static int CHATTED_COUNT = 0;
    private ArrayList<Task> taskList = new ArrayList();

    public Main() {
    }

    public static void main(String[] args) {
        new ScriptUploader().uploadAndStart("pClimbingBoots",
                "pClimbingBoots",
                "localhost:5559",
                false,
                true);
    }

    public void onStart() {

        Paint paint = PaintBuilder.newBuilder()
                .x(40)
                .y(45)
                .trackInventoryItem(Constants.CLIMBING_BOOTS, "Climbing Boots")
                .addString("Current Task: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Main.CURRENT_TASK);
                    }
                })
                .addString("Chatted #: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Main.CHATTED_COUNT);
                    }
                })
                .withoutDiscordWebhook()
                .build();
        addPaint(paint);

        taskList.add(new EquipmentChecker(this));
        taskList.add(new CameraHandler(this));
        taskList.add(new OpenBank(this));
        taskList.add(new BankBoots(this));
        taskList.add(new BankCoins(this));
        taskList.add(new BankDueling(this));
        taskList.add(new BankGamesNecklace(this));
        taskList.add(new GrabBoots(this));
        taskList.add(new GoToHouse(this));
        taskList.add(new GoToTenzing(this));
        taskList.add(new GoToBank(this));
        taskList.add(new GoToFerox(this));
    }

    @Override
    public void poll() {
        for (Task task : taskList) {
            if (task.activate()) {
                task.execute();
                if (ScriptManager.INSTANCE.isStopping()) {
                    break;
                }
            }
        }
    }
}
