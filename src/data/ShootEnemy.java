package data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ShootEnemy {
	private final int WIDTH = 15;
	private final int HEIGHT = 15;
	private boolean paintShootEnemy=true;
	private final int MOVEMENTSPEED=4;
	private int xMovement=0;
	private int xEnemy=0;
	private int yEnemy=0;
	private Enemy enemy;
	private Craft ball;

	/*
	 * Constructor del disparo enemigo que recibe por parametro al enemigo propietario y la nave.
	 */
	public ShootEnemy(Enemy enemy, Craft craft) {
		this.enemy= enemy;
		this.ball=craft;
	}

	/*
	 * Cada invocacion pinta el disparo del enemigo.
	 */
	public void paint(Graphics2D g,BufferedImage[] sprites) {
		xEnemy= enemy.getX();
		yEnemy= enemy.getY();
		//g.drawImage(sprites[3], xEnemy+xMovement-WIDTH/2, yEnemy, WIDTH, HEIGHT, null);
		g.setColor(Color.WHITE);
		g.fillOval(xEnemy+xMovement-WIDTH/2, yEnemy+HEIGHT, WIDTH, HEIGHT-5); 
		xMovement-=MOVEMENTSPEED;
		if(xEnemy+xMovement <= - HEIGHT){
			xMovement=0;
			if(ball.getEnemysACTIVOS()==0){
				paintShootEnemy=false;
			}
		}
	}

	/*
	 * Devuelvo el rectangulo que facilita la gestion de colisiones.
	 */
	public Rectangle getBounds() {
		xEnemy= enemy.getX();
		yEnemy= enemy.getY();
		return new Rectangle(xEnemy+xMovement-WIDTH/2, yEnemy+HEIGHT, WIDTH, HEIGHT-5);
	}

	public int getXMovement() {
		return xMovement;
	}

	public void setXmovement(int xMovement) {
		this.xMovement = xMovement;
	}

	public boolean isPaintShootEnemy() {
		return paintShootEnemy;
	}

	public void setPaintShootEnemy(boolean paintShootEnemy) {
		this.paintShootEnemy = paintShootEnemy;
	}
}