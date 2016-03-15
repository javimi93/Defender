package data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;


public class Craft {
	//Posiciones iniciales de la bola
	int xInit = 0;
	int yInit = 80;
	boolean firstTime=true;
	//Variables de cambio de la posición de la bola en el plano
	//xMovement es el desplazamiento de la bola sobre el eje x
	//yMovement es el desplazamiento de la bola sobre el eje y
	int xMovement = 0;
	int yMovement = 0;
	private BufferedImage image;
	//private int nKeyPressed =0;
	private boolean left=false;
	private boolean right=false;
	private boolean up=false;
	private boolean down=false;
	private boolean space=false;
	private static final int MOVEMENTSPEED= 4;
	//Tabla de sprites
	BufferedImage[] sprites;
	//Ancho y largo de los sprites
	private final int WIDTH = 65;
	private final int HEIGHT = 40;
	private Game game;
	private Villager villager;
	//Ultimo movimiento de la nave
	private int lastMovement=1;
	//Variable que sirve si se debe pintar el enemigo
	private boolean paint=false;
	//Disparos activos de la nave
	private int shootsACTIVOS=0;
	//Enemigos Activos
	private int enemysACTIVOS=0;
	boolean pickedVillager=false;
	ScoreBoard scoreBoard;
	Vector <Integer> xExplosion=new Vector<Integer>();
	Vector <Integer> yExplosion=new Vector<Integer>();
	//Vector que almacena todos los disparos de la nave activos
	private Vector<ShootCraft> shoots= new Vector<ShootCraft>();

	/*
	 * Constructor de la clase Craft que recibe el juego y los sprites por parametro.
	 * Y pone por defecto la imagen de la nave que se mueve hacia la derecha.
	 */
	public Craft(Game game,BufferedImage[] sprites,Villager villager, ScoreBoard scoreBoard) {
		this.game= game;
		this.sprites=sprites;
		image=sprites[68];
		this.villager=villager;
		this.scoreBoard=scoreBoard;
	}

	/*
	 * Metodo que sirve para mover la nave segun la tecla pulsada, a traves de la variable MOVEMENTSPEED.
	 */
	void move() {
		if(left){
			xMovement-=MOVEMENTSPEED;
		}
		if(right){
			xMovement+=MOVEMENTSPEED;
		}
		if(up){
			yMovement-=MOVEMENTSPEED;
		}
		if(down){
			yMovement+=MOVEMENTSPEED;
		}
		if(space){
			shoots.add(new ShootCraft(game,this));
			shootsACTIVOS++;
			/*Game.sound.stop();
			Game.sound.start("shoot");*/
		}
		if (xInit+xMovement  < 0){
			xMovement+=MOVEMENTSPEED;
		}
		if (xInit+xMovement > game.getWidth() - WIDTH){
			xMovement-=MOVEMENTSPEED;
		}
		if (yInit+yMovement < 80){
			yMovement+=MOVEMENTSPEED;
		}
		if (yInit+yMovement > game.getHeight() - HEIGHT){
			yMovement-=MOVEMENTSPEED;
		}
		//Si se produce una colision ente la nave y los disparos o el enemigo. Se acaba el juego.
		if (collision()){
			scoreBoard.decreaseVidas();
			game.gameOver();
		}
		//Si coge el aldeano
		if (pickVillager()){
			pickedVillager = true;
		}
		//Si se produce una colision entre un enemigo y un disparo, se elimina el disparo y el enemigo.
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

		xInit += xMovement;
		yInit += yMovement;

		if(xMovement!=0){
			lastMovement = xMovement;
		}

		xMovement=0;
		yMovement=0;
	}

	/*
	 * Metodo que detecta una colision entre un enemigo y la nave o entre un disparo del enemigo y la nave.
	 */
	private boolean collision() {
		if(enemysACTIVOS > 0){
			return Game.enemy.getBounds().intersects(getBounds()) || Game.enemy.getShootEnemy().getBounds().intersects(getBounds()) ;
		}
		else{
			return false;
		}
	}

	/*
	 * Metodo que detecta una colision entre la nave y el aldeano.
	 */
	private boolean pickVillager() {
		//if(enemysACTIVOS > 0){
		return Game.villager.getBounds().intersects(getBounds());
		//}
		//else{
		//	return false;
		//}
	}

	/*
	 * Metodo que pinta cada vez la nave en su posicion y sus disparos.
	 */
	public void paint(Graphics2D g) {
		g.drawImage(image, xInit, yInit, WIDTH, HEIGHT, null);
		//g.drawRect(xInit, yInit, WIDTH, HEIGHT-10); 
		if(pickedVillager && !villager.getBounds().intersects(new Rectangle(0,800, 1000, 200))){
			villager.setX(xInit);
			villager.setY(yInit+HEIGHT-10);
		}
		for(int i=0; i< shootsACTIVOS;i++){
			paint=shoots.get(i).paint(g,lastMovement);
			if(!paint){
				shoots.remove(i);
				shootsACTIVOS--;
			}
		}
	}

	/*
	 * Metodo que se invoca al pulsar una tecla y define el siguiente movimiento de la nave.
	 */
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_LEFT){
			left=true;
			//Si la nave no estaba moviendose ya hacia la derecha,
			//se actualiza el sprite
			if (!right){
				image=sprites[69];
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			right=true;
			//Si la nave no estaba moviendose ya hacia la izquierda,
			//se actualiza el sprite
			if (!left){
				image=sprites[68];
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP){
			//nKeyPressed++;
			up=true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN){
			//nKeyPressed++;
			down=true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			space=true;
		}
	}

	/*
	 * Metodo que se invoca al soltar una tecla.
	 */
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT){
			left=false;
			if (right){
				image=sprites[68];
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			right=false;
			if (left){
				image=sprites[69];
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP){
			up=false;
			//nKeyPressed--;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN){
			down=false;
			//nKeyPressed--;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			space=false;
		}
	}

	/*
	 * Devuelve un Rectangulo que rodea a la nave para facilitar la deteccion de colisiones.
	 */
	public Rectangle getBounds() {
		return new Rectangle(xInit, yInit, WIDTH, HEIGHT-10);
	}

	public void explosion(Graphics2D g){
		int inercia=1;
		double gravedad=0.1;
		int enfriamiento=3;
		int dx=5;
		int dy=5;
		int caosX=3;
		double dif=Math.PI/5;
		double ang=0;
		ang=ang+dif;
		double dxx=dx+inercia*Math.sin(ang)+Math.random()*4-2;
		double dyy=dy+inercia*Math.cos(ang)+Math.random()*4-2;
		int temp=80+(int)Math.round(Math.random()*40-20);
		temp=temp-enfriamiento;
		dx=(int) (dxx+(caosX*Math.random()-caosX/2));
		dy=(int) (dyy+gravedad);
		// Choque contra el suelo
		if(firstTime){
			xExplosion.add(0,(int)Math.round(xInit));
			yExplosion.add(0,(int)Math.round(yInit));
			xExplosion.add(1,(int)Math.round(xInit));
			yExplosion.add(1,(int)Math.round(yInit));
			xExplosion.add(2,(int)Math.round(xInit));
			yExplosion.add(2,(int)Math.round(yInit));
			xExplosion.add(3,(int)Math.round(xInit));
			yExplosion.add(3,(int)Math.round(yInit));
			xExplosion.add(4,(int)Math.round(xInit));
			yExplosion.add(4,(int)Math.round(yInit));
			xExplosion.add(5,(int)Math.round(xInit));
			yExplosion.add(5,(int)Math.round(yInit));
			xExplosion.add(6,(int)Math.round(xInit));
			yExplosion.add(6,(int)Math.round(yInit));
			xExplosion.add(7,(int)Math.round(xInit));
			yExplosion.add(7,(int)Math.round(yInit));
			firstTime=false;
			xInit=0;
			yInit=80;
		}
		else{
			xExplosion.set(0,(int)Math.round(xExplosion.get(0)+dx));
			yExplosion.set(0,(int)Math.round(yExplosion.get(0)+dy));
			xExplosion.set(1,(int)Math.round(xExplosion.get(1)-dx));
			yExplosion.set(1,(int)Math.round(yExplosion.get(1)-dy));
			xExplosion.set(2,(int)Math.round(xExplosion.get(2)+dx));
			yExplosion.set(2,(int)Math.round(yExplosion.get(2)-dy));
			xExplosion.set(3,(int)Math.round(xExplosion.get(3)-dx));
			yExplosion.set(3,(int)Math.round(yExplosion.get(3)+dy));
			xExplosion.set(4,(int)Math.round(xExplosion.get(4)-dx));
			yExplosion.set(4,(int)Math.round(yExplosion.get(4)));
			xExplosion.set(5,(int)Math.round(xExplosion.get(5)));
			yExplosion.set(5,(int)Math.round(yExplosion.get(5)+dy));
			xExplosion.set(6,(int)Math.round(xExplosion.get(6)+dx));
			yExplosion.set(6,(int)Math.round(yExplosion.get(6)));
			xExplosion.set(7,(int)Math.round(xExplosion.get(7)));
			yExplosion.set(7,(int)Math.round(yExplosion.get(7)-dy));
		}
		g.setColor(Color.YELLOW);
		if(yExplosion.get(0)>80){
			g.fillOval(xExplosion.get(0),yExplosion.get(0),10,10);
		}
		if(yExplosion.get(1)>80){
			g.fillOval(xExplosion.get(1),yExplosion.get(1),10,10);
		}
		if(yExplosion.get(2)>80){
			g.fillOval(xExplosion.get(2),yExplosion.get(2),10,10);
		}
		if(yExplosion.get(3)>80){
			g.fillOval(xExplosion.get(3),yExplosion.get(3),10,10);
		}
		g.fillOval(xExplosion.get(4),yExplosion.get(4),10,10);
		if(yExplosion.get(5)>80){
			g.fillOval(xExplosion.get(5),yExplosion.get(5),10,10);
		}
		g.fillOval(xExplosion.get(6),yExplosion.get(6),10,10);
		if(yExplosion.get(7)>80){
			g.fillOval(xExplosion.get(7),yExplosion.get(7),10,10);
		}
		
	}

	/*
	 * Devuelve la X inicial de la nave.
	 */
	public int getX(){
		return xInit;
	}

	/*
	 * Devuelve la Y inicial de la nave.
	 */
	public int getY(){
		return yInit;
	}

	/*
	 * Devuelve la XMovement.
	 */
	public int getXMovement(){
		return xMovement;
	}

	/*
	 * Devuelve el numero de enemigos activos.
	 */
	public int getEnemysACTIVOS() {
		return enemysACTIVOS;
	}

	/*
	 * Devuelve la X inicial de la nave.
	 */
	public void setEnemysACTIVOS(int enemysACTIVOS) {
		this.enemysACTIVOS = enemysACTIVOS;
	}
	public void addEnemysACTIVOS() {
		this.enemysACTIVOS++;
	}
	public void setRGBCraft(Color color){
		for (int i = 0; i < 65; i++) {
			for (int j = 0; j < 40; j++) {
				int rgbAux=image.getRGB(i, j);
				if(rgbAux!=0){
					image.setRGB(i, j, color.getRGB());
				}
			}
		}
	}

	/*public int getnKeyPressed() {
		return nKeyPressed;
	}

	public void setnKeyPressed(int nKeyPressed) {
		this.nKeyPressed = nKeyPressed;
	}*/


}