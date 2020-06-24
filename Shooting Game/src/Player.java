import java.util.ArrayList;
import org.newdawn.slick.Input;

/**
 * Critical class of this game, represents the behavior of player(space ship). 
 *
 */
public class Player extends Sprite {
	public final static String PLAYER_SPRITE_PATH = "assets/spaceship.png";
	public final static int PLAYER_INITIAL_X = 512;
	public final static int PLAYER_INITIAL_Y = 688;
	public final static int LASER_INITIAL_SPEED = 350;
	public final static int LASER_SPEED_UP = 150;
	private final static int LIVE = 10;
	
	//Time for shield
	private int ProtectTime;
	//Protect time when lose life
	private final static int LOSE_LIFE_SHIELD = 3000;
    //Continues time when pick up any power up
	private final static int POWER_UP = 5000;
	//active when pick shot speed up.
	private boolean Speed_active = false;
	//time passed after picking shot speed up
	private int speed_up = 0;
	private int SHOOT_SEP = LASER_INITIAL_SPEED;
	
	public final static float SPEED = 0.5f;
	
	private static final float LIVE_X_OFFSET = 20;
	private static final float LIVE_Y_OFFSET = 696;
	private static final float LIVE_X_SEP = 40;
    
	private int press_time = 0;

	private ArrayList<Sprite> lives = new ArrayList<>();
	private Shield shield = new Shield();
	
	public Player() {
		super(PLAYER_SPRITE_PATH, PLAYER_INITIAL_X, PLAYER_INITIAL_Y);
		shield.setActivate(false);
		for (int i = 0; i < LIVE; i += 1) {
			lives.add(new Live(LIVE_X_OFFSET + i*LIVE_X_SEP, LIVE_Y_OFFSET));
		}
	}
	
	@Override
	public void update(Input input, int delta) {
		doMovement(input, delta);
		
		// do shooting
		if (input.isKeyDown(Input.KEY_SPACE)) {
			if(Speed_active) {
				SHOOT_SEP = LASER_SPEED_UP;
			}else {
				SHOOT_SEP = LASER_INITIAL_SPEED;
			}
			if(press_time == 0) {
				World.getInstance().addSprite(new LaserShot(getX(), getY()));
				}
			press_time += delta;
			if(press_time >= SHOOT_SEP) {
			World.getInstance().addSprite(new LaserShot(getX(), getY()));
			press_time = 0;
			}
		}else {
			press_time = 0;
		}
		if(Speed_active) {
			speed_up += delta;
			if(speed_up>=POWER_UP) {
				Speed_active = false;
				speed_up = 0;
			}
		}
		shield.update(getX(), getY(), delta, ProtectTime);
	}
	public void render() {
		
		getImage().drawCentered(this.getX(),this.getY());
		
		for (Sprite sprite : lives) {
			sprite.render();
		}if(shield.checkActivate()) {
			shield.render();
		}
	}
	@Override
	public void contactSprite(Sprite other) {

		if (other instanceof Enemy||other instanceof EnemyLaserShot) {
			if(lives.size() > 0 && !shield.checkActivate()) {
				lives.remove(lives.size()-1);
				shield.setActivate(true);
				ProtectTime = LOSE_LIFE_SHIELD;
			}
			if(lives.size() == 0 && !shield.checkActivate()) {
				System.exit(0);
			}
		}
		if(other instanceof ShieldPowerUp){
			shield.setActivate(true);
			ProtectTime = POWER_UP;
			other.deactivate();
		}
		if(other instanceof SpeedPowerUp){
			Speed_active = true;
			other.deactivate();
		}
	}	

		
		
	private void doMovement(Input input, int delta) {
		// handle horizontal movement
		float dx = 0;
		if (input.isKeyDown(Input.KEY_LEFT)) {
			dx -= SPEED;
		}
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			dx += SPEED;
		}

		// handle vertical movement
		float dy = 0;
		if (input.isKeyDown(Input.KEY_UP)) {
			dy -= SPEED;
		}
		if (input.isKeyDown(Input.KEY_DOWN)) {
			dy += SPEED;
		}
		
		move(dx * delta, dy * delta);
		clampToScreen();
	}

}
