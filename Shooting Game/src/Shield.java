/**
 * Prevent player from losing life in a shot time.
 * There are two way to active shield with different protect time.
 */

public class Shield extends Sprite {
	public final static String SHIELD_SPRITE_PATH = "assets/shield.png";
	private float x;
	private float y;

	public boolean active = false;
	private int ShieldTime = 0;
	
	public Shield() {
		super(SHIELD_SPRITE_PATH, 0, 0);

	}
	
	public void update(float x, float y,int delta, int WorkTime) {
		if(active) {
		this.x = x;
		this.y = y;
		ShieldTime += delta;
		if(ShieldTime >= WorkTime) {
			active = false;
			ShieldTime = 0;
		}
		}
		
	}
	public void render() {
		if(active) {
		getImage().drawCentered(x,y);
		}
		
    }
	public void setActivate(boolean active) { 
		this.active = active;
	}
	public boolean checkActivate() { 
		return active;
	}

}
