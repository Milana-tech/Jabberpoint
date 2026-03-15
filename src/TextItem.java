import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextItem extends SlideItem
{
    private static final String EMPTYTEXT = "No Text Given";

    private final String text;

    public TextItem(int level, String string)
    {
        super(level);
        this.text = string;
    }

    public TextItem()
    {
        this(0, EMPTYTEXT);
    }

    public String getText()
    {
        return this.text == null ? "" : this.text;
    }

    public AttributedString getAttributedString(Style style, float scale)
    {
        AttributedString attrStr = new AttributedString(this.getText());
        attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, this.text.length());
        return attrStr;
    }

    public Rectangle getBoundingBox(Graphics g, ImageObserver observer,
                                    float scale, Style myStyle)
    {
        List<TextLayout> layouts = this.getLayouts(g, myStyle, scale);
        int xsize = 0, ysize = (int) (myStyle.leading * scale);
        Iterator<TextLayout> iterator = layouts.iterator();
        while (iterator.hasNext())
        {
            TextLayout layout = iterator.next();
            Rectangle2D bounds = layout.getBounds();
            if (bounds.getWidth() > xsize)
            {
                xsize = (int) bounds.getWidth();
            }
            if (bounds.getHeight() > 0)
            {
                ysize += bounds.getHeight();
            }
            ysize += layout.getLeading() + layout.getDescent();
        }
        return new Rectangle((int) (myStyle.indent * scale), 0, xsize, ysize);
    }

    public void draw(int x, int y, float scale, Graphics g,
                     Style myStyle, ImageObserver o)
    {
        if (this.text == null || this.text.length() == 0)
        {
            return;
        }
        List<TextLayout> layouts = this.getLayouts(g, myStyle, scale);
        Point pen = new Point(x + (int) (myStyle.indent * scale),
                y + (int) (myStyle.leading * scale));
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(myStyle.color);
        Iterator<TextLayout> it = layouts.iterator();
        while (it.hasNext())
        {
            TextLayout layout = it.next();
            pen.y += layout.getAscent();
            layout.draw(g2d, pen.x, pen.y);
            pen.y += layout.getDescent();
        }
    }

    private List<TextLayout> getLayouts(Graphics g, Style s, float scale)
    {
        List<TextLayout> layouts = new ArrayList<TextLayout>();
        AttributedString attrStr = this.getAttributedString(s, scale);
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
        float wrappingWidth = (Slide.WIDTH - s.indent) * scale;
        while (measurer.getPosition() < this.getText().length())
        {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            layouts.add(layout);
        }
        return layouts;
    }

    public String toString()
    {
        return "TextItem[" + getLevel() + "," + this.getText() + "]";
    }
}
