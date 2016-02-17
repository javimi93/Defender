package data;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy {
	private static final int Y = 330;
	private static final int WITH = 60;
	private static final int HEIGHT = 10;
	int x = 0;
	int xa = 1;
	
	private Game game;

	public Enemy(Game game) {
		this.game= game;
	}

	public void move() {
		if (x + xa > 0 && x + xa < game.getWidth()-WITH)
			x = x + xa;
	}

	public void paint(Graphics2D g) {
		g.fillRect(x, 330, 60, 10);
	}

	
	
	public Rectangle getBounds() {
		return new Rectangle(x, Y, WITH, HEIGHT);
	}

	public int getTopY() {
		return Y - HEIGHT;
	}
}