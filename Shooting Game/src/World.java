import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;

/**
* class represents the game world.
* Handles drawing, input and rendering.
*/
public class World {
	public static final String BACKGROUND_PATH = "assets/space.png";
	private static final float BACKGROUND_SCROLL_SPEED = 0.2f;
	private static final float GAME_SPEED_UP = 5;
	
	public String EnemyName;
	public float x;
	public float y;
	public float delay;
	public Graphics g;
	public int score;
	private float backgroundOffset = 0;
	private Image background;
	
	private static World world;
	public static World getInstance() {
		if (world == null) {
			world = new World();
		}
		return world;
	}
	
	private ArrayList<Sprite> sprites = new ArrayList<>();
	public ArrayList<Sprite> lives = new ArrayList<>();
	private ArrayList<Sprite> powerups = new ArrayList<>();
	
	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}
	


	public World() {
		try {
			background = new Image(BACKGROUND_PATH);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		//Read wave text file
		 try (BufferedReader br =
		            new BufferedReader(new FileReader("assets/waves.txt"))) {
			 			String text;
			 		
			 			// Create sprites
		            while ((text = br.readLine()) != null) {
		            	    if(text.charAt(0)!= '#') {
		                 String waves[] = text.split(",");
		                 EnemyName = waves[0];
		                 x = Float.parseFloat(waves[1]);
		                 delay = Float.parseFloat(waves[2]);
		                 if(EnemyName.equals("BasicEnemy")) {
		                	     addSprite(new BasicEnemy(x,delay));
		                 }if(EnemyName.equals("SineEnemy")) {
		                	 	addSprite(new SineEnemy(x,delay));
		                 }if(EnemyName.equals("BasicShooter")) {
		                	 	addSprite(new BasicShooter(x,delay));
		                 }if(EnemyName.equals("Boss")) {
		                	 	addSprite(new Boss(x,delay));
		                 }
		            }}}catch (Exception e) {
		                e.printStackTrace();
		            }
		 
		
		sprites.add(new Player());
		
		world = this;
	}
	
	public void update(Input input, int delta) {
		// Update all sprites
		for (int i = 0; i < sprites.size(); ++i) {
			sprites.get(i).update(input, delta);
		}
		// Handle collisions
		for (Sprite sprite : sprites) {
			for (Sprite other : sprites) {
				if (sprite != other && sprite.getBoundingBox().intersects(other.getBoundingBox())) {
					sprite.contactSprite(other);
				}
			}
		}
		for (int index=0; index<powerups.size(); index++) {
			addSprite(powerups.get(index));
			powerups.remove(index);
		}
		for (int i = 0; i < lives.size(); ++i) {
			if (lives.get(i).getActive() == false) {
				lives.remove(i);
				// decrement counter to make sure we don't miss any
				--i;
			}
		}
		// Clean up inactive sprites
		for (int i1 = 0; i1 < sprites.size(); ++i1) {
			
			//when the Boss has been defeated, game ended
			if (sprites.get(i1) instanceof Boss && sprites.get(i1).getActive() == false) {
				System.exit(0);
			}
			if (sprites.get(i1).getActive() == false) {
				sprites.remove(i1);
				// decrement counter to make sure we don't miss any
				--i1;
			}
		}

		
		//Speed the game five time when press s key.
		if(input.isKeyDown(Input.KEY_S)) {
			backgroundOffset += BACKGROUND_SCROLL_SPEED * delta*GAME_SPEED_UP;
		}else {
		backgroundOffset += BACKGROUND_SCROLL_SPEED * delta;
		}
		backgroundOffset = backgroundOffset % background.getHeight();
	}
	
	public void render(Graphics g){
		// Tile the background image
		for (int i = 0; i < App.SCREEN_WIDTH; i += background.getWidth()) {
			for (int j = -background.getHeight() + (int)backgroundOffset; j < App.SCREEN_HEIGHT; j += background.getHeight()) {
				background.draw(i, j);
			}
		}
		// Draw all sprites
		for (Sprite sprite : sprites) {
			sprite.render();
		}	
		
		for (Sprite sprite : powerups) {
			sprite.render();
		}
		g.drawString("Score : "+ score, 20, 738);
	}

	public void addScore(int score) {
		this.score += score;
	}
	public void generatePowerUp(float x, float y, Sprite powerup) {
		powerups.add(powerup);
	}
}
