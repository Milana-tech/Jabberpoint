package slide;

import presentation.PresentationComponent;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * slide.SlideItem is the abstract base class for all drawable items on a slide.
 * <p>
 * SRP: Owns only the level field and declares the drawing contract.
 * LSP: slide.TextItem and slide.BitmapItem fully substitute slide.SlideItem wherever it is used.
 */
public abstract class SlideItem implements PresentationComponent
{
    public static final int DEFAULT_LEVEL = 0;

    // The components that are decorated

    private final PresentationComponent wrapped;

    private final int level;

    public SlideItem(PresentationComponent wrapped, int level)
    {
        this.wrapped = wrapped;
        this.level = level;
    }

    public SlideItem(int level)
    {
        this.wrapped = null;
        this.level = level;
    }

    @Override
    public int renderTo(Graphics g, Rectangle area, ImageObserver observer)
    {
        int y = (this.wrapped != null) ? this.wrapped.renderTo(g, area, observer) : area.y;
        float scale = Math.min(
                (float) area.width / Slide.WIDTH,
                (float) area.height / Slide.HEIGHT
        );
        Style style = Style.getStyle(this.level);
        this.draw(area.x, y, scale, g, style, observer);
        y += this.getBoundingBox(g, observer, scale, style).height;
        return y;
    }


    public int getLevel()
    {
        return this.level;
    }

    public abstract Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style);

    public abstract void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer);
}