package data;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class Ball {
	//Posiciones iniciales de la bola
	int xInit = 0;
	int yInit = 0;
	//Variables de cambio de la posición de la bola en el plano
	//xMovement es el desplazamiento de la bola sobre el eje x
	//yMovement es el desplazamiento de la bola sobre el eje y
	int xMovement = 0;
	int yMovement = 0;
	//private BufferedImage image;
	private static final int DIAMETER = 30;
	private static final int ADD= 3;
	private Game game;
	private int lastXa=1;
	private boolean paint=false;
	private Shoot shoot;
	private int shootsACTIVOS=0;
	private int enemysACTIVOS=0;
	private Vector<Shoot> shoots= new Vector();

	public Ball(Game game) {
		this.game= game;
		//TODO cargar imagen nave.
		/*try
        {
            this.image = ImageIO.read(new File("datos/sprite.jpg"));
        }
        catch (IOException e)
        {
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
			return game.enemy.getBounds().intersects(getBounds());
		}
		else{
			return false;
		}
	}

	public void paint(Graphics2D g) {
		//g.drawImage(image, x, y, 20, 20, null);
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
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			xMovement=ADD;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP){
			yMovement=-ADD;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN){
			yMovement=ADD;

		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			shoots.add(new Shoot(game,this));
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