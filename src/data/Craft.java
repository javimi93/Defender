package data;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Craft {
	//Posiciones iniciales de la bola
	int xInit = 0;
	int yInit = 0;
	//Variables de cambio de la posición de la bola en el plano
	//xMovement es el desplazamiento de la bola sobre el eje x
	//yMovement es el desplazamiento de la bola sobre el eje y
	int xMovement = 0;
	int yMovement = 0;
	private BufferedImage image;
	//private static final int DIAMETER = 30;
	private static final int ADD= 3;
	BufferedImage[] sprites;
	private final int WIDTH = 25;
	private final int HEIGHT = 20;
	private Game game;
	private int lastXa=1;
	private boolean paint=false;
	private int shootsACTIVOS=0;
	private int enemysACTIVOS=0;
	private Vector<ShootCraft> shoots= new Vector<ShootCraft>();

	public Craft(Game game,BufferedImage[] sprites) {
		this.game= game;
		this.sprites=sprites;
 		image=sprites[23];
	}

	void move() {
 		if (xInit+xMovement  < 0){
 			xInit+=ADD;
 		}
 		if (xInit+xMovement > game.getWidth() - WIDTH){
 			xInit-=ADD;
 		}
 		if (yInit+yMovement < 0){
 			yInit+=ADD;
 		}
		if (yInit+yMovement > game.getHeight() - HEIGHT){
			yInit-=ADD;
		}
		if (collision()){
			game.gameOver();
		}
		if(shootsACTIVOS > 0 && enemysACTIVOS > 0){
			for(int i=0; i< shootsACTIVOS;i++){
				if(enemysACTIVOS > 0 && Game.enemy.getBounds().intersects(shoots.get(i).getBounds())){
					game.updatePuntuacion();
					enemysACTIVOS--;
					shootsACTIVOS--;
					Game.enemy.setPaint(false);
					shoots.remove(i);
				}
			}
		}

		xInit = xInit + xMovement;
		yInit = yInit + yMovement;

		if(xMovement!=0){
			lastXa = xMovement;
		}

		xMovement=0;
		yMovement=0;
	}

	private boolean collision() {
		if(enemysACTIVOS > 0){
			return Game.enemy.getBounds().intersects(getBounds()) || Game.enemy.getShootEnemy().getBounds().intersects(getBounds()) ;
		}
		else{
			return false;
		}
	}

	public void paint(Graphics2D g) {
		g.drawImage(image, xInit, yInit, WIDTH, HEIGHT, null);
		//g.fillOval(xInit, yInit, DIAMETER, DIAMETER);
		for(int i=0; i< shootsACTIVOS;i++){
			paint=shoots.get(i).paint(g,lastXa,sprites);
			if(!paint){
				shoots.remove(i);
				shootsACTIVOS--;
			}
		}
	}


	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT){
			xMovement=-ADD;
			image=sprites[26];
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			xMovement=ADD;
			image=sprites[23];
		}
		if (e.getKeyCode() == KeyEvent.VK_UP){
			yMovement=-ADD;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN){
			yMovement=ADD;

		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			shoots.add(new ShootCraft(game,this));
			shootsACTIVOS++;
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(xInit, yInit, WIDTH, HEIGHT);
	}

	public int getX(){
		return xInit;
	}

	public int getY(){
		return yInit;
	}

	public int getXa(){
		return xMovement;
	}

	public int getEnemysACTIVOS() {
		return enemysACTIVOS;
	}

	public void setEnemysACTIVOS(int enemysACTIVOS) {
		this.enemysACTIVOS = enemysACTIVOS;
	}
	public void addEnemysACTIVOS() {
		this.enemysACTIVOS++;
	}
}