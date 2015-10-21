
public class Pair {
	public int x;
	public int y;
	
	Pair(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public String toString(){
		return "(" + x + ", " + y + ")";  
	}
}