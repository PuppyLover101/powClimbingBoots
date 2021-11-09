import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Camera;

public class CameraHandler extends Task {
    Main main;

    public CameraHandler(Main main) {
        super();
        super.name = "CameraHandler";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Camera.getZoom() != 0 || Camera.pitch() < 80
                && !Bank.opened();
    }

    @Override
    public void execute() {
        Main.CURRENT_TASK = "CameraHandler";
        System.out.println("Zoom: " + Camera.getZoom());
        System.out.println("Pitch: " + Camera.pitch());

        if (Camera.getZoom() != 0) {
            System.out.println("Moving zoom silder");
            Camera.moveZoomSlider(10);
        } else if (Camera.pitch() < 80) {
            System.out.println("Moving camera pitch");
            Camera.pitch(99);
        }
    }
}
