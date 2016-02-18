package data;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class ShootEnemy {
	private static final int Y = 330;
	private static final int WITH = 60;
	private static final int HEIGHT = 10;
	private static final int DIAMETER = 50;
	private boolean direccion=true; //direccion del disparo, true = derecha, false = izquierda
	int x = 0;
	int xa=0;
	int xEnemy=0;
	int yEnemy=0;
	Thread shoot;
	private boolean activo=false;
	private Game game;
	private Enemy enemy;
	private int lastXa2=0;

	public ShootEnemy(Game game, Enemy enemy) {
		this.game= game;
		this.enemy= enemy;
	}

	public void paint(Graphics2D g) {
		/*if(!activo){
			activo=true;
			xaBall= ball.getXa();
			if(lastXa2 != lastXa){
				lastXa2=lastXa;
			}
		}*/

		/*if(xaBall == 0){
			if(lastXa2 > 0){
				g.fillRect(xEnemy+xa+DIAMETER,yEnemy+(DIAMETER/2), 10, 10);
				xa--;
				direccion=true;
				if(xEnemy+xa+DIAMETER <= game.getWidth() - 10){
					return true;
				}
				else{
					return false;
				}
			}
			else{*/
		xEnemy= enemy.getX();
		yEnemy= enemy.getY();
		g.fillRect(xEnemy+xa,yEnemy+(DIAMETER/2), 10, 10);
		xa--;
		//direccion=false;
		if(xEnemy+xa <= - 10){
			xa=0;
		}
	}
	/*}
		else{
			if(xaBall > 0 ){
				g.fillRect(xEnemy+xa+DIAMETER,yEnemy+(DIAMETER/2), 10, 10);
				xa++;
				direccion=true;
				if(xEnemy+xa+DIAMETER <= game.getWidth() - 10){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				g.fillRect(xEnemy+xa,yEnemy+(DIAMETER/2), 10, 10);
				xa--;
				direccion=false;
				if(xEnemy-+xa >= - 10){
					return true;
				}
				else{
					return false;
				}
			}
		}

		g.fillRect(xBall+xa+DIAMETER,yBall+(DIAMETER/2), 10, 10);
		xa++;
		if(xBall+xa+DIAMETER <= game.getWidth() - 10){
			return true;
		}
		else{
			return false;
		}
	}*/


	public Rectangle getBounds() {
		/*if(direccion){
			return new Rectangle(xEnemy+xa+DIAMETER, yEnemy+(DIAMETER/2), 10, 10);
		}
		else{*/
		xEnemy= enemy.getX();
		yEnemy= enemy.getY();
		return new Rectangle(xEnemy+xa, yEnemy+(DIAMETER/2), 10, 10);
		//}
	}

	public int getTopY() {
		return Y - HEIGHT;
	}
}