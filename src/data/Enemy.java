package data;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Enemy{

	private boolean paint = true;
	private Game game;
	private int X = 0;
	private int Y = 0;
	private ShootEnemy shootEnemy ;

	public Enemy(Game game,Craft craft) {
		this.game= game;
		shootEnemy=new ShootEnemy(game,this,craft);
		craft.addEnemysACTIVOS();

	}

	public void paint(Graphics2D g,BufferedImage[] sprites) {
		if(paint){
			X = game.getWidth()/2;
			Y = game.getHeight()/2;
			//g.fillRoundRect(X, Y, 25, 50,5,50);
			g.drawImage(sprites[1], X, Y, 25, 20, null);
		}
		if(shootEnemy.isPaintShootEnemy()){
			shootEnemy.paint(g,sprites);
		}

	}


	public Rectangle getBounds() {
		if(paint){
			X = game.getWidth()/2;
			Y = game.getHeight()/2;
			return new Rectangle(X, Y, 25, 20);
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
		shootEnemy.setPaintShootEnemy(true);
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