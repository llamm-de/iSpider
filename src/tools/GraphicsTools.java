package tools;

//import ODE.ExplicitEuler;
//import ODE.IVP;
import java.awt.Color;
import tools.Function.ScalarFct;
import java.util.ArrayList;
//import ODE.ODESystem;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.VectorRenderer;
import org.jfree.data.xy.VectorSeries;
import org.jfree.data.xy.VectorSeriesCollection;
import org.jfree.ui.RectangleInsets;

/**
 * Beinhaltet Methoden zum Plotten von Phasendiagrammen für ODESysteme mit
 * 2 Zustandsgrößen. Im aktuellen Framework ist vorzugsweise die Methode
 * {@link #plotPhasendiagramm(double[], double[])} zu verwenden.
 *
 * @author berthold
 */
public final class GraphicsTools {

    private GraphicsTools() {}
    
    /**
     * Erzeugt und visualisiert ein Phasendiagramm bzgl. der Loesungen fuer
     * z1 und z2, im Intervall [t0,t1] mit einer Schrittweite von dt.
     * @param z1    Loesung einer Zustandsgroesse eines ODESystems in Form einer
     *              ScalarFunction1d
     * @param z2    Loesung einer Zustandsgroesse eines ODESystems in Form einer
     *              ScalarFunction1d
     * @param t0    Startzeitpunkt, ab dem die Funktionen ausgewertet werden
     * @param t1    Endzeitpunkt, bis zu dem die Funktionen ausgewertet werden
     * @param dt    Schrittweite, mit der die Funktionen ausgewertet werden
     */
    public static void plotPhasendiagramm(ScalarFct z1, ScalarFct z2, double t0, double t1, double dt) {
    
        VectorSeriesCollection vsc = new VectorSeriesCollection();
        
        VectorSeries vs = new VectorSeries("");
        
        double z1_told = z1.getValue(t0);
        double z2_told = z2.getValue(t0);
        for (double t=t0+dt; t<=t1; t+=dt) {
            
            double z1_t  = z1.getValue(t);
            double z2_t  = z2.getValue(t);
            
            vs.add(z1_told, z2_told, z1_t-z1_told, z2_t-z2_told);
            z1_told = z1_t;
            z2_told = z2_t;
        }
            
        vsc.addSeries(vs);
        createAndViewVectorPlot("Phasendiagramm", vsc);
    }
    
    /**
     * Erzeugt und visualisiert das Phasendiagramm für ein ODESystem, dessen
     * Loesung in Form von double arrays vorliegt. Ein typisches Vorgehen zum
     * Plotten des Phasendiagramms fuer ein ODESystem mit 2 Zustandsgroessen:
     * <ol>
     *  <li>Loesen des IVP ivp mit {@link IVP#solveWithHistory(double, ODE.ExplicitTimeStepMethod, double)}:
     *      <code>double[][] res = ivp.solve(...);</code>
     *  <li>Plotten des Phasendiagramms:
     *      <code>GraphicsTools.plotPhasendiagramm(res[1], res[2]);</code>
     * </ol>
     * @param z1
     * @param z2 
     */
    public static void plotPhasendiagramm(double[] z1, double[] z2) {
        
        VectorSeriesCollection vsc = new VectorSeriesCollection();
        
        VectorSeries vs = new VectorSeries("");
        
        double z1_told = z1[0];
        double z2_told = z2[0];
        for (int i=1; i<z1.length; i++) {
            
            double z1_t  = z1[i];
            double z2_t  = z2[i];
            
            vs.add(z1_told, z2_told, z1_t-z1_told, z2_t-z2_told);
            z1_told = z1_t;
            z2_told = z2_t;
        }
            
        vsc.addSeries(vs);
        createAndViewVectorPlot("Phasendiagramm", vsc);
    }
    
    
        
    /**
     * Erzeugt und visualisiert die Ableitungen eines 2D-ODESystems projeziert
     * auf die Zustandsebene z1,z2 im Rechteck [z0_min,z0_max]x[z1_min,z1_max]
     * mit einer Schrittweite von dz in Richtung von z1 und z2. Es werden nur die
     * Ableitungen zum Zeitpunkt t gezeichnet.
     * @param sys
     * @param t
     * @param dz
     * @param z0_min
     * @param z0_max
     * @param z1_min
     * @param z1_max 
     */
//    public static void plotAbleitungen2D(ODESystem sys, double t, double dz, double z0_min, double z0_max, double z1_min, double z1_max) {
//        
//        
//        double zscale = dz;
//        
//        VectorSeriesCollection vsc = new VectorSeriesCollection();
//        VectorSeries vs = new VectorSeries("dz/dt");
//        
//        for (double z0=z0_min; z0<=z0_max; z0+=dz) {
//            for (double z1=z1_min; z1<=z1_max; z1+=dz) {
//                double[] werte = new double[]{z0, z1};
//
//                // Ableitungen am Punkt [z0,z1] berechnen
//                double[] dzdt = sys.getValue(t, werte);
//                vs.add(z0, z1, zscale*dzdt[0], zscale*dzdt[1]);
//            }
//        }
//        vsc.addSeries(vs);
//        createAndViewVectorPlot("Ableitungen", vsc);
//        
//    }
    
    
    
    public static void createAndViewVectorPlot(String title, VectorSeriesCollection vsc) {
        // Diagramm erstellen
        NumberAxis xAxis = new NumberAxis("X");
        xAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
        xAxis.setLowerMargin(0.01);
        xAxis.setUpperMargin(0.01);
        xAxis.setAutoRangeIncludesZero(false);
        
        NumberAxis yAxis = new NumberAxis("Y");
        yAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
        yAxis.setLowerMargin(0.01);
        yAxis.setUpperMargin(0.01);
        yAxis.setAutoRangeIncludesZero(false);
        VectorRenderer renderer = new VectorRenderer();
        XYPlot plot = new XYPlot(vsc, xAxis, yAxis, renderer);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5, 5, 5, 5));
        plot.setOutlinePaint(Color.black);
        JFreeChart chart = new JFreeChart(title, plot);
        chart.setBackgroundPaint(Color.white);
        
        // Fenster fuer die Darstellung
        ChartFrame f = new ChartFrame(title, chart);
        f.setSize(800,800);
        f.setVisible(true);
    }
    
}
