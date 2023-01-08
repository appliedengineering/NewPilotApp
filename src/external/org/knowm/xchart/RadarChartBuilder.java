package external.org.knowm.xchart;

import external.org.knowm.xchart.internal.ChartBuilder;

/**
 * @author timmolter
 */
public class RadarChartBuilder extends ChartBuilder<RadarChartBuilder, RadarChart> {

  public RadarChartBuilder() {}

  @Override
  public RadarChart build() {

    return new RadarChart(this);
  }
}
