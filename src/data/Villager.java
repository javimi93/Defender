package data;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Villager{

	private boolean paint = true;
	private Game game;
	private int X = 800;
	private int Y = 800;
	private ShootEnemy shootEnemy ;
	private int addX=0;
	private int addY=0;
	//Cuenta para que pase un intervalo de tiempo entre movimiento y movimiento del aldeano para la caida
	private int countFalling=10;
	//Cuenta para que pase un intervalo de tiempo entre movimiento y movimiento del aldeano
	private int countMovement=25;
	private boolean moveVillager=true;
	private boolean falling = false;
	private final int MOVEMENTSPEED = 5;
	double signo=0;
	/*
	 * Constructor que crea al enemigo y tiene por por parametro, el juego y la nave.
	 * Cuando se invoca, crea un emeigo y incrementa la variable enemysActivos.
	 */
	public Villager(Game game,Craft craft) {
		this.game = game;
		signo=Math.random()*1;
	}
	/*
	 * Pinta cada invocacion el enemigo y los disparos suyos.
	 */
	public void paint(Graphics2D g,BufferedImage[] sprites) {
		//El aldeano se mueve junto al enemigo
		if(moveVillager){
			//g.drawRect(X, Y, 45, 40); 
			if(Y > 800){
				g.drawImage(sprites[5], X, 800, 45, 40, null);
			}
			else{
				g.drawImage(sprites[5], X, Y, 45, 40, null);
			}
			addX=0;
			addY=0;
			countMovement=25;
			countFalling = 10;
		}
		//El aldeano cae
		else if (falling){
			countMovement=25;
			if(countFalling == 10){
				countFalling=0;
				if(Y- addY >=800){
					falling=false;
					countMovement=25;
					Y -= addY;
				}
				else{
					addY -= MOVEMENTSPEED;
				}
			}

			g.drawImage(sprites[5], X, Y - addY, 45, 40, null);
			countFalling++;
			addX=0;
		}
		//El aldeano se mueve en el suelo aleatoriamente
		else{
			countFalling = 10;
			addY=0;
			if(countMovement == 25){

				if(signo>0.5){
					addX+=MOVEMENTSPEED;
					countMovement=0;
				}
				else{
					addX-=MOVEMENTSPEED;
					countMovement=0;
				}
			}
			g.drawImage(sprites[5], X + addX, Y, 45, 40, null);
			countMovement++;


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
			else if(falling){
				return new Rectangle(X, Y - addY, 45, 40);
			}
			else{
				return new Rectangle(X + addX, Y, 45, 40);
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

	public void setPicked(boolean picked){
		moveVillager=picked;
	}

	public void setFalling(boolean fall){
		falling = fall;
	}
	public ShootEnemy getShootEnemy() {
		return shootEnemy;
	}
}