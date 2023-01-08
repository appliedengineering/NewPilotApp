package external.org.knowm.xchart;

import external.org.knowm.xchart.internal.ChartBuilder;

/**
 * @author timmolter
 */
public class DialChartBuilder extends ChartBuilder<DialChartBuilder, DialChart> {

  public DialChartBuilder() {}

  @Override
  public DialChart build() {

    return new DialChart(this);
  }
}
