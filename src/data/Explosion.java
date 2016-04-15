package data;

import java.awt.Color;

public class Explosion {

	private int x;
	private int y;
	private int incrementoX;
	private int incrementoY;
	private int xAdd;
	private int yAdd;
	private Color color;
	
	public Explosion(int x, int y,int incrementoX, int incrementoY, int xAdd, int yAdd,Color color){
		this.x = x;
		this.y= y;
		this.incrementoX=incrementoX;
		this.incrementoY=incrementoY;
		this.xAdd=xAdd;
		this.yAdd=yAdd;
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public void setXY(int x,int y) {
		this.x = x;
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public int getIncrementoX() {
		return incrementoX;
	}

	public void setIncrementoX(int incrementoX) {
		this.incrementoX = incrementoX;
	}

	public int getIncrementoY() {
		return incrementoY;
	}

	public void setIncrementoY(int incrementoY) {
		this.incrementoY = incrementoY;
	}

	public int getxAdd() {
		return xAdd;
	}

	public void setxAdd(int xAdd) {
		this.xAdd = xAdd;
	}

	public int getyAdd() {
		return yAdd;
	}

	public void setyAdd(int yAdd) {
		this.yAdd = yAdd;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
