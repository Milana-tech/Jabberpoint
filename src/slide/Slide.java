package slide;

import presentation.PresentationComponent;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * slide.Slide represents a single slide in a presentation.
 * <p>
 * SRP: Owns slide state (title, items) and delegates drawing to its items.
 */
public class Slide implements PresentationComponent
{

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    private String title;

    public Slide()
    {
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Convenience method: creates a slide.TextItem and appends it.
     */

    @Override
    public int renderTo(Graphics g, Rectangle area, ImageObserver observer)
    {
        float scale = this.calculateScale(area);
        int y = area.y;

        // Draw only the title — items are decorators stacked on top
        SlideItem titleItem = new TextItem(0, this.getTitle());
        Style titleStyle = Style.getStyle(titleItem.getLevel());
        titleItem.draw(area.x, y, scale, g, titleStyle, observer);
        y += titleItem.getBoundingBox(g, observer, scale, titleStyle).height;

        return y;
    }

    private float calculateScale(Rectangle area)
    {
        return Math.min(
                (float) area.width / (float) WIDTH,
                (float) area.height / (float) HEIGHT
        );
    }
}