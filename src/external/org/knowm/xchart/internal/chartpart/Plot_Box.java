package external.org.knowm.xchart.internal.chartpart;

import external.org.knowm.xchart.BoxSeries;
import external.org.knowm.xchart.style.BoxStyler;

public class Plot_Box<ST extends BoxStyler, S extends BoxSeries> extends Plot_AxesChart<ST, S> {

  public Plot_Box(Chart<ST, S> chart) {

    super(chart);
    this.plotContent = new PlotContent_Box<ST, S>(chart);
  }
}
