package data;

public class Camera {

	private float x, y;
	private Game game;
	
	public Camera(float x, float y, Game game){
		this.x = x;
		this.y = y;
		this.game = game;
	}
	
	public void tick(Craft craft){
		this.x = -craft.getX() + game.getWidth()/2;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public float getX(){
		return this.x;
	}
	
	public float getY(){
		return this.y;
	}
	
	
}
