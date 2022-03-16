import javax.swing.SwingUtilities;

/**
 * This class contains the main method, which initiates the construction of a ResultsGraph object, which in turn initializes the dataset and GUI using the Dynamic and Recursive classes as well as the JFreeCharts library.
 * @author Jordan Roig
 * @since 15.02+7
 *
 */
public class Module5 {

	/**
	 * This method creates a ResultsGraph object using its constructor and sets its output to be visible. 
	 * It is contained by an invokeLater method for asynchronous handling of AWT events to prevent race conditions in the current thread. This acts as a safety in case additional event handlers are added and cause issues.
	 * @param args Arguments passed to the virtual machine via the console. Not currently used, but can be applied in modifications of the program in place of Scanner objects.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ResultsGraph().setVisible(true);
			}
		});
	}
}