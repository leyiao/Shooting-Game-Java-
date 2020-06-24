import org.newdawn.slick.Input;
/**
 * a function of this game, allow player get power up
 * The initial position is the position of enemy which becomes power up.
 */
public class PowerUp extends Sprite{
	public final static float POWER_UP_SPEED = 0.1f;


	public PowerUp(String POWER_UP_PATH,float x, float y) {
		super(POWER_UP_PATH,x,y);

	}

	@Override
	public void update(Input input, int delta) {
		move(0,POWER_UP_SPEED*delta);
		if (!onScreen()) {
			deactivate();
		}
	}

}
