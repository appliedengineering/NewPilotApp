package external.org.knowm.xchart.internal.chartpart;

import external.org.knowm.xchart.XYSeries;
import external.org.knowm.xchart.style.XYStyler;

/**
 * @author timmolter
 */
public class Plot_XY<ST extends XYStyler, S extends XYSeries> extends Plot_AxesChart<ST, S> {

  /**
   * Constructor
   *
   * @param chart
   */
  public Plot_XY(Chart<ST, S> chart) {

    super(chart);
    this.plotContent = new PlotContent_XY<ST, S>(chart);
  }
}
