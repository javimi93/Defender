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
	private static Dimension dim;
	private static BufferedImage[] sprites;
	static Sound sound;
	private static JFrame frame;
	private static Craft craft;
	private static ScoreBoard scoreBoard;
	static Villager villager;
	private static int WIDTH = 45;
	private final static int HEIGHT = 40;
	static Enemy enemy;
	boolean explosion=false;

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
		if(explosion){
			int inercia=1;
			int puntos=100;
			int dx=5;

			double gravedad=0.1;
			int enfriamiento=3;
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

			xCraft+=dx;
			yCraft+=dy;
			xCraft1-=dx;
			yCraft1-=dy;
			xCraft2+=dx;
			yCraft2-=dy;
			xCraft3-=dx;
			yCraft3+=dy;
			int X=(int)Math.round(xCraft);
			int Y=(int)Math.round(yCraft);
			int XX=(int)Math.round(xCraft1);
			int YY=(int)Math.round(yCraft1);  
			int XX1=(int)Math.round(xCraft2);
			int YY1=(int)Math.round(yCraft2);  
			int XX2=(int)Math.round(xCraft3);
			int YY2=(int)Math.round(yCraft3);  
			g.setColor(Color.YELLOW);
			if(Y>80){
				g.fillOval(X,Y,10,10);
			}
			if(YY>80){
				g.fillOval(XX,YY,10,10);
			}
			if(YY1>80){
				g.fillOval(XX1,YY1,10,10);
			}
			if(YY2>80){
				g.fillOval(XX2,YY2,10,10);
			}
			enemy.paint(g2d,sprites);
			villager.paint(g2d, sprites);
			scoreBoard.paint(g2d, time);
			explosiones++;
			if(explosiones>=puntos){
				explosion = false;
				explosiones= 0;
				restart--;
				if(scoreBoard.getVidas()==0){
					scoreBoard.paint(g2d, time);
					g.drawImage(sprites[45], this.getWidth()/2-200, (this.getHeight()-80)/2, 45, 40, null);
					g.drawImage(sprites[39], this.getWidth()/2+35-200, (this.getHeight()-80)/2, 45, 40, null);
					g.drawImage(sprites[51], this.getWidth()/2+35*2-200, (this.getHeight()-80)/2, 45, 40, null);
					g.drawImage(sprites[43], this.getWidth()/2+35*3-200, (this.getHeight()-80)/2, 45, 40, null);

					g.drawImage(sprites[53], this.getWidth()/2+35*4-200+25, (this.getHeight()-80)/2, 45, 40, null);
					g.drawImage(sprites[60], this.getWidth()/2+35*5-200+25, (this.getHeight()-80)/2, 45, 40, null);
					g.drawImage(sprites[43], this.getWidth()/2+35*6-200+25, (this.getHeight()-80)/2, 45, 40, null);
					g.drawImage(sprites[56], this.getWidth()/2+35*7-200+25, (this.getHeight()-80)/2, 45, 40, null);
					sound.start("Game Over");
				}
				else{
					craft = new Craft(this, sprites,villager,scoreBoard);
					villager= new Villager(this,craft);
				}
			}
			else{
				craft = new Craft(this, sprites,villager,scoreBoard);
				villager= new Villager(this,craft);
			}
		}

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
		explosion=true;
		xCraft=craft.getX();
		xCraft1=xCraft;
		xCraft2=xCraft;
		xCraft3=xCraft;
		yCraft=craft.getY();
		yCraft1=yCraft;
		yCraft2=yCraft;
		yCraft3=yCraft;
		this.repaint();
	}

	/*
	 * Actualiza la puntuacion cada vez que se destruye un enemigo.
	 */
	public void updatePuntuacion(){
		scoreBoard.addPuntuacion();
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
			sprites = new BufferedImage[70 * cols];

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
		} catch (IOException e) {
			e.printStackTrace();
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
			}
			Thread.sleep(1500);
			sound.start("start");
		}
		frame.dispose();
	}

}
