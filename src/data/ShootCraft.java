package data;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ShootCraft {
	private static final int DIAMETER = 30;
	private boolean direccion=true; //direccion del disparo, true = derecha, false = izquierda
	int x = 0;
	int xa=0;
	int xaBall = 1;
	int xBall=0;
	int yBall=0;
	Thread shoot;
	private boolean activo=false;
	private final int ADD=3;
	private Game game;
	private Craft ball;
	private int lastXa2=0;

	public ShootCraft(Game game, Craft craft) {
		this.game= game;
		this.ball= craft;
		xBall= craft.getX();
		yBall= craft.getY();
	}

	public boolean paint(Graphics2D g,int lastXa,BufferedImage[] sprites) {
		if(!activo){
			activo=true;
			xaBall= ball.getXa();
			if(lastXa2 != lastXa){
				lastXa2=lastXa;
			}
		}

		if(xaBall == 0){
			if(lastXa2 > 0){
				g.drawImage(sprites[62], xBall+xa+50, yBall+(60/2), 10, 10, null);
				//g.fillRect(xBall+xa+DIAMETER,yBall+(DIAMETER/2), 10, 10);
				xa+=ADD;
				direccion=true;
				if(xBall+xa+DIAMETER <= game.getWidth() - 10){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				g.drawImage(sprites[62], xBall+xa, yBall+(60/2), 10, 10, null);
				//g.fillRect(xBall+xa,yBall+(DIAMETER/2), 10, 10);
				xa-=ADD;
				direccion=false;
				if(xBall+xa >= - 10){
					return true;
				}
				else{
					return false;
				}
			}
		}
		else{
			if(xaBall > 0 ){
				g.drawImage(sprites[62], xBall+xa+50, yBall+(60/2), 10, 10, null);
				//g.fillRect(xBall+xa+DIAMETER,yBall+(DIAMETER/2), 10, 10);
				xa+=ADD;
				direccion=true;
				if(xBall+xa+DIAMETER <= game.getWidth() - 10){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				g.drawImage(sprites[62], xBall+xa, yBall+(60/2), 10, 10, null);
				//g.fillRect(xBall+xa,yBall+(DIAMETER/2), 10, 10);
				xa-=ADD;
				direccion=false;
				if(xBall+xa >= - 10){
					return true;
				}
				else{
					return false;
				}
			}
		}
	}


	public Rectangle getBounds() {
		if(direccion){
			return new Rectangle(xBall+xa+25, yBall+(20/2), 10, 10);
		}
		else{
			return new Rectangle(xBall+xa, yBall+(20/2), 10, 10);
		}
	}
}