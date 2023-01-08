package external.org.knowm.xchart;

import external.org.knowm.xchart.internal.chartpart.RenderableSeries;
import external.org.knowm.xchart.internal.chartpart.RenderableSeries.LegendRenderType;
import external.org.knowm.xchart.internal.series.AxesChartSeriesNumericalNoErrorBars;

/**
 * A Series containing X and Y data to be plotted on a Chart
 *
 * @author timmolter
 */
public class XYSeries extends AxesChartSeriesNumericalNoErrorBars {

  private XYSeriesRenderStyle xySeriesRenderStyle = null;
  // smooth curve
  private boolean smooth;

  /**
   * Constructor
   *
   * @param name
   * @param xData
   * @param yData
   * @param errorBars
   */
  public XYSeries(
      String name, double[] xData, double[] yData, double[] errorBars, DataType axisType) {

    super(name, xData, yData, errorBars, axisType);
  }

  public XYSeriesRenderStyle getXYSeriesRenderStyle() {

    return xySeriesRenderStyle;
  }

  public XYSeries setXYSeriesRenderStyle(XYSeriesRenderStyle chartXYSeriesRenderStyle) {

    this.xySeriesRenderStyle = chartXYSeriesRenderStyle;
    return this;
  }

  @Override
  public LegendRenderType getLegendRenderType() {

    return xySeriesRenderStyle.getLegendRenderType();
  }

  // TODO what is this again?
  public boolean isSmooth() {
    return smooth;
  }

  public void setSmooth(boolean smooth) {
    this.smooth = smooth;
  }

  public enum XYSeriesRenderStyle implements RenderableSeries {
    Line(LegendRenderType.Line),

    Area(LegendRenderType.Line),

    Step(LegendRenderType.Line),

    StepArea(LegendRenderType.Line),

    PolygonArea(LegendRenderType.Box),

    Scatter(LegendRenderType.Scatter);

    private final LegendRenderType legendRenderType;

    XYSeriesRenderStyle(LegendRenderType legendRenderType) {

      this.legendRenderType = legendRenderType;
    }

    @Override
    public LegendRenderType getLegendRenderType() {

      return legendRenderType;
    }
  }
}
