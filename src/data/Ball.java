package data;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Ball {
	//Posiciones iniciales de la bola
	int xInit = 0;
	int yInit = 0;
	//Variables de cambio de la posición de la bola en el plano
	//xMovement es el desplazamiento de la bola sobre el eje x
	//yMovement es el desplazamiento de la bola sobre el eje y
	int xMovement = 0;
	int yMovement = 0;
	private BufferedImage image;
	private static final int DIAMETER = 30;
	private static final int ADD= 3;
	private BufferedImage[] sprites;
	private Game game;
	private int lastXa=1;
	private boolean paint=false;
	private int shootsACTIVOS=0;
	private int enemysACTIVOS=0;
	private Vector<ShootCraft> shoots= new Vector<ShootCraft>();

	public Ball(Game game) {
		this.game= game;
		/*BufferedImage bigImg;
		try {
			bigImg = ImageIO.read(new File("datos/sprite.png"));
		
		// The above line throws an checked IOException which must be caught.

		final int width = 25;
		final int height = 20;
		final int rows = 2;
		final int cols = 21;
		sprites = new BufferedImage[rows * cols];

		for (int i = 1; i < rows; i++)
		{
		    for (int j = 0; j < cols; j++)
		    {
		        sprites[(i * cols) + j] = bigImg.getSubimage(
		            j * width,
		            i * height,
		            width,
		            height
		        );
		    }
		}
		image=sprites[23];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

	void move() {
		if (xInit+xMovement  < 0){
			xInit+=ADD;
		}
		if (xInit+xMovement > game.getWidth() - DIAMETER){
			xInit-=ADD;
		}
		if (yInit+yMovement < 0){
			yInit+=ADD;
		}
		if (yInit+yMovement > game.getHeight() - DIAMETER){
			yInit-=ADD;
		}
		if (collision()){
			game.gameOver();
		}
		if(shootsACTIVOS > 0 && enemysACTIVOS > 0){
			for(int i=0; i< shootsACTIVOS;i++){
				if(enemysACTIVOS > 0 && game.enemy.getBounds().intersects(shoots.get(i).getBounds())){
					game.updatePuntuacion();
					enemysACTIVOS--;
					shootsACTIVOS--;
					game.enemy.setPaint(false);
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
			return game.enemy.getBounds().intersects(getBounds()) || game.enemy.getShootEnemy().getBounds().intersects(getBounds()) ;
		}
		else{
			return false;
		}
	}

	public void paint(Graphics2D g) {
		//g.drawImage(image, xInit, yInit, 20, 20, null);
		g.fillOval(xInit, yInit, DIAMETER, DIAMETER);
		for(int i=0; i< shootsACTIVOS;i++){
			paint=shoots.get(i).paint(g,lastXa);
			if(!paint){
				shoots.remove(i);
				shootsACTIVOS--;
			}
		}
	}


	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT){
			xMovement=-ADD;
			//image=sprites[25];
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			xMovement=ADD;
			//image=sprites[23];
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
		return new Rectangle(xInit, yInit, DIAMETER, DIAMETER);
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