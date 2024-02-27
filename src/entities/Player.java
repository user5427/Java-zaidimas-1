package entities;

import static utils.Constants.PlayerConstants.GetSpriteAmount;
import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import editor.LevelData;
import main.Game;
import utils.LoadSave;

public class Player extends Entity{
	
	private int score = 0;
	private boolean alive, changeLevel; 
	
	private BufferedImage[][] animations;
	
	private int aniTick, aniIndex, aniSpeed = 15;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 1.0f * Game.SCALE;
	private LevelData levelData;
	private float xDrawOffset = 21 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;
	
	// Jumping / Gravity
	private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;
	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height); // pass to our entity class where it is stored
		loadAnimations();
		initHitbox(x, y, (int)(20*Game.SCALE), (int)(27*Game.SCALE));
		alive = true;
		score = 0;
		changeLevel = false;
	}
	
	public void update() {
		List<Integer> effects;
		updatePos();
		updateAnimationTick();
		setAnimation();
		effects = getAllEffects(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelData);
        for (Integer effect : effects) {
            switch (effect) {
                case 1:
                    score++;
                    break;
                case 2:
                    alive = false;
                    break;
                case 3:
                    changeLevel = true;
                    break;
                default:
                    break;
            }
        }
	}
	
	public void render(Graphics g, int lvlOffsetX, int lvlOffsetY) {
		g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffsetX, (int)(hitbox.y - yDrawOffset) - lvlOffsetY, width, height, null);
		//drawHitbox(g);
	}
	

	
	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}
		}
		
	}
	
	private void setAnimation() {
		int startAni = playerAction;
		
		if(moving)
			playerAction = RUNNING;
		else
			playerAction = IDLE;
		
		if (inAir && airSpeed < 0)
			playerAction = JUMP;
		else if (inAir && airSpeed > 0)
			playerAction = FALLING;
			
		if (attacking)
			playerAction = ATTACK_1;
		
		
		
		if (startAni != playerAction)
			resetAniTick();
		
	}
	
	private void resetAniTick() {
		// TODO Auto-generated method stub
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		
		moving = false;
		if (jump)
			jump();
		if (!inAir)
			if (!left && !right || (left && right))
				return;
		

		
		float xSpeed = 0;
		
		if (left) 
			xSpeed -= playerSpeed;
	    if (right) 
			xSpeed += playerSpeed;
	    
	    if (!inAir) {
	    	if(!isEntityOnFloor(hitbox, levelData.getLvlData2D())) {
	    		inAir = true;
	    	}
	    }
	
		
	    if (inAir) {
	    	if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData.getLvlData2D())) {
	    		hitbox.y += airSpeed;
	    		airSpeed += gravity;
	    		updateXPos(xSpeed);
	    	} else {
	    		hitbox.y = getEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
	    		if (airSpeed > 0) {
	    			resetInAir();
	    		} else
	    			airSpeed = fallSpeedAfterCollision;
	    		updateXPos(xSpeed);
	    	}
	    } else {
	    	updateXPos(xSpeed);
	    }
	    

	    moving = true;
		
	}
	
	private void jump() {
		if (inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;
		
	}

	private void resetInAir() {
		// TODO Auto-generated method stub
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPos(float xSpeed) {
		if (canMoveHere(hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData.getLvlData2D())) {
		hitbox.x += xSpeed;
		} else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
		}

	}

	private void loadAnimations() {

			BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			
			animations = new BufferedImage[9][6];
			// get all the images of the sprite into the array
			for (int j = 0; j < animations.length; j++) {
				for (int i = 0; i < animations[j].length; i++) {
					animations[j][i] = img.getSubimage(i*64, j*40, 64, 40);
				}
			}
	
		
		
	}
	
	public void loadLvlData(LevelData levelData) {
		this.levelData = levelData;
		if (!isEntityOnFloor(hitbox, levelData.getLvlData2D()))
			inAir = true;
	}
	
	public void resetDirBooleans(){
		left = false;
		right = false;
		up = false;
		down = false;
		jump = false;
	}
	
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public void resetPlayer() {
		// TODO Auto-generated method stub
		alive = true;
		changeLevel = false;
		resetDirBooleans();
		resetHitbox();
	}

	public int getScore() {
		return score;
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean isChangeLevel() {
		return changeLevel;
	}

    public void resetScore() {
		score = 0;
    }
}
