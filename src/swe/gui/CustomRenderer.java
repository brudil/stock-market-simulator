package swe.gui;

import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer3D;

import java.awt.*;

/**
 * A custom renderer that returns a different color for each item in a single series.
 */
class CustomRenderer extends StackedBarRenderer3D {

    /** The colors. */
    private Paint[] colors;

    /**
     * Creates a new renderer.
     *
     * @param colors  the colors.
     */
    public CustomRenderer(final Paint[] colors) {
        this.colors = colors;
    }

    /**
     * Returns the paint for an item.  Overrides the default behaviour inherited from
     * AbstractSeriesRenderer.
     *
     * @param row  the series.
     * @param column  the category.
     *
     * @return The item color.
     */
    public Paint getItemPaint(final int row, final int column) {
        return this.colors[column % this.colors.length];
    }
}