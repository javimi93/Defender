package data;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class ShootCraft {
	private boolean direccion=true; //direccion del disparo, true = derecha, false = izquierda
	//Ancho y largo de los sprites
	private final int WIDTH = 10;
	private final int HEIGHT = 5;
	private int xMovement=0;
	private int xMovementCraft = 1;
	private int xCraft=0;
	private int yCraft=0;
	private boolean activo=false;
	private final int MOVEMENTSPEED=15;
	private Game game;
	private Craft ball;
	private int lastXMovement2=0;
	private ScoreBoard scoreBoard;
	/*
	 * Constructor de los disparos de la nave, que recibe por parametro el juego y la nave.
	 */
	public ShootCraft(Game game, Craft craft,ScoreBoard scoreBoard) {
		this.game= game;
		this.ball= craft;
		xCraft= craft.getX();
		yCraft= craft.getY();
		this.scoreBoard=scoreBoard;
		}

	/*
	 * Cada vez que se invoca se pinta el disparo con su respectivo movimiento.
	 */
	public boolean paint(Graphics2D g,int lastXMovement) {
		//Se utiliza lastXMovement para saber cual ha sido el ultimo movimiento de la nave
		//por si dispara la nave estando parada.
		if(!activo){
			activo=true;
			xMovementCraft= ball.getXMovement();
			if(lastXMovement2 != lastXMovement){
				lastXMovement2=lastXMovement;
			}
		}
		//Si la nave esta parada
		if(xMovementCraft == 0){
			if(lastXMovement2 > 0){
				//Hacia la derecha
				xMovement+=MOVEMENTSPEED;
				g.setColor(scoreBoard.getColor());
				g.fillRect(xCraft+xMovement+WIDTH*5, yCraft+HEIGHT*4, WIDTH, HEIGHT);
				//g.drawImage(image, xCraft+xMovement+WIDTH, yCraft, WIDTH, HEIGHT, null);
				direccion=true;
				//Si se ha llegado al limite de la pantalla
				if(xCraft+xMovement+WIDTH*5 <= game.getWidth()){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				//Hacia la izquierda
				xMovement-=MOVEMENTSPEED;
				g.setColor(scoreBoard.getColor());
				g.fillRect(xCraft+xMovement, yCraft+HEIGHT*4, WIDTH, HEIGHT);
				//g.drawImage(image, xCraft+xMovement-WIDTH/2, yCraft, WIDTH, HEIGHT, null);
				direccion=false;
				//Si se ha llegado al limite de la pantalla
				if(xCraft+xMovement >= - WIDTH){
					return true;
				}
				else{
					return false;
				}
			}
		}
		//Si la nave se esta moviendo
		else{
			if(xMovementCraft > 0 ){
				//Hacia la derecha
				xMovement+=MOVEMENTSPEED;
				g.setColor(scoreBoard.getColor());
				g.fillRect(xCraft+xMovement+WIDTH*5, yCraft+HEIGHT*4, WIDTH, HEIGHT);
				//g.drawImage(image, xCraft+xMovement+WIDTH, yCraft, WIDTH, HEIGHT, null);
				direccion=true;
				//Si se ha llegado al limite de la pantalla
				if(xCraft+xMovement+WIDTH*5 <= game.getWidth()){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				//Hacia la izquierda
				xMovement-=MOVEMENTSPEED;
				g.setColor(scoreBoard.getColor());
				g.fillRect(xCraft+xMovement, yCraft+HEIGHT*4, WIDTH, HEIGHT);
				//g.drawImage(image, xCraft+xMovement-WIDTH/2, yCraft, WIDTH, HEIGHT, null);
				direccion=false;
				//Si se ha llegado al limite de la pantalla
				if(xCraft+xMovement >= - WIDTH){
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
			return new Rectangle(xCraft+xMovement+WIDTH*5, yCraft+HEIGHT*4, WIDTH, HEIGHT);
		}
		else{
			return new Rectangle(xCraft+xMovement, yCraft+HEIGHT*4, WIDTH, HEIGHT);
		}
	}
}