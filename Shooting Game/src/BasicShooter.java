import org.newdawn.slick.Input;
/**
 * subclass of enemy represents basic shooter 
 *
 */
public class BasicShooter extends Enemy{
	public final static String BASIC_SHOOTER_PATH = "assets/basic-shooter.png";
	public final static float BASIC_SHOOTER_SPEED = 0.2f;
    
	private final static float MIN_Y_COORDINATE = 48;
    private final static float MAX_Y_COORDINATE = 464;
	
    private float shot_position;
    private int start_shot_time = 0;
	private final static int SHOOT_SEP = 3500;
	public final static int BASIC_SHOOTER_POINT = 200;
	
    public BasicShooter(float x, float delay) {
		super(BASIC_SHOOTER_PATH, x,  0 - delay*BASIC_SHOOTER_SPEED, BASIC_SHOOTER_POINT );
		shot_position = (float) (Math.random()*MAX_Y_COORDINATE+MIN_Y_COORDINATE);
	}
    
	/**
	 * update the position of basic shooter enemy since it can move vertically.
	 * Star shooting when reach a random position.
	 * Shot enemy laser shot every 3500 milliseconds.
	 */
	@Override
	public void update(Input input, int delta) {
		float y = getY();
		if(y< shot_position) {
			move(0,BASIC_SHOOTER_SPEED*delta);
		}else{
			if(start_shot_time== 0) {
				World.getInstance().addSprite(new EnemyLaserShot(getX(), getY()));
				}
			start_shot_time += delta;
			if(start_shot_time >= SHOOT_SEP) {
			World.getInstance().addSprite(new EnemyLaserShot(getX(), getY()));
			start_shot_time = 0;
			}
		}
	}
}


