package data;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy{

	private boolean paint = true;
	private Game game;
	private int X = 0;
	private int Y = 0;
	private ShootEnemy shootEnemy ;

	public Enemy(Game game,Ball ball) {
		this.game= game;
		shootEnemy=new ShootEnemy(game,this);
		ball.addEnemysACTIVOS();
		
	}

	public void paint(Graphics2D g) {
		if(paint){
			X = game.getWidth()/2;
			Y = game.getHeight()/2;
			g.fillRoundRect(X, Y, 25, 50,5,50);
			shootEnemy.paint(g);
		}
		else{
			shootEnemy.setXa(0);
		}
	}


	public Rectangle getBounds() {
		if(paint){
			X = game.getWidth()/2;
			Y = game.getHeight()/2;
			return new Rectangle(X, Y, 25, 50);
		}
		else{
			return null;
		}
	}


	public boolean isPaint() {
		return paint;
	}

	public void setPaint(boolean paint) {
		this.paint = paint;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		this.X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		this.Y = y;
	}

	public ShootEnemy getShootEnemy() {
		return shootEnemy;
	}
}