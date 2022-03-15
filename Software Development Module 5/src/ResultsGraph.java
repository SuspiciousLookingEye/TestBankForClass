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

public class ResultsGraph extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResultsGraph() {
		createUI();
	}
	
	private void createUI() {
		XYDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset);
		ChartPanel panel = new ChartPanel(chart);
		panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		panel.setBackground(Color.white);
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(700,500));
		container.add(panel);
		this.add(container);
		
		panel.validate();
		panel.repaint();
		this.pack();
		setContentPane(container);
		setTitle("Results Chart");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private XYDataset createDataset() {
		var dynamicSeries = new XYSeries("Dynamic Series");
		Dynamic chartObject1 = new Dynamic();
		for (int x = 1; x <= 25; x++) {
			chartObject1.setLimit(x);
			chartObject1.run();
			dynamicSeries.add(x, chartObject1.getTime());
		}
		
		var recursiveSeries = new XYSeries("Recursive Series");
		Recursive chartObject2 = new Recursive();
		for (int x = 1; x <= 25; x++) {
			chartObject2.setLimit(x);
			chartObject2.run();
			recursiveSeries.add(x, chartObject2.getTime());
		}
		
		var dataset = new XYSeriesCollection();
		dataset.addSeries(dynamicSeries);
		dataset.addSeries(recursiveSeries);
		return dataset;
	}
	
	private JFreeChart createChart (final XYDataset dataset) {
		JFreeChart chart = ChartFactory.createXYLineChart("Dynamic vs Recursive Fibonacci Sequence", "Input Limit", "Time in nanoseconds", dataset, PlotOrientation.VERTICAL, true, true, false);
		XYPlot plot = chart.getXYPlot();
		var renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		renderer.setSeriesPaint(1, Color.BLUE);
		renderer.setSeriesStroke(1, new BasicStroke(2.0f));
		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinesVisible(false);
		chart.getLegend().setFrame(BlockBorder.NONE);
		chart.setTitle(new TextTitle("Dynamic vs Recursive Fibonacci Sequence", new Font("Serif", Font.BOLD, 18)));
		return chart;
	}

}
