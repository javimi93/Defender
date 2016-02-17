package data;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Shoot {
	private static final int Y = 330;
	private static final int WITH = 60;
	private static final int HEIGHT = 10;
	private static final int DIAMETER = 30;
	int x = 0;
	int xa=0;
	int xaBall = 1;
	int xBall=0;
	int yBall=0;
	Thread shoot;
	private boolean activo=false;
	private Game game;
	private Ball ball;
	private int lastXa2=0;

	public Shoot(Game game, Ball ball) {
		this.game= game;
		this.ball= ball;
		xBall= ball.getX();
		yBall= ball.getY();
	}

	public boolean paint(Graphics2D g,int lastXa) {
		if(!activo){
			activo=true;
			xaBall= ball.getXa();
			if(lastXa2 != lastXa){
				lastXa2=lastXa;
			}
		}

		if(xaBall == 0){
			if(lastXa2 > 0){
				g.fillRect(xBall+xa+DIAMETER,yBall+(DIAMETER/2), 10, 10);
				xa++;
				if(xBall+xa+DIAMETER <= game.getWidth() - 10){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				g.fillRect(xBall+xa,yBall+(DIAMETER/2), 10, 10);
				xa--;
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
				g.fillRect(xBall+xa+DIAMETER,yBall+(DIAMETER/2), 10, 10);
				xa++;
				if(xBall+xa+DIAMETER <= game.getWidth() - 10){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				g.fillRect(xBall+xa,yBall+(DIAMETER/2), 10, 10);
				xa--;
				if(xBall-+xa >= - 10){
					return true;
				}
				else{
					return false;
				}
			}
		}

		/*g.fillRect(xBall+xa+DIAMETER,yBall+(DIAMETER/2), 10, 10);
		xa++;
		if(xBall+xa+DIAMETER <= game.getWidth() - 10){
			return true;
		}
		else{
			return false;
		}*/
	}


	public Rectangle getBounds() {
		return new Rectangle(xBall+xa+DIAMETER, yBall+(DIAMETER/2), 10, 10);
	}

	public int getTopY() {
		return Y - HEIGHT;
	}
}