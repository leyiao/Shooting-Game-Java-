/**
 * a superclass of all type of enemy
 *
 */
public class Enemy extends Sprite {
	
	//rang of random a number for check whether enemy would become power up after being shoot
	private static final float MAX_PROBABILITY = 100;
	private static final float MIN_PROBABILITY = 0;
	//5% chance that enemy would
	private static final float POWER_CHANCE = 5;
	//equal chance for becoming one of two types
	private static final float TYPE = 50;
	private int enemy_point;
	
	public Enemy(String ENEMY_SPRITE_PATH, float x, float y, int point) {
		super(ENEMY_SPRITE_PATH, x,y);
		enemy_point = point;
	}


	@Override
	public void contactSprite(Sprite other) {
		if (other instanceof LaserShot) {
			deactivate();
			other.deactivate();
			World.getInstance().addScore(enemy_point);
			
			//test whether enemy could become power up
			float probability = (float) (Math.random()*(MAX_PROBABILITY-MIN_PROBABILITY)+MIN_PROBABILITY);
			if(probability< POWER_CHANCE){
			becomePowerUp();
			}
		}
	}
	public void becomePowerUp() {
		if (this instanceof Enemy) {
			 //which type of power up that enemy would become
				float type_prob = (float) (Math.random()*(MAX_PROBABILITY-MIN_PROBABILITY)+MIN_PROBABILITY);
			// equal chance for two types	
				if (type_prob < TYPE) {
					World.getInstance().generatePowerUp(getX(), getY(), new ShieldPowerUp(getX(), getY()));
				}else {
					World.getInstance().generatePowerUp(getX(), getY(), new SpeedPowerUp(getX(), getY()));
				}
				
			}
		}
	
}
