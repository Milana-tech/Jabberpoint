import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;

/**
 * TextItem renders a single line of text on a slide, with word-wrapping.
 * <p>
 * SRP: Responsible only for measuring and drawing attributed text.
 * LSP: Fully substitutes SlideItem — honours the drawing contract.
 */
public class TextItem extends SlideItem
{

    private static final String EMPTY_TEXT = "No Text Given";

    private final String text;

    public TextItem(int level, String text)
    {
        super(level);
        this.text = text;
    }

    public TextItem()
    {
        this(DEFAULT_LEVEL, EMPTY_TEXT);
    }

    public String getText()
    {
        return this.text == null ? "" : this.text;
    }

    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style)
    {
        List<TextLayout> layouts = this.buildLayouts(g, style, scale);
        int width = 0;
        int height = (int) (style.leading * scale);
        for (TextLayout layout : layouts)
        {
            Rectangle2D bounds = layout.getBounds();
            if (bounds.getWidth() > width)
            {
                width = (int) bounds.getWidth();
            }
            if (bounds.getHeight() > 0)
            {
                height += (int) bounds.getHeight();
            }
            height += (int) (layout.getLeading() + layout.getDescent());
        }
        return new Rectangle((int) (style.indent * scale), 0, width, height);
    }

    @Override
    public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer)
    {
        if (this.isEmpty())
        {
            return;
        }
        List<TextLayout> layouts = this.buildLayouts(g, style, scale);
        Point pen = new Point(
                x + (int) (style.indent * scale),
                y + (int) (style.leading * scale)
        );
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(style.color);
        for (TextLayout layout : layouts)
        {
            pen.y += (int) layout.getAscent();
            layout.draw(g2d, pen.x, pen.y);
            pen.y += (int) layout.getDescent();
        }
    }

    private boolean isEmpty()
    {
        return this.text == null || this.text.isEmpty();
    }

    private List<TextLayout> buildLayouts(Graphics g, Style style, float scale)
    {
        List<TextLayout> layouts = new ArrayList<>();
        AttributedString attributedString = this.buildAttributedString(style, scale);
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(attributedString.getIterator(), frc);
        float wrappingWidth = (Slide.WIDTH - style.indent) * scale;
        while (measurer.getPosition() < this.getText().length())
        {
            layouts.add(measurer.nextLayout(wrappingWidth));
        }
        return layouts;
    }

    private AttributedString buildAttributedString(Style style, float scale)
    {
        AttributedString attributedString = new AttributedString(this.getText());
        attributedString.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, this.getText().length());
        return attributedString;
    }

    @Override
    public String toString()
    {
        return "TextItem[" + getLevel() + "," + this.getText() + "]";
    }
}