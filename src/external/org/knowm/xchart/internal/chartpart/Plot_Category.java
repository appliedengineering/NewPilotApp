package external.org.knowm.xchart.internal.chartpart;

import external.org.knowm.xchart.CategorySeries;
import external.org.knowm.xchart.style.CategoryStyler;

/**
 * @author timmolter
 */
public class Plot_Category<ST extends CategoryStyler, S extends CategorySeries>
    extends Plot_AxesChart<ST, S> {

  /**
   * Constructor
   *
   * @param chart
   */
  public Plot_Category(Chart<ST, S> chart) {

    super(chart);
    this.plotContent = new PlotContent_Category_Bar<ST, S>(chart);
  }
}
