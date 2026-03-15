package slide;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * slide.SlideItem is the abstract base class for all drawable items on a slide.
 * <p>
 * SRP: Owns only the level field and declares the drawing contract.
 * LSP: slide.TextItem and slide.BitmapItem fully substitute slide.SlideItem wherever it is used.
 */
public abstract class SlideItem
{

    public static final int DEFAULT_LEVEL = 0;

    private final int level;

    public SlideItem(int level)
    {
        this.level = level;
    }

    public SlideItem()
    {
        this(DEFAULT_LEVEL);
    }

    public int getLevel()
    {
        return this.level;
    }

    public abstract Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style);

    public abstract void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer);
}