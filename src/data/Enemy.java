package data;

import java.applet.Applet;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy{
	private static final int Y = 330;
	private static final int WITH = 60;
	private static final int HEIGHT = 10;
	int x = 0;
	private boolean paint = true;


	int xa = 1;

	private Game game;

	public Enemy(Game game,Ball ball) {
		this.game= game;
		ball.addEnemysACTIVOS();
		
	}

	public void move() {
		if (x + xa > 0 && x + xa < game.getWidth()-WITH)
			x = x + xa;
	}

	public void paint(Graphics2D g) {
		if(paint){
			g.fillRoundRect(game.getWidth()/2, game.getHeight()/2, 25, 50,5,50);
		}
	}


	public Rectangle getBounds() {
		if(paint){
			return new Rectangle(game.getWidth()/2, game.getHeight()/2, 50, 25);
		}
		else{
			return null;
		}
	}

	public int getTopY() {
		return Y - HEIGHT;
	}

	public boolean isPaint() {
		return paint;
	}

	public void setPaint(boolean paint) {
		this.paint = paint;
	}
}