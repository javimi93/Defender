package data;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ShootEnemy {
	private static final int Y = 330;
	private static final int HEIGHT = 10;
	//private static final int DIAMETER = 50;
	private boolean paintShootEnemy=true;
	int x = 0;
	int xa=0;
	int xEnemy=0;
	int yEnemy=0;
	private Enemy enemy;
	private Craft ball;

	public ShootEnemy(Game game, Enemy enemy, Craft craft) {
		//this.game= game;
		this.enemy= enemy;
		this.ball=craft;
	}

	public void paint(Graphics2D g,BufferedImage[] sprites) {
		xEnemy= enemy.getX();
		yEnemy= enemy.getY();
		g.drawImage(sprites[38], xEnemy+xa, yEnemy-(10/2), 25, 20, null);
		//g.fillRect(xEnemy+xa,yEnemy+(DIAMETER/2), 10, 10);
		xa--;
		//direccion=false;
		if(xEnemy+xa <= - 10){
			xa=0;
			if(ball.getEnemysACTIVOS()==0){
				paintShootEnemy=false;
			}
		}
	}


	public Rectangle getBounds() {
		xEnemy= enemy.getX();
		yEnemy= enemy.getY();
		return new Rectangle(xEnemy+xa, yEnemy-(10/2), 10, 10);
	}

	public int getXa() {
		return xa;
	}

	public void setXa(int xa) {
		this.xa = xa;
	}

	public int getTopY() {
		return Y - HEIGHT;
	}

	public boolean isPaintShootEnemy() {
		return paintShootEnemy;
	}

	public void setPaintShootEnemy(boolean paintShootEnemy) {
		this.paintShootEnemy = paintShootEnemy;
	}
}