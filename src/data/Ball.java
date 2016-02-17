package data;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class Ball {
	int x = 0;
	int y = 0;
	int xa= 0;
	int ya= 0;
	//private BufferedImage image;
	private static final int DIAMETER = 30;
	private static final int ADD= 3;
	private Game game;
	private int lastXa=1;
	private boolean paint=false;
	private Shoot shoot;
	private int ACTIVOS=0;
	private Vector<Shoot> shoots= new Vector();

	public Ball(Game game) {
		this.game= game;
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
		if (x+xa  < 0){
			x+=ADD;
		}
		if (x+xa > game.getWidth() - DIAMETER){
			x-=ADD;
		}
		if (y+ya < 0){
			y+=ADD;
		}
		if (y+ya > game.getHeight() - DIAMETER){
			//game.gameOver();
			y-=ADD;
		}
		/*if (collision()){
			ya = -1;
			y = game.racquet.getTopY() - DIAMETER;
		}*/

		x = x + xa;
		y = y + ya;

		if(xa!=0){
			lastXa = xa;
		}

		xa=0;
		ya=0;
	}

	/*private boolean collision() {
	//	return game.racquet.getBounds().intersects(getBounds());
	}*/

	public void paint(Graphics2D g) {
		//g.drawImage(image, x, y, 20, 20, null);
		g.fillOval(x, y, DIAMETER, DIAMETER);
		for(int i=0; i< ACTIVOS;i++){
			paint=shoots.get(i).paint(g,lastXa);
			if(!paint){
				shoots.remove(i);
				ACTIVOS--;
			}
		}
	}


	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT){
			xa=-ADD;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			xa=ADD;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP){
			ya=-ADD;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN){
			ya=ADD;

		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			shoots.add(new Shoot(game,this));
			ACTIVOS++;
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, DIAMETER, DIAMETER);
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getXa(){
		return xa;
	}
}