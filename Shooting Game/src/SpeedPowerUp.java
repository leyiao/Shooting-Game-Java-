/**
 * one type of power up.
 * Increasing shooting speed of player for 5000 milliseconds after getting contact with player 
 */
public class SpeedPowerUp extends PowerUp{
	public final static String SPEED_POWER_UP_PATH = "assets/shotspeed-powerup.png";
			
	public SpeedPowerUp(float x, float y) {
		super(SPEED_POWER_UP_PATH,x,y);

	}
}
