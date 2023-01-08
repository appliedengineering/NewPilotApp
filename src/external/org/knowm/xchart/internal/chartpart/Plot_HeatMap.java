package external.org.knowm.xchart.internal.chartpart;

import external.org.knowm.xchart.HeatMapSeries;
import external.org.knowm.xchart.style.HeatMapStyler;

/**
 * @author Mr14huashao
 */
public class Plot_HeatMap<ST extends HeatMapStyler, S extends HeatMapSeries>
    extends Plot_AxesChart<ST, S> {

  /**
   * Constructor
   *
   * @param chart
   */
  public Plot_HeatMap(Chart<ST, S> chart) {

    super(chart);
    this.plotContent = new PlotContent_HeatMap<ST, S>(chart);
  }
}
