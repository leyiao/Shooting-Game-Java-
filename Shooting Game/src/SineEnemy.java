import org.newdawn.slick.Input;
/**
* subclass of enemy represents sine enemy
* 
*/
public class SineEnemy extends Enemy{
	public final static String SINE_ENEMY_PATH = "assets/sine-enemy.png";
	public final static float SINE_ENEMY_SPEED = 0.15f;
	public final static int SINE_ENEMY_POINT = 100;
    
	public float delay;
    public float x;
    
    private int elapsedTime;
    
	public SineEnemy(float x, float delay) {
		super(SINE_ENEMY_PATH, x,  0 - delay*SINE_ENEMY_SPEED,SINE_ENEMY_POINT);
		this.delay = delay;
		this.x = x;
	}

	@Override
	public void update(Input input, int delta) {
		elapsedTime += delta;
		float dx = (float) (96 * Math.sin(2* Math.PI/1500 *(elapsedTime - delay)));
		move(x+dx,SINE_ENEMY_SPEED*delta);
	}
	public void move(float x, float y) {
		this.setX(x);
		this.setY(this.getY() + y);
		this.getBb().setX(this.getX());
		this.getBb().setY(this.getY());
	}
}

