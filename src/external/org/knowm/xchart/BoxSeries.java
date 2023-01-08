package external.org.knowm.xchart;

import java.util.List;
import external.org.knowm.xchart.internal.chartpart.RenderableSeries.LegendRenderType;
import external.org.knowm.xchart.internal.series.AxesChartSeriesCategory;

public class BoxSeries extends AxesChartSeriesCategory {

  public BoxSeries(
      String name,
      List<?> xData,
      List<? extends Number> yData,
      List<? extends Number> extraValues,
      DataType xAxisDataType) {

    super(name, xData, yData, extraValues, xAxisDataType);
  }

  @Override
  public LegendRenderType getLegendRenderType() {

    return null;
  }
}
