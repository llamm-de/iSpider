package tools;

//import tools.IVP;
import tools.Function.ScalarFct;
import java.awt.Color;
import java.awt.Shape;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



/**
 * <p>
 * Klasse zum Zeichnen von eindimensionalen skalaren Funktionen.
 * Die Klasse stellt Methoden (<code>addOrUpdateSeries(...)</code>) zum
 * Hinzuf&uuml;gen von Funktionen als Wertepaare (x, f(x)) und als
 * {@link function.ScalarFunction1d} zur Verf&uuml;gung. Eine Funktion wird in
 * Form von diskreten Wertepaaren gespeichert, die linear interpoliert dargestellt
 * werden.
 * </p>
 * <p>
 * Einer Funktion wird ein <code>key</code> (String) zugeordnet, &uuml;ber die
 * ihre Eigenschaften zum Zeichnen (Farbe, etc.) ge&auml;ndert werden k&ouml;nnen
 * (Methode <code>setSeries...</code>).
 * </p>
 * <p>
 * Die Methode {@link #plot(int, int)} dient zum Anzeigen des Charts in einem
 * Fenster.
 * </p>
 * @author berthold
 */
public class FunctionGraph extends ChartPanel {
    
    private XYSeriesCollection serColl = new XYSeriesCollection();
    private LinkedHashMap<String, XYSeries> seriesMap = new LinkedHashMap<>();
    private XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

    /**
     * Erzeugt ein Chart mit entsprechendem Titel und Achsenbeschriftungen.
     * @param title Titel des Charts
     * @param xdesc Beschreibung der x-Achse
     * @param yDesc Beschreibung der y-Achse
     */
    public FunctionGraph(String title, String xdesc, String yDesc) {
        super(null);
        super.setChart(ChartFactory.createXYLineChart(title, xdesc, yDesc, serColl, PlotOrientation.VERTICAL, true, true, true));
        super.getChart().getXYPlot().setRenderer(renderer);
    }
    
    /**
     * Liefert alle keys der Funktionen, die das Chart beinhaltet.
     * @return Menge aller keys
     */
    public Set<String> getSeriesKeys() {
        return seriesMap.keySet();
    }
    
    /**
     * Hinzuf&&uuml;gen einer Funktion. Existiert bereits eine Funktion mit entsprechendem
     * <code>key</code>, werden die Daten dieser Funktion &uuml;berschrieben.
     * 
     * @param samplingPts   St&uuml;tzstellen der Funktion (x_i)
     * @param samplingValues    Funktionswerte an den St&uuml;tzstellen (f(x_i))
     * @param key   eindeutige Bezeichnung der Funktion
     */
    public void addOrUpdateSeries(double[] samplingPts, double[] samplingValues, String key) {
        XYSeries found = seriesMap.get(key);
        if (found != null) {
            found.clear();
        }
        else {
            found = new XYSeries(key);
            serColl.addSeries(found);
            seriesMap.put(key, found);
        }
        
        for (int i=0; i<samplingPts.length; i++) {
            found.add(samplingPts[i], samplingValues[i], true);
        }
    }
    
    /**
     * Hinzuf&&uuml;gen einer Funktion. Existiert bereits eine Funktion mit entsprechendem
     * <code>key</code>, werden die Daten dieser Funktion &uuml;berschrieben.
     * Die Funktion <code>f</code> wird im Intervall <code>[xmin,xmax]</code>
     * &auml;quidistant mit Schrittweite <code>dx</code> abgetastet.
     * 
     * @param f Funktionsbeschreibung
     * @param xmin untere Intervallgrenze des darzustellenden Bereiches
     * @param xmax obere Intervallgrenze des darzustellenden Bereiches
     * @param dx Schrittweite
     * @param key eindeutige Bezeichnung der Funktion
     */
    public void addOrUpdateSeries(ScalarFct f, double xmin, double xmax, double dx, String key) {
        
        int c = 0;
        double x=xmin;
        for (; x<=xmax; x+=dx) {
            c++;
        }
        if (x-dx < xmax) {
            c++;
        }
        
        double[][] values = new double[2][c];
        
        c=0;
        x=xmin;
        for (; x<=xmax; x+=dx) {
            values[0][c] = x;
            values[1][c] = f.getValue(x);
            c++;
        }
        if (x-dx < xmax) {
            values[0][c] = xmax;
            values[1][c] = f.getValue(xmax);
        }
        addOrUpdateSeries(values[0], values[1], key);
    }
    
    /**
     * Entfernt die Funktion mit entsprechendem <code>key</code> aus dem Chart,
     * falls sie vorhanden ist.
     * 
     * @param key 
     */
    public void removeSeries(String key) {
        XYSeries found = seriesMap.get(key);
        if (found != null) {
            serColl.removeSeries(found);
            seriesMap.remove(key);
        }
    }
    
    /**
     * Entfernt alle Funktionen aus dem Chart.
     */
    public void removeAllSeries() {
        this.serColl.removeAllSeries();
        this.seriesMap.clear();
    }
    
    /**
     * Setzt die Farbe der Funktion mit entsprechendem <code>key</code>.
     * @param key   key zur Identifikation der Funktion
     * @param col   Farbe, die zum Plotten der Funktion verwendet werden soll.
     */
    public void setSeriesColor(String key, Color col) {
        int index = getSeriesIndex(key);
        super.getChart().getXYPlot().getRenderer().setSeriesPaint(index, col);
    }
    
    /**
     * Setzt das Shape der Funktion mit entsprechendem <code>key</code>.
     * @param key   key zur Identifikation der Funktion
     * @param shape   Shape, das zum Plotten der Funktion verwendet werden soll.
     */
    public void setSeriesShape(String key, Shape shape) {
        int index = getSeriesIndex(key);
        renderer.setSeriesShape(index, shape);
    }
    
    /**
     * Methode zum Setzen der Zeichnungseigenschaften einer Funktion
     * 
     * @param key   key zur Identifikation der Funktion
     * @param lines <code>true</code>: zeichne Liniensegmente zwischen zwei
     *              Funktionswerten, <code>false</code>: zeichne keine Liniensegmente.
     * @param shapes <code>true</code>: zeichne Shapes an Funktionswerten,
     *               <code>false</code>: zeichne keine Shapes.
     */
    public void setSeriesLinesAndShapesVisible(String key, boolean lines, boolean shapes) {
        int index = getSeriesIndex(key);
        renderer.setSeriesLinesVisible(index, lines);
        renderer.setSeriesShapesVisible(index, shapes);
    }
    
    /**
     * Liefert die hinterlegten St&uuml;tzstellen und Funktionswerte einer Funktion.
     * @param key   key zur Identifikation der Funktion
     * @return <code>double[][]</code>, mit <code>[0][i]</code>: i-te St&uuml;tzstelle,
     *         <code>[1][i]</code>: i-ter Funktionswert
     */
    public double[][] getSeriesData(String key) {
        XYSeries found = seriesMap.get(key);
        if (found == null) {
            return null;
        }
        return found.toArray();
    }
    
    
    private int getSeriesIndex(String key) {
        int index = -1;
        for (int i=0; i<serColl.getSeriesCount(); i++) {
            if (serColl.getSeries(i).getKey().equals(key)) {
                index = i;
                break;
            }
        }
        return index;
    }
    
    /**
     * Legt fest, dass Funktion mit entsprechendem <code>key</code> die anderen
     * Funktionen &uuml;berdecken soll.
     * @param key entsprechender key zur Funktion.  
     */
    public void moveSeriesToTop(String key) {
        int index = getSeriesIndex(key);
        if (index == -1) {
            return;
        }
        serColl.removeAllSeries();
        serColl.addSeries(seriesMap.get(key));
        
        for (Entry<String, XYSeries> e : seriesMap.entrySet()) {
            if (e.getKey().equals(key)) {
                continue;
            }
            serColl.addSeries(e.getValue());
        }
        
    }
    
    /**
     * Zeigt ein Fenster mit dem Chart an. Beim Schliessen des Fensters wird das
     * Programm beendet.
     * 
     * @param width Breite des Fensters in Pixeln
     * @param height H&ouml;he des Fensters in Pixeln
     * @return das Fenster
     */
    public JFrame plot(int width, int height) {
        JFrame f = new JFrame();
        f.add(this);
        f.setSize(width, height);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        return f;
    }
   
//     public void addIVPToPlot(IVP ivp){
//        
//       // This method adds data to a passed function graph
//         
//       ivp.history.forEach((hist) -> {
//            this.addOrUpdateSeries(hist.getDisctPoints(),
//                    hist.getComp1dValues(0), hist.getName());
//        });
//    }
//    
//     
//
//     public void addIVPToPlot(IVP ivp, int[] slice){
//        /*
//        This method adds data to a passed function grahe but only the 
//         ones from the slice array
//        */
//        
//        for(int i : slice){
//            History hist = ivp.history.get(i);
//            
//            this.addOrUpdateSeries(hist.getDisctPoints(),
//                    hist.getComp1dValues(0), hist.getName());
//        }
//    }
     
    
}
