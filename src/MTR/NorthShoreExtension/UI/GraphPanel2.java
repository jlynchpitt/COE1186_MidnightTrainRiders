/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * --------------------------
 * SecondaryDatasetDemo1.java
 * --------------------------
 * (C) Copyright 2004, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited).
 * Contributor(s):   -;
 *
 * $Id: MultipleDatasetDemo1.java,v 1.1 2004/05/21 14:22:35 mungady Exp $
 *
 * Changes
 * -------
 * 30-Jan-2004 : Version 1 (DG);
 *
 */

package MTR.NorthShoreExtension.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
//import org.jfree.ui.Spacer;

import MTR.NorthShoreExtension.Backend.StaticTrackDBHelper;

/**
 * A demo showing the addition and removal of multiple datasets / renderers.
 */
public class GraphPanel2 extends JFrame {

    /** The plot. */
    private XYPlot plot;
       
    /**
     * Constructs a new demonstration application.
     *
     * @param title  the frame title.
     */
    public GraphPanel2(final String title, int trainID, StaticTrackDBHelper db) {
    	super("Train Power/Speed Graph");
    	
        setFont(new Font("Arial", 0, 30));

    	//create the series - add some dummy data
        XYSeries powerSeries = new XYSeries("Power");
        XYSeries setSpeedSeries = new XYSeries("Set Speed");
        XYSeries actualSpeedSeries = new XYSeries("Actual Speed");
        
        //Add power data
        Map<Long, Double> powerMap = db.getPowerList(trainID);
        
        for(Long key : powerMap.keySet()) {
        	//System.out.println("key: " + key + " value: " + powerMap.get(key));
        	powerSeries.add(key, powerMap.get(key));
        }
        
        //powerSeries.add(1000, 100);
        
        //Add setSpeed data
        Map<Long, Double> setSpeedMap = db.getSetSpeedList(trainID);
        
        for(Long key : setSpeedMap.keySet()) {
        	setSpeedSeries.add(key, setSpeedMap.get(key));
        }
        
        //Add actualSpeed data
        Map<Long, Double> actualSpeedMap = db.getActualSpeedList(trainID);
        
        for(Long key : actualSpeedMap.keySet()) {
        	actualSpeedSeries.add(key, actualSpeedMap.get(key));
        }
        

        //create the datasets
        XYSeriesCollection powerDataset = new XYSeriesCollection();
        XYSeriesCollection setSpeedDataset = new XYSeriesCollection();
        XYSeriesCollection actualSpeedDataset = new XYSeriesCollection();
        powerDataset.addSeries(powerSeries);
        setSpeedDataset.addSeries(setSpeedSeries);
        actualSpeedDataset.addSeries(actualSpeedSeries);

        //construct the plot
        XYPlot plot = new XYPlot();
        plot.setDataset(0, powerDataset);
        plot.setDataset(1, setSpeedDataset);
        plot.setDataset(2, actualSpeedDataset);

        //customize the plot with renderers and axis
        plot.setRenderer(0, new XYSplineRenderer());//use default fill paint for first series
        XYLineAndShapeRenderer linerenderer = new XYLineAndShapeRenderer();
        linerenderer.setSeriesFillPaint(0, Color.BLUE);
        plot.setRenderer(1, linerenderer);
        plot.setRangeAxis(0, new NumberAxis("Power (kWatts)"));
        plot.setRangeAxis(1, new NumberAxis("Speed (MPH)"));
        plot.setDomainAxis(new NumberAxis("Time (seconds since program launch)"));
        XYSplineRenderer splinerenderer2 = new XYSplineRenderer();
        splinerenderer2.setSeriesFillPaint(0, Color.GREEN);
        plot.setRenderer(2, splinerenderer2);

        //Map the data to the appropriate axis
        plot.mapDatasetToRangeAxis(0, 0);
        plot.mapDatasetToRangeAxis(1, 1);   
        plot.mapDatasetToRangeAxis(2, 1);   

        //generate the chart
        JFreeChart chart = new JFreeChart(title, getFont(), plot, true);
        chart.setBackgroundPaint(Color.WHITE);
        JPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 540));


        /*super(title);
        final TimeSeriesCollection dataset1 = createRandomDataset("Series 1");
        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Multiple Dataset Demo 1", "Time", "Value", dataset1, true, true, false
        );
        chart.setBackgroundPaint(Color.white);*/
        
        this.plot = chart.getXYPlot();
        this.plot.setBackgroundPaint(Color.lightGray);
        this.plot.setDomainGridlinePaint(Color.white);
        this.plot.setRangeGridlinePaint(Color.white);

        Font tickFont = new Font("Arial", Font.PLAIN, 16); 
        Font labelFont = new Font("Arial", Font.BOLD, 20); 
        //this.plot.getDomainAxis().setLabelFont(font3);
        //this.plot.getRangeAxis().setLabelFont(font3);
//        this.plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 4, 4, 4, 4));
        final ValueAxis axis = this.plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setTickLabelFont(tickFont);
        axis.setLabelFont(labelFont);

        final NumberAxis rangeAxis2 = new NumberAxis("Range Axis 2");
        rangeAxis2.setAutoRangeIncludesZero(false);
        
        final ValueAxis yAxis = this.plot.getRangeAxis();
        yAxis.setTickLabelFont(tickFont);
        yAxis.setLabelFont(labelFont);
        
        final ValueAxis yAxis2 = this.plot.getRangeAxisForDataset(1);
        yAxis2.setTickLabelFont(tickFont);
        yAxis2.setLabelFont(labelFont);
        
        final JPanel content = new JPanel(new BorderLayout());

        //final ChartPanel chartPanel = new ChartPanel(chart);
        content.add(chartPanel);
        
        /*final JButton button1 = new JButton("Add Dataset");
        button1.setActionCommand("ADD_DATASET");
        button1.addActionListener(this);
        
        final JButton button2 = new JButton("Remove Dataset");
        button2.setActionCommand("REMOVE_DATASET");
        button2.addActionListener(this);

        final JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        
        content.add(buttonPanel, BorderLayout.SOUTH);*/
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 540));
        setContentPane(content);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    /**
     * Creates a random dataset.
     * 
     * @param name  the series name.
     * 
     * @return The dataset.
     */
    private TimeSeriesCollection createRandomDataset(final String name) {
        final TimeSeries series = new TimeSeries(name);
        double value = 100.0;
        RegularTimePeriod t = new Day();
        for (int i = 0; i < 50; i++) {
            series.add(t, value);
            t = t.next();
            value = value * (1.0 + Math.random() / 100);
        }
        return new TimeSeriesCollection(series);
    }
    
    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    
   
    
    public static void createAndShowGui(StaticTrackDBHelper db, int trainID) {
    	final GraphPanel2 demo = new GraphPanel2("Multiple Dataset Demo 1", trainID, db); //TODO: Auto grab train id
        //demo.setSize(d);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    /*public static void main(final String[] args) {

        final GraphPanel2 demo = new GraphPanel2("Multiple Dataset Demo 1", 1);
        //demo.setSize(d);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }*/

}
