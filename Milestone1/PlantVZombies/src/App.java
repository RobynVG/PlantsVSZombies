import controller.Controller;
import model.Board;
import view.View;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		View gui = new View();
		Controller controller = new Controller(gui);
		controller.initController();
	}

}
