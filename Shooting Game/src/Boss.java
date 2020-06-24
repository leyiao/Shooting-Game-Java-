import org.newdawn.slick.Input;
/**
 * subclass of enemy represents boss
 *
 */
public class Boss extends Enemy{
	public final static String BOSS_PATH = "assets/boss.png";
	public final static float BOSS_Y_SPEED = 0.05f;
	public final static float BOSS_X_SPEED_1 = 0.2f;
	public final static float BOSS_X_SPEED_2 = 0.1f;
	public final static int BOSS_POINT = 5000;
	
	private final static float MIN_X_COORDINATE = 128;
    private final static float MAX_X_COORDINATE = 869;
    private final static float DEAD_TIME = 60;
    private final static float FIRST_PERIOD = 5000;
    private final static float SECOND_PERIOD = 2000;
    private final static float THIRD_PERIOD = 3000;
    
	private int elapsedTime = 0;
	private int WaiteTime = 0;
    private int hit_time = 0;
    
	private boolean stage_1 = false;
	private boolean stage_2 = false;
	private boolean stage_3 = false;
	private boolean random_status = true;
	private float current_random_x = 0;
	private int stage_3_time = 0;
	private int shotTime;
	private final static int SHOOT_SEP = 200;
	
	
	public Boss(float x, float delay) {
		super(BOSS_PATH, x,  0 - delay*BOSS_Y_SPEED, BOSS_POINT);
	}

	/**
	 * Update the position of boss enemy since it can move horizontally 
	 * after reach y-coordinate of 72.
	 * start and end each stage according to time passed.
	 * 
	 */
	@Override
	public void update(Input input, int delta) {
		if(getY()<=72) {
			move(0,BOSS_Y_SPEED*delta);
		}else{
			if(elapsedTime >= FIRST_PERIOD && random_status) {
				current_random_x = (float) (Math.random()*MAX_X_COORDINATE+MIN_X_COORDINATE);
				stage_1 = true;
			} 
			Stage_1(delta);
			Stage_2(delta);
			Stage_3(delta);
			elapsedTime += delta;
		}
	}
	/**
	 * Boss would die after be shoot 60 times
	 * 
	 */
	@Override
	public void contactSprite(Sprite other) {
		if (other instanceof LaserShot) {
			if(hit_time < DEAD_TIME) {
				hit_time ++;
			}else {
			deactivate();
			}
			other.deactivate();
		}
	}
	/**
	 * random choose and move to a x-coordinate 
	 * 
	 */
	private void Stage_1(int delta) {
		if (!stage_1) {
			return;
		}
		random_status = false;
		if(getX() > current_random_x) {
			move(-(BOSS_X_SPEED_1*delta),0);
		}
		if(getX() < current_random_x) {
			move(BOSS_X_SPEED_1*delta,0);
		}
		float diff = Math.abs(getX()-current_random_x); 
		if(diff < 1) {
			stage_2 = true;
			stage_1 = false;
			current_random_x = 0;
		}
		
	}
	
	/**
	 * Waiting for 2000 milliseconds after reach that random position
	 * 
	 */
	private void Stage_2(int delta) {
		if (!stage_2) {
			return;
		}
		WaiteTime += delta;
		if(WaiteTime >= SECOND_PERIOD) {
			stage_3 = true;
			stage_2 = false;
			WaiteTime = 0;
			current_random_x = (float) (Math.random()*MAX_X_COORDINATE+MIN_X_COORDINATE);
		}
	}
	
	private void Stage_3(int delta) {
		if (!stage_3) {
			return;
		}
		if(shotTime == 0) {
			this.shot();
		}
		shotTime += delta;
		if(shotTime >= SHOOT_SEP) {
			this.shot();
			shotTime = 0;
		}
		stage_3_time += delta;
		if(getX() > current_random_x) {
			move(-(BOSS_X_SPEED_2*delta),0);
		}
		if(getX()< current_random_x) {
			move(BOSS_X_SPEED_2*delta,0);
		}
		if(stage_3_time>= THIRD_PERIOD) {
			stage_3 = false;
			stage_3_time = 0;
			elapsedTime = 0;
			random_status = true;
		}
	}
	
	/**
	 * Generate four shot each time
	 */
	private void shot() {
		World.getInstance().addSprite(new EnemyLaserShot(getX()-97, getY()));
		World.getInstance().addSprite(new EnemyLaserShot(getX()+97, getY()));
		World.getInstance().addSprite(new EnemyLaserShot(getX()-74, getY()));
		World.getInstance().addSprite(new EnemyLaserShot(getX()+74, getY()));
	}
}

