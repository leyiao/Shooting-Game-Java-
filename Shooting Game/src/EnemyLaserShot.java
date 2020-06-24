import org.newdawn.slick.Input;

/**
 * subclass of sprite represents enemy laser shot
 * different with space ship'laser shot 
 */
public class EnemyLaserShot extends Sprite {
	public final static String ENEMY_SHOT_PATH = "assets/enemy-shot.png";
	public final float ENEMY_SHOT_SPEED = 0.7f;
	
	public EnemyLaserShot(float x, float y) {
		super(ENEMY_SHOT_PATH, x, y);
	}
	
	@Override
	public void update(Input input, int delta) {
		move(0, ENEMY_SHOT_SPEED*delta);
		if (!onScreen()) {
			deactivate();
		}
	}
}
