package slide;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * slide.Slide represents a single slide in a presentation.
 * <p>
 * SRP: Owns slide state (title, items) and delegates drawing to its items.
 */
public class Slide
{

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    private String title;
    private final List<SlideItem> slideItems;

    public Slide()
    {
        this.slideItems = new ArrayList<>();
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getSize()
    {
        return this.slideItems.size();
    }

    public void append(SlideItem item)
    {
        this.slideItems.add(item);
    }

    /**
     * Convenience method: creates a slide.TextItem and appends it.
     */
    public void append(int level, String text)
    {
        this.append(new TextItem(level, text));
    }

    public SlideItem getSlideItem(int index)
    {
        return this.slideItems.get(index);
    }

    public List<SlideItem> getSlideItems()
    {
        return this.slideItems;
    }

    public void draw(Graphics g, Rectangle area, ImageObserver view)
    {
        float scale = this.calculateScale(area);
        int y = area.y;

        // Draw the slide title as a level-0 text item

        SlideItem titleItem = new TextItem(0, this.getTitle());
        Style titleStyle = Style.getStyle(titleItem.getLevel());
        titleItem.draw(area.x, y, scale, g, titleStyle, view);
        y += titleItem.getBoundingBox(g, view, scale, titleStyle).height;

        for (SlideItem item : this.slideItems)
        {
            Style style = Style.getStyle(item.getLevel());
            item.draw(area.x, y, scale, g, style, view);
            y += item.getBoundingBox(g, view, scale, style).height;
        }
    }

    private float calculateScale(Rectangle area)
    {
        return Math.min(
                (float) area.width / (float) WIDTH,
                (float) area.height / (float) HEIGHT
        );
    }
}