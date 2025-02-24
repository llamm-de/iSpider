package tools;

import tools.Function.ScalarFct;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Klasse zum einfachen Zeichnen von Funktionen mittels JFreeChart.<br>
 * Benutzung:
 * <ol>
 *  <li>Erzeugen einer Instanz von FunctionPlotter (mit Beschriftung)
 *  <li>Hinzufuegen von Funktionen (mittels <code>addFunction<code>)
 *  <li>Zeichnen des Diagramms (mittels <code>plot<code>).
 * </ol>
 *
 * @author Institut fuer Bauinformatik, Leibniz Universitaet Hannover
 */
public class FunctionPlotter {
    
    /* Funktionen als XYSeries */
    private XYSeriesCollection functions;
    
    /* Beschriftungen */
    private String title, xDesc, yDesc;
    
    /**
     * Erzeugt einen FunctionPlotter, mit Titel "Diagramm" und 
     * Achsenbeschriftungen laut {@link FunctionPlotter#FunctionPlotter(java.lang.String)}.
     */
    public FunctionPlotter() {
        this("Diagramm");
    }

    /**
     * Erzeugt einen FunctionPlotter mit Titel <code>title</code> und 
     * Achsenbeschriftungen "x" und "f(x)".
     * 
     * @param title Titel des Diagramms.
     */
    public FunctionPlotter(String title) {
        this(title, "x", "f(x)");
    }
    
    /**
     * Erzeugt einen FunctionPlotter mit gegebenem Titel und Achsenbeschriftungen.
     * 
     * @param title Titel des Diagramms
     * @param xDesc Beschriftung der x-Achse
     * @param yDesc Beschriftung der y-Achse
     */
    public FunctionPlotter(String title, String xDesc, String yDesc) {
        this.title = title;
        this.xDesc = xDesc;
        this.yDesc = yDesc;
        functions = new XYSeriesCollection();
    }
    
    /**
     * Nimmt die Funktion, die durch die Punkte [x_i, y_i=f(x_i)] definiert ist
     * in das Diagramm auf. In der Legende wird diese Funktion mit <code>name</code>
     * gekennzeichnet.
     * 
     * @param x Feld mit Stuetzstellen x_i
     * @param y Feld mit Funktionswerten y_i an den zugehoerigen Stuetzstellen x_i
     * @param name Name der Funktion
     */
    public void addFunction(double[] x, double[] y, String name) {
        if (x.length != y.length)
            throw new IllegalArgumentException("unzulaessig: size of x ("+x.length+") != size of y ("+y.length+")");
        XYSeries xys = new XYSeries(name);
        
        for (int i=0;  i<x.length; i++)
            xys.add(x[i], y[i], false);
        functions.addSeries(xys);
    }

    /**
     * Nimmt die Funktion f in das Diagramm auf. Dazu muss der zu zeichnende
     * Bereich (<code>[xmin,max]</code>) und die Abtastrate <code>dx</code>
     * festgelegt werden. In der Legende wird diese Funktion mit <code>name</code>
     * gekennzeichnet.
     * 
     * @param f     zu zeichnende Funktion
     * @param xmin  linke Grenze des zu zeichnenden Bereichs
     * @param xmax  rechte Grenze des zu zeichnenden Bereichs
     * @param dx    Abtastrate fuer die Stuetzstellen
     * @param name  Name der Funktion
     */
    public void addFunction(ScalarFct f, double xmin, double xmax, double dx, String name) {
        if (xmin >= xmax)
            throw new IllegalArgumentException("unzulaessig: xmin ("+xmin+") >= xmax ("+xmax+")");
        XYSeries xys = new XYSeries(name);
        for (double x=xmin; x<=xmax; x+=dx)
            xys.add(x, f.getValue(x), false);
        functions.addSeries(xys);
    }
    
//    public void addFunction(DiscretizedFuzzyNumber x, double xmin, double xmax, String name) {
//        XYSeries xys = new XYSeries(name);
//        
//        // Alphacutplottdifferenz festlegen
//        double delta = 0.01;
//        double schritte = 1./delta;
//        
//        // linken Teil hinzufuegen
//        for (double i=0;  i<=schritte; i++){
//            if (x.getAlphaX(i*delta)>xmin && x.getAlphaX(i*delta)<xmax){
//            xys.add(x.getAlphaX(i*delta), i*delta);}
//        }
//        
//        // rechten Teil hinzufuegen
//        for (double i= schritte;  i>= 0; i--){
//            if (x.getBetaX(i*delta)>xmin && x.getBetaX(i*delta)<xmax){
//            xys.add(x.getBetaX(i*delta), i*delta);}
//        }
//
//        functions.addSeries(xys);
//    }
    
    /**
     * Ruft {@link #plot(int, int) plot(800,600)} auf.
     */
    public void plot() {
        plot(800,600);
    }
    
    
    /**
     * Ruft {@link #plot(int, int, boolean) plot(800,600, plotPoints)} auf.
     */
    public void plot(boolean plotPoints) {
        plot(800,600, plotPoints);
    }
    
    
    /**
     * Zeichnet ein Diagramm mit allen zuvor (durch <code>addFunction</code>)
     * hinzugefuegten Funktionen in einem Fenster der Groesse <code>xSize x
     * ySize</code>.
     * 
     * @param xSize Pixel in x-Richtung des Fensters
     * @param ySize Pixel in y-Richtung des Fensters
     */
    public void plot(int xSize, int ySize) {
        
        // XY-Linien-Diagramm erstellen
        JFreeChart chart = ChartFactory.createXYLineChart(title, xDesc, yDesc, functions, PlotOrientation.VERTICAL, true, true, false);
        
        // Fuer Modifikationen am Diagramm
        XYPlot plot = chart.getXYPlot();
        
        // Renderer verwenden, der Shapes und Linien zeichnen kann.
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        for (int i=0; i<plot.getSeriesCount(); i++) {
            // Linien zeichnen (false fuer nicht zeichnen)
            renderer.setSeriesLinesVisible(i, true);
        }
        // Renderer uebernehmen
        plot.setRenderer(renderer);

        // Fenster fuer die Darstellung
        ChartFrame f = new ChartFrame("Vis", chart);
        f.setSize(xSize,ySize);
        f.setVisible(true);
    }
    
    
    /**
     * Zeichnet ein Diagramm mit allen zuvor (durch <code>addFunction</code>)
     * hinzugefuegten Funktionen in einem Fenster der Groesse <code>xSize x
     * ySize</code>.
     * 
     * @param xSize Pixel in x-Richtung des Fensters
     * @param ySize Pixel in y-Richtung des Fensters
     */
    public void plot(int xSize, int ySize, boolean plotPoints) {
        
        // XY-Linien-Diagramm erstellen
        JFreeChart chart = ChartFactory.createXYLineChart(title, xDesc, yDesc, functions, PlotOrientation.VERTICAL, true, true, false);
        
        // Fuer Modifikationen am Diagramm
        XYPlot plot = chart.getXYPlot();
        
        // Renderer verwenden, der Shapes und Linien zeichnen kann.
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        for (int i=0; i<plot.getSeriesCount(); i++) {
            // Linien zeichnen (false fuer nicht zeichnen)
            renderer.setSeriesLinesVisible(i, true);
            renderer.setSeriesShapesVisible(i, plotPoints);
        }
        // Renderer uebernehmen
        plot.setRenderer(renderer);

        // Fenster fuer die Darstellung
        ChartFrame f = new ChartFrame("Vis", chart);
        f.setSize(xSize,ySize);
        f.setVisible(true);
    }
    
    
    /**
     * Liefert den Sinus zu einem Argument x.
     * @return sin(x)
     */
    public static ScalarFct getSinus() {
        return new ScalarFct() {
            @Override
            public double getValue(double x) {
                return Math.sin(x);
            }
        };
    }
    
    
    /** Spielwiese... */
    public static void main(String[] args) {
        
        // Stuetzstellen
        double[] x = new double[] {
            -3.0,-2.0, 1.3, 4.6, 17.5
        };
        // Funktionswerte
        double[] y = new double[] {
            10., 10., 11.2, 12.8, 13.0
        };
        
        ScalarFct sin = getSinus();
        
        FunctionPlotter plotter = new FunctionPlotter("test");
        // Zeichnen von f(x_i) = y_i
        plotter.addFunction(x, y, "f");
        // Sinus zeichnen (mit ScalarFunction1d)
        plotter.addFunction(sin, 0.,10., 0.1, "Sinus");
        // e(x) zeichnen (mit anonymer innerer Klasse)
        plotter.addFunction(new ScalarFct() {

            @Override
            public double getValue(double x) {
                return Math.exp(x);
            }
        }, 0, 6., 1, "e(x)");
        
        plotter.plot();
    }

    public void drawLegend(boolean b) {
        
    }
}
