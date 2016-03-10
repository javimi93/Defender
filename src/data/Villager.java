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
	private boolean moveVillager=false;


	/*
	 * Constructor que crea al enemigo y tiene por por parametro, el juego y la nave.
	 * Cuando se invoca, crea un emeigo y incrementa la variable enemysActivos.
	 */
	public Villager(Game game,Craft craft) {
		this.game = game;
	}
	/*
	 * Pinta cada invocacion el enemigo y los disparos suyos.
	 */
	public void paint(Graphics2D g,BufferedImage[] sprites) {
		if(moveVillager){
			//g.drawRect(X, Y, 45, 40); 
			g.drawImage(sprites[5], X, Y, 45, 40, null);
		}
		else{
			g.drawImage(sprites[5], game.getWidth()/2, 50, 45, 40, null);

		}
	}

	/*
	 * Devuelve un rectangulo que facilita la deteccion de colision con el enemigo.
	 */
	public Rectangle getBounds() {
		if(paint){
			if(moveVillager){
				return new Rectangle(X, Y, 45, 40);
			}
			else{
				return new Rectangle(game.getWidth()/2, 50, 45, 40);
			}
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
		moveVillager=true;
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