package data;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ShootCraft {
	private boolean direccion=true; //direccion del disparo, true = derecha, false = izquierda
	//Ancho y largo de los sprites
	private final int WIDTH = 50;
	private final int HEIGHT = 40;
	private final int POSITIONSHOOT= 60;
	private int xMovement=0;
	private int xMovementBall = 1;
	private int xBall=0;
	private int yBall=0;
	private boolean activo=false;
	private final int MOVEMENTSPEED=5;
	private Game game;
	private Craft ball;
	private int lastXMovement2=0;
	private BufferedImage image;

	/*
	 * Constructor de los disparos de la nave, que recibe por parametro el juego y la nave.
	 */
	public ShootCraft(Game game, Craft craft,BufferedImage[] sprites) {
		this.game= game;
		this.ball= craft;
		xBall= craft.getX();
		yBall= craft.getY();
		this.image=sprites[58];
		}

	/*
	 * Cada vez que se invoca se pinta el disparo con su respectivo movimiento.
	 */
	public boolean paint(Graphics2D g,int lastXMovement) {
		//Se utiliza lastXMovement para saber cual ha sido el ultimo movimiento de la nave
		//por si dispara la nave estando parada.
		if(!activo){
			activo=true;
			xMovementBall= ball.getXMovement();
			if(lastXMovement2 != lastXMovement){
				lastXMovement2=lastXMovement;
			}
		}
		//Si la nave esta parada
		if(xMovementBall == 0){
			if(lastXMovement2 > 0){
				//Hacia la derecha
				xMovement+=MOVEMENTSPEED;
				g.drawImage(image, xBall+xMovement+WIDTH, yBall+(POSITIONSHOOT/2), WIDTH, HEIGHT, null);
				direccion=true;
				//Si se ha llegado al limite de la pantalla
				if(xBall+xMovement+WIDTH <= game.getWidth() - HEIGHT){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				//Hacia la izquierda
				xMovement-=MOVEMENTSPEED;
				g.drawImage(image, xBall+xMovement, yBall+(POSITIONSHOOT/2), WIDTH, HEIGHT, null);
				direccion=false;
				//Si se ha llegado al limite de la pantalla
				if(xBall+xMovement >= - HEIGHT){
					return true;
				}
				else{
					return false;
				}
			}
		}
		//Si la nave se esta moviendo
		else{
			if(xMovementBall > 0 ){
				//Hacia la derecha
				xMovement+=MOVEMENTSPEED;
				g.drawImage(image, xBall+xMovement+WIDTH, yBall+(POSITIONSHOOT/2), WIDTH, HEIGHT, null);
				direccion=true;
				//Si se ha llegado al limite de la pantalla
				if(xBall+xMovement+WIDTH <= game.getWidth() - HEIGHT){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				//Hacia la izquierda
				xMovement-=MOVEMENTSPEED;
				g.drawImage(image, xBall+xMovement, yBall+(POSITIONSHOOT/2), WIDTH, HEIGHT, null);
				direccion=false;
				//Si se ha llegado al limite de la pantalla
				if(xBall+xMovement >= - HEIGHT){
					return true;
				}
				else{
					return false;
				}
			}
		}
	}

	/*
	 * Devuelve rectangulo para facilitar gestion de colisiones.
	 */
	public Rectangle getBounds() {
		if(direccion){
			return new Rectangle(xBall+xMovement+WIDTH, yBall+(POSITIONSHOOT/2), WIDTH, HEIGHT);
		}
		else{
			return new Rectangle(xBall+xMovement, yBall+(POSITIONSHOOT/2), WIDTH, HEIGHT);
		}
	}
}