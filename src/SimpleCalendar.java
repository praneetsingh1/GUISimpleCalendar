
/**
 * Main tester class SimpleCalendar
 * @author Praneet Singh
 *
 */
public class SimpleCalendar {
	public static void main(String[] args) {
		MyCalendar cal = new MyCalendar();
		View view = new View();
		@SuppressWarnings("unused")
		Controller controller = new Controller(cal, view);
	}
}
