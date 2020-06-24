/**
 * one type of power up.
 * Active shield for 5000 milliseconds after getting contact with player 
 */
public class ShieldPowerUp extends PowerUp{
	public final static String SHIELD_POWER_UP_PATH = "assets/shield-powerup.png";
			
	public ShieldPowerUp(float x, float y) {
		super(SHIELD_POWER_UP_PATH,x,y);

	}
}
