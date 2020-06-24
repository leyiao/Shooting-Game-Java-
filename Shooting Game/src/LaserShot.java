import org.newdawn.slick.Input;
/**
 * subclass of Sprite represents space ship'laser shot 
 *
 */
public class LaserShot extends Sprite {
	public final static String SHOT_SPRITE_PATH = "assets/shot.png";
	public final float SHOT_SPEED = -3f;
	
	public LaserShot(float x, float y) {
		super(SHOT_SPRITE_PATH, x, y);
	}
	
	@Override
	public void update(Input input, int delta) {
		move(0, SHOT_SPEED);
		if (!onScreen()) {
			deactivate();
		}
	}
}
