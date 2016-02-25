package data;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Villager{

	private boolean paint = true;
	private Game game;
	private int X = 0;
	private int Y = 0;
	private ShootEnemy shootEnemy ;

	/*
	 * Constructor que crea al enemigo y tiene por por parametro, el juego y la nave.
	 * Cuando se invoca, crea un emeigo y incrementa la variable enemysActivos.
	 */
	public Villager(Game game,Craft craft) {
		this.game= game;
		this.X = 800;
		this.Y = 50;
	}

	/*
	 * Pinta cada invocacion el enemigo y los disparos suyos.
	 */
	public void paint(Graphics2D g,BufferedImage[] sprites) {
			
			//g.fillRoundRect(X, Y, 25, 50,5,50);
			g.drawImage(sprites[9], X, Y, 50, 40, null);
	}

	/*
	 * Devuelve un rectangulo que facilita la deteccion de colision con el enemigo.
	 */
	public Rectangle getBounds() {
		if(paint){
			return new Rectangle(X, Y, 50, 40);
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