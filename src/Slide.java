import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Vector;

public class Slide
{
    public final static int WIDTH = 1200;
    public final static int HEIGHT = 800;
    protected String title;
    protected Vector<SlideItem> items;

    public Slide()
    {
        this.items = new Vector<SlideItem>();
    }

    public void append(SlideItem anItem)
    {
        this.items.addElement(anItem);
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String newTitle)
    {
        this.title = newTitle;
    }

    public void append(int level, String message)
    {
        this.append(new TextItem(level, message));
    }

    public SlideItem getSlideItem(int number)
    {
        return this.items.elementAt(number);
    }

    public Vector<SlideItem> getSlideItems()
    {
        return this.items;
    }

    public int getSize()
    {
        return this.items.size();
    }

    public void draw(Graphics g, Rectangle area, ImageObserver view)
    {
        float scale = this.getScale(area);
        int y = area.y;

        SlideItem slideItem = new TextItem(0, this.getTitle());
        Style style = Style.getStyle(slideItem.getLevel());
        slideItem.draw(area.x, y, scale, g, style, view);
        y += slideItem.getBoundingBox(g, view, scale, style).height;
        for (int number = 0; number < this.getSize(); number++)
        {
            slideItem = this.getSlideItems().elementAt(number);
            style = Style.getStyle(slideItem.getLevel());
            slideItem.draw(area.x, y, scale, g, style, view);
            y += slideItem.getBoundingBox(g, view, scale, style).height;
        }
    }

    private float getScale(Rectangle area)
    {
        return Math.min(((float) area.width) / ((float) WIDTH), ((float) area.height) / ((float) HEIGHT));
    }
}
