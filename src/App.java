import controller.Controller;
import model.Board;
import view.View;

public class App {
	public static void main(String args[]) {
		Board m = new Board();
		View v = new View();
		Controller c = new Controller(m,v);
		
		c.initController();
	}
}
