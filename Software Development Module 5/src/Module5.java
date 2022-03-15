import javax.swing.SwingUtilities;

public class Module5 {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ResultsGraph().setVisible(true);
			}
		});
	}
}