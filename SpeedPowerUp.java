import java.awt.*;

public class SpeedPowerUp extends PowerUp {

    public SpeedPowerUp(){
        super();
        this.color = Color.BLUE;
        this.effectLifeTime = 190;
    }

    public void activate(){
        owner.affectedByPowerUp(effectLifeTime);
        owner.setSpeed();
    }
}
