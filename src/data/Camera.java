package data;

public class Camera {

	private float x, y;
	
	public Camera(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void tick(Craft craft){
		x--;
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
