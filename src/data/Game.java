package data;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {

	int xCraft=0;
	int yCraft=0;
	int xCraft1=0;
	int yCraft1=0;
	int xCraft2=0;
	int yCraft2=0;
	int xCraft3=0;
	int yCraft3=0;
	int explosiones=0;
	private static int restart=3;
	private static long TInicio; //Variables para determinar el tiempo de ejecución
	private static long TFin;
	private static long time;
	private static boolean terminar=true;
	private static boolean endLevel=false;
	private static Dimension dim;
	private static BufferedImage[] sprites;
	static Sound sound;
	private static JFrame frame;
	private static Craft craft;
	private static ScoreBoard scoreBoard;
	static Villager villager;
	private static int WIDTH = 45;
	private static int HEIGHT = 40;
	static Enemy enemy;
	boolean explosion=false;
	boolean firstTime=true;
	boolean implosion=false;
	private static int puntos=250;
	private Explosion [] explosionTable=new Explosion[60];
	private static Explosion [] implosionTable=new Explosion[60];
	private Explosion [] tablaExplosiones= new Explosion [16*10];
	private int villagersAlive = 0;
	/*
	 * Constructor del juego que habilita la deteccion de teclas pulsadas y soltadas.
	 */
	public Game() {
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			/*
			 * Gestiona cuando se suelta una tecla.
			 */
			public void keyReleased(KeyEvent e) {
				craft.keyReleased(e);
			}

			@Override
			/*
			 * Gestiona cuando se pulsa una tecla.
			 */
			public void keyPressed(KeyEvent e) {
				craft.keyPressed(e);
			}
		});
		setFocusable(true);
	}

	/*
	 * Mueve la nave.
	 */
	private void move() {
		craft.move();
	}

	/*
	 * Pinta en cada invocacion todos los elementos del juego.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		//implosion
		if(implosion){
			enemy.paint(g2d,sprites);
			villager.paint(g2d, sprites);
			scoreBoard.paint(g2d, time);
			implosionTable=craft.implosion(g2d,implosionTable,Color.WHITE);
			explosiones++;
			if(explosiones>=puntos){
				implosion=false;
				explosiones=0;
				puntos=250;
			}
		}
		//Termina un nivel
		else if(endLevel){
			scoreBoard.paint(g2d, time);
			//Attack
			g.drawImage(scoreBoard.getImage(sprites[39]), (this.getWidth()/2-200)-50, (this.getHeight()-500)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[58]), (this.getWidth()/2+35-200)-50, (this.getHeight()-500)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[58]), (this.getWidth()/2+35*2-200)-50, (this.getHeight()-500)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[39]), (this.getWidth()/2+35*3-200)-50, (this.getHeight()-500)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[41]), (this.getWidth()/2+35*4-200)-50, (this.getHeight()-500)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[49]), (this.getWidth()/2+35*5-200)-50, (this.getHeight()-500)/2, 45, 40, null);
			//Wave 1
			g.drawImage(scoreBoard.getImage(sprites[61]), (this.getWidth()/2+35*6-200+25)-50, (this.getHeight()-500)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[39]), (this.getWidth()/2+35*7-200+25)-50, (this.getHeight()-500)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[60]), (this.getWidth()/2+35*8-200+25)-50, (this.getHeight()-500)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[43]), (this.getWidth()/2+35*9-200+25)-50, (this.getHeight()-500)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[28]), (this.getWidth()/2+35*11-200+25)-50, (this.getHeight()-500)/2, 45, 40, null);
			//Completed
			g.drawImage(scoreBoard.getImage(sprites[41]), (this.getWidth()/2+35-200+25)-50, (this.getHeight()-400)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[53]), (this.getWidth()/2+35*2-200+25)-50, (this.getHeight()-400)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[51]), (this.getWidth()/2+35*3-200+25)-50, (this.getHeight()-400)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[54]), (this.getWidth()/2+35*4-200+25)-50, (this.getHeight()-400)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[50]), (this.getWidth()/2+35*5-200+25)-50, (this.getHeight()-400)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[43]), (this.getWidth()/2+35*6-200+25)-50, (this.getHeight()-400)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[58]), (this.getWidth()/2+35*7-200+25)-50, (this.getHeight()-400)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[43]), (this.getWidth()/2+35*8-200+25)-50, (this.getHeight()-400)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[42]), (this.getWidth()/2+35*9-200+25)-50, (this.getHeight()-400)/2, 45, 40, null);
			//Bonus X 100
			g.drawImage(scoreBoard.getImage(sprites[40]), (this.getWidth()/2-200+25)-50, (this.getHeight()-200)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[53]), (this.getWidth()/2+35-200+25)-50, (this.getHeight()-200)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[52]), (this.getWidth()/2+35*2-200+25)-50, (this.getHeight()-200)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[59]), (this.getWidth()/2+35*3-200+25)-50, (this.getHeight()-200)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[57]), (this.getWidth()/2+35*4-200+25)-50, (this.getHeight()-200)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[62]), (this.getWidth()/2+35*6-200+25)-50, (this.getHeight()-200)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[28]), (this.getWidth()/2+35*8-200+25)-50, (this.getHeight()-200)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[27]), (this.getWidth()/2+35*9-200+25)-50, (this.getHeight()-200)/2, 45, 40, null);
			g.drawImage(scoreBoard.getImage(sprites[27]), (this.getWidth()/2+35*10-200+25)-50, (this.getHeight()-200)/2, 45, 40, null);
			//Aldeanos
			int x=0;
			int y=1;
			for(int i=0;i<villagersAlive;i++){
				g.drawImage((sprites[5]), (this.getWidth()/2+x*y-200+25)-50, (this.getHeight()-100)/2, 45, 40, null);
				x=35;
				if(i>=2){
					y++;
				}
			}
			scoreBoard.setnPuntuacion(scoreBoard.getnPuntuacion()+villagersAlive*100);
		}
		//Ha terminado explosion
		else if(explosiones>=puntos){
			explosion = false;
			explosiones= 0;
			restart--;
			if(scoreBoard.getVidas()==0){
				scoreBoard.paint(g2d, time);
				g.drawImage(scoreBoard.getImage(sprites[45]), this.getWidth()/2-200, (this.getHeight()-80)/2, 45, 40, null);
				g.drawImage(scoreBoard.getImage(sprites[39]), this.getWidth()/2+35-200, (this.getHeight()-80)/2, 45, 40, null);
				g.drawImage(scoreBoard.getImage(sprites[51]), this.getWidth()/2+35*2-200, (this.getHeight()-80)/2, 45, 40, null);
				g.drawImage(scoreBoard.getImage(sprites[43]), this.getWidth()/2+35*3-200, (this.getHeight()-80)/2, 45, 40, null);

				g.drawImage(scoreBoard.getImage(sprites[53]), this.getWidth()/2+35*4-200+25, (this.getHeight()-80)/2, 45, 40, null);
				g.drawImage(scoreBoard.getImage(sprites[60]), this.getWidth()/2+35*5-200+25, (this.getHeight()-80)/2, 45, 40, null);
				g.drawImage(scoreBoard.getImage(sprites[43]), this.getWidth()/2+35*6-200+25, (this.getHeight()-80)/2, 45, 40, null);
				g.drawImage(scoreBoard.getImage(sprites[56]), this.getWidth()/2+35*7-200+25, (this.getHeight()-80)/2, 45, 40, null);
				sound.start("Game Over");
				//implosion=true;
				//puntos=50;
			}
			else{
				craft = new Craft(this, sprites,villager,scoreBoard);
				villager= new Villager(this,craft);
				//implosion=true;
				//puntos=50;
			}
		}
		//Hay explosion activa
		else if(explosion){
			/*if(explosiones>200){
					explosionTable=craft.explosion(g2d,explosionTable,Color.YELLOW);
				}
				else{*/
			tablaExplosiones=craft.explosion(g2d,tablaExplosiones,Color.WHITE);
			//}
			enemy.paint(g2d,sprites);
			villager.paint(g2d, sprites);
			scoreBoard.paint(g2d, time);
			explosiones++;
		}
		//Funcionamiento normal
		else{
			craft.paint(g2d);
			enemy.paint(g2d,sprites);
			villager.paint(g2d, sprites);
			scoreBoard.paint(g2d, time);
		}
	}



	/*g.drawLine(-1920+craft.getX(), 960, -1000+craft.getX(), 800);
		g.drawLine(-600+craft.getX(), 800, -800+craft.getX(), 960);
		g.drawLine(-400+craft.getX(), 960, -600+craft.getX(), 800);
		g.drawLine(-200+craft.getX(), 800, -400+craft.getX(), 960);
		g.drawLine(0+craft.getX(),960,-200+craft.getX(),800);	

		g.drawLine(0+craft.getX(),960,200+craft.getX(),800);	
		g.drawLine(200+craft.getX(), 800, 400+craft.getX(), 960);
		g.drawLine(400+craft.getX(), 960, 600+craft.getX(), 800);
		g.drawLine(600+craft.getX(), 800, 800+craft.getX(), 960);
		g.drawLine(800+craft.getX(), 960, 1000+craft.getX(), 800);*/

	/*
	 * Metodo que invoca el modo Game Over.
	 */
	public void gameOver() {
		sound.start("explosion");
		explosion=true;
		rellenarTablaExplosiones();
		this.repaint();
	}

	public void endLevel() {
		endLevel=true;
		this.repaint();
	}

	/*
	 * Actualiza la puntuacion cada vez que se destruye un enemigo.
	 */
	public void updatePuntuacion(){
		scoreBoard.addPuntuacion();
	}

	public int getVillagersAlive() {
		return villagersAlive;
	}

	public void setVillagersAlive(int villagersAlive) {
		this.villagersAlive = villagersAlive;
	}
	public void addVillagersAlive() {
		this.villagersAlive++;
	}

	/*
	 * Inicializa la tabla de los spirtes
	 */
	public static void tablaSprites(){
		BufferedImage bigImg;
		try {
			bigImg = ImageIO.read(new File("datos/imagenes/sprites1.png"));

			int rows = 67;
			int cols = 1;
			sprites = new BufferedImage[72];

			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < cols; j++)
				{
					sprites[(i * cols) + j] = bigImg.getSubimage(
							i * WIDTH,
							j * HEIGHT,
							WIDTH,
							HEIGHT
							);

				}
			}

			WIDTH=65;
			bigImg = ImageIO.read(new File("datos/imagenes/sprites2.png"));
			sprites[68] = bigImg.getSubimage(0 * WIDTH,0 * HEIGHT,WIDTH,HEIGHT);
			sprites[69] = bigImg.getSubimage(1 * WIDTH,0 * HEIGHT,WIDTH,HEIGHT);
			WIDTH=18;
			HEIGHT=5;
			bigImg = ImageIO.read(new File("datos/imagenes/llama_motor_izq.png"));
			sprites[70] = bigImg.getSubimage(0 * WIDTH,0 * HEIGHT,WIDTH,HEIGHT);
			bigImg = ImageIO.read(new File("datos/imagenes/llama_motor_der.png"));
			sprites[71] = bigImg.getSubimage(0 * WIDTH,0 * HEIGHT,WIDTH,HEIGHT);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void rellenarTablaExplosiones(){
		int caso=0;
		int incrementoX = 1;
		int incrementoY = 1;
		int [] xInit=new int [16];
		int [] yInit=new int [16];
		int count=0;
		for(int i=0;i<xInit.length;i++){
			xInit[i]=craft.xInit;
			yInit[i]=craft.yInit;
		}
		int xInitAdd = 2;
		int yInitAdd = 2;

		for(int i=0;i<tablaExplosiones.length;i++){
			switch(caso){
			case 0:
				incrementoX=1;
				incrementoY=1;
				tablaExplosiones[i]=new Explosion(xInit[caso],yInit[caso],incrementoX,incrementoY,xInitAdd,yInitAdd);
				caso=1;
				break;
			case 1:
				incrementoX=-1;
				incrementoY=-1;
				tablaExplosiones[i]=new Explosion(xInit[caso],yInit[caso],incrementoX,incrementoY,xInitAdd,yInitAdd);
				caso=2;
				break;
			case 2:
				incrementoX=1;
				incrementoY=-1;
				tablaExplosiones[i]=new Explosion(xInit[caso],yInit[caso],incrementoX,incrementoY,xInitAdd,yInitAdd);
				caso=3;
				break;
			case 3:
				incrementoX=-1;
				incrementoY=1;
				tablaExplosiones[i]=new Explosion(xInit[caso],yInit[caso],incrementoX,incrementoY,xInitAdd,yInitAdd);
				caso=4;
				break;
			case 4:
				incrementoX=1;
				incrementoY=0;
				tablaExplosiones[i]=new Explosion(xInit[caso],yInit[caso],incrementoX,incrementoY,xInitAdd,yInitAdd);
				caso=5;
				break;
			case 5:
				incrementoX=0;
				incrementoY=1;
				tablaExplosiones[i]=new Explosion(xInit[caso],yInit[caso],incrementoX,incrementoY,xInitAdd,yInitAdd);
				caso=6;
				break;
			case 6:
				incrementoX=-1;
				incrementoY=0;
				tablaExplosiones[i]=new Explosion(xInit[caso],yInit[caso],incrementoX,incrementoY,xInitAdd,yInitAdd);
				caso=7;
				break;
			case 7:
				incrementoX=0;
				incrementoY=-1;
				tablaExplosiones[i]=new Explosion(xInit[caso],yInit[caso],incrementoX,incrementoY,xInitAdd,yInitAdd);
				caso=8;
				break;
			case 8:
				incrementoX=2;
				incrementoY=1;
				tablaExplosiones[i]=new Explosion(xInit[caso],yInit[caso],incrementoX,incrementoY,xInitAdd,yInitAdd);
				caso=9;
				break;
			case 9:
				incrementoX=-2;
				incrementoY=-1;
				tablaExplosiones[i]=new Explosion(xInit[caso],yInit[caso],incrementoX,incrementoY,xInitAdd,yInitAdd);
				caso=10;
				break;
			case 10:
				incrementoX=2;
				incrementoY=-1;
				tablaExplosiones[i]=new Explosion(xInit[caso],yInit[caso],incrementoX,incrementoY,xInitAdd,yInitAdd);
				caso=11;
				break;
			case 11:
				incrementoX=-2;
				incrementoY=1;
				tablaExplosiones[i]=new Explosion(xInit[caso],yInit[caso],incrementoX,incrementoY,xInitAdd,yInitAdd);
				caso=12;
				break;
			case 12:
				incrementoX=1;
				incrementoY=2;
				tablaExplosiones[i]=new Explosion(xInit[caso],yInit[caso],incrementoX,incrementoY,xInitAdd,yInitAdd);
				caso=13;
				break;
			case 13:
				incrementoX=-1;
				incrementoY=-2;
				tablaExplosiones[i]=new Explosion(xInit[caso],yInit[caso],incrementoX,incrementoY,xInitAdd,yInitAdd);
				caso=14;
				break;
			case 14:
				incrementoX=-1;
				incrementoY=2;
				tablaExplosiones[i]=new Explosion(xInit[caso],yInit[caso],incrementoX,incrementoY,xInitAdd,yInitAdd);
				caso=15;
				break;
			case 15:
				incrementoX=1;
				incrementoY=-2;
				tablaExplosiones[i]=new Explosion(xInit[caso],yInit[caso],incrementoX,incrementoY,xInitAdd,yInitAdd);
				caso=0;
				break;
			}
			count++;
			if(count>=80){
				count=0;
				xInitAdd++;
				yInitAdd++;
			}
		}

	}
	/*
	 * Main del juego que lo inicia.
	 */
	public static void main(String[] args) throws InterruptedException {

		tablaSprites();
		sound=new Sound();
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		//Se inicia la pantalla de introduccion del juego.
		Game game = new Game();
		frame = new JFrame("Defender");
		JPanel menu= new JPanel();
		ImageIcon icon = new ImageIcon("datos/imagenes/intro.png");
		Image image = icon.getImage().getScaledInstance((int)dim.getWidth(), (int)dim.getHeight(), Image.SCALE_SMOOTH);
		icon.setImage(image);
		JLabel imgMenu=new JLabel(icon);
		menu.add(imgMenu);
		menu.setBackground(Color.BLACK);
		frame.add(game,BorderLayout.CENTER);
		frame.setMinimumSize(new Dimension((int)dim.getWidth()/2,(int)dim.getHeight()/2));
		frame.add(menu,BorderLayout.CENTER);
		frame.setSize(dim);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		for(int i=0; i<implosionTable.length;i++){

			//implosionTable[i]=new Explosion(0,80);
		}
		puntos=250;
		//Se inicia el sonido de la intro del juego durante 3 segundos y medio.
		sound.start("start");
		Thread.sleep(3500);
		frame.remove(menu);
		//Si no hay que terminar el juego
		while(terminar){
			//Se crea la pantalla del juego, con el tiempo transcurrido y la puntuacion
			TInicio = System.currentTimeMillis(); //Tomamos la hora en que inicio el algoritmo y la almacenamos en la variable inicio
			game = new Game();
			game.setBackground(Color.BLACK);
			villager= new Villager(game,craft);
			scoreBoard= new ScoreBoard(game,sprites);
			craft = new Craft(game, sprites,villager,scoreBoard);
			enemy = new Enemy(game,craft);			
			frame.setSize(dim);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.add(game,BorderLayout.CENTER);
			frame.setVisible(true); 
			int count=0;
			restart=3;


			//Si el jugador decide reintentar.
			while (restart>0) {
				game.move();
				game.repaint();
				TFin = System.currentTimeMillis(); //Se toma la hora en que finalizó el algoritmo y se almacena en la variable T
				time = TFin - TInicio; //Se calculan los milisegundos de diferencia
				Thread.sleep(10);
				count++;

				//Renace el enemigo si ha muerto
				if(craft.getEnemysACTIVOS() == 0 && count%100 == 0){
					craft.addEnemysACTIVOS();
					enemy.setPaint(true);
				}
				if(endLevel){
					Thread.sleep(5000);
					endLevel=false;
					craft = new Craft(game, sprites,villager,scoreBoard);
					enemy = new Enemy(game,craft);
					craft.setMAXPUNTUACION(craft.getMAXPUNTUACION()+250);
				}
			}
			Thread.sleep(1500);
			sound.start("start");
		}
		frame.dispose();
	}

}
