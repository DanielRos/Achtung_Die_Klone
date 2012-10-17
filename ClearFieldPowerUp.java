import java.awt.*;

public class ClearFieldPowerUp extends PowerUp{
    public ClearFieldPowerUp() {
        super();
        this.color = Color.WHITE;
    }

    public void activate(){
        SnakePanel.getInstance().clearField();
    }
}
