import org.newdawn.slick.Input;

/**
 * subclass of enemy represents basic enemy 
 *
 */
public class BasicEnemy extends Enemy{
	public final static String BASIC_ENEMY_PATH = "assets/basic-enemy.png";
	public final static float BASIC_ENEMY_SPEED = 0.2f;
	public final static int BASCI_ENEMY_POINT = 50;

	public BasicEnemy(float x, float delay) {
		super(BASIC_ENEMY_PATH, x,  0 - delay*BASIC_ENEMY_SPEED, BASCI_ENEMY_POINT );
	}
	
	/**
	 * update the position of basic enemy since it can move vertically.
	 * 
	 */
	@Override
	public void update(Input input, int delta) {
		move(0,BASIC_ENEMY_SPEED*delta);
	}

}
