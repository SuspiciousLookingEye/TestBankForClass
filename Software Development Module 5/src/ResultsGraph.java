import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

/**
 * This class utilizes the dynamic and recursive classes to construct a XY data set, where X is the limit variable used and Y is the time taken to generate results.
 * The class then takes the data set and uses Swing to create a two-line XY line chart that maps out the data set and displays it in a popup window.
 * <p>All components of this class heavily rely on the external JAR library for JFreeCharts in order to construct the necessary Dataset and Chart objects for the UI. Additionally, JFreeCharts itself requires the JCommon library.</p>
 * @author Jordan Roig
 * @since 15.02+7
 */
public class ResultsGraph extends JFrame{

	/**
	 * The serialVersionUID is a code that ensures that a serializable class matches up to a compatible loaded class.
	 * This class does not use or care about serialization, but a default UID value is provided to clear up potential IDE warnings and assist debugging efforts.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The constructor for the ResultsGraph object. Upon construction, the object will generate a data set, chart, and JPanel using createUI().
	 */
	public ResultsGraph() {
		createUI();
	}
	
	/**
	 * This method first utilizes the private createDataset() method to run the Dynamic and Recursive Fibonacci sequences up to a default limit of 50.
	 * It will instantiate two different XYSeries objects and store the current term as well as the time taken, organize them into a Collections object, and pass the collection to createChart().
	 * <p>The private createChart() method will then use the dataset to construct a XYLineChart for the values, construct a plot to represent them in the UI, and construct a renderer to create style configurations for them. 
	 * This chart is then passed back to the createUI() method to finish construction.</p>
	 * <p>The method then constructs a ChartPanel object to contain the chart and sets style elements. It also creates the final JPanel object container, where the popup dimensions are set and the objects are added in.
	 * Last, the method validates the chart's components and layout, sets positioning and default close operation, and opens the popup. </p>
	 */
	public void createUI() {
		//These call the private methods to construct a dataset collection, and then pass the dataset collection to create a chart.
		XYDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset);
		//this will construct a container for the chart and set the background and border size for the outer portions of the chart.
		ChartPanel panel = new ChartPanel(chart);
		panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		panel.setBackground(Color.white);
		//these methods construct the JPanel that will contain the chart
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(700,500));
		//these methods add the charts to the UI, validates their layout and appearance, and fits them to the UI dimensions
		container.add(panel);
		this.add(container);
		panel.validate();
		panel.repaint();
		this.pack();
		//This fits the UI elements to the content pane for usage, sets the pane title, position, and exit behavior, and enables it.
		setContentPane(container);
		setTitle("Results Chart");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private XYDataset createDataset() {
		//After instantiating the data series and objects, the loops iterate through terms up to the default limit of 50. It will run, and add the results to the dataset as (term,time).
		var dynamicSeries = new XYSeries("Dynamic Series");
		Dynamic chartObject1 = new Dynamic();
		for (int x = 1; x <= 50; x++) {
			chartObject1.setLimit(x);
			chartObject1.run();
			dynamicSeries.add(x, chartObject1.getTime());
		}
		
		var recursiveSeries = new XYSeries("Recursive Series");
		Recursive chartObject2 = new Recursive();
		for (int x = 1; x <= 50; x++) {
			chartObject2.setLimit(x);
			chartObject2.run();
			recursiveSeries.add(x, chartObject2.getTime());
		}
		//constructs a collection object, and adds both series to it.
		var dataset = new XYSeriesCollection();
		dataset.addSeries(dynamicSeries);
		dataset.addSeries(recursiveSeries);
		return dataset;
	}

	private JFreeChart createChart (final XYDataset dataset) {
		//constructs a chart using data and preliminary settings such as graph orientation, titles, and features. Legends are enabled, tooltips and URLs are not.
		JFreeChart chart = ChartFactory.createXYLineChart("Dynamic vs Recursive Fibonacci Sequence", "Term Number", "Time in nanoseconds", dataset, PlotOrientation.VERTICAL, true, true, false);
		//this uses the chart to construct a XYplot of points to be displayed.
		XYPlot plot = chart.getXYPlot();
		//this constructs a renderer object to draw the XY lines and set their stylistic settings such as color or stroke.
		var renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		renderer.setSeriesPaint(1, Color.BLUE);
		renderer.setSeriesStroke(1, new BasicStroke(2.0f));
		//these methods handle some final configuration details and style choices, and then the chart is returned to createUI().
		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinesVisible(false);
		chart.getLegend().setFrame(BlockBorder.NONE);
		chart.setTitle(new TextTitle("Dynamic vs Recursive Fibonacci Sequence", new Font("Serif", Font.BOLD, 18)));
		return chart;
	}

}
