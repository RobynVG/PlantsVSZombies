import controller.Controller;
import view.View;

public class App {
	public static void main(String args[]) {
		//Board m = new Board();
		View v = new View();
		Controller c = new Controller(v);
		
		c.initController();
	}
}
