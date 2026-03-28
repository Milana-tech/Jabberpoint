package slide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.PresentationComponent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import static org.junit.jupiter.api.Assertions.*;

class SlideItemTest
{
    private Slide slide;
    private Graphics graphics;
    private Rectangle area;

    @BeforeEach
    void setUp()
    {
        Style.createStyles();
        slide = new Slide();
        slide.setTitle("Test Title");
        BufferedImage image = new BufferedImage(Slide.WIDTH, Slide.HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics = image.getGraphics();
        area = new Rectangle(0, 0, Slide.WIDTH, Slide.HEIGHT);
    }

    private SlideItem createEmptyItem(PresentationComponent wrapped, int level)
    {
        return new SlideItem(wrapped, level)
        {
            @Override
            public Rectangle getBoundingBox(Graphics g, ImageObserver
                    observer, float scale, Style style)
            {
                return new Rectangle(0, 0, 0, 50);
            }

            @Override
            public void draw(int x, int y, float scale, Graphics g, Style
                    style, ImageObserver observer) {}

            @Override
            public String getContent() { return ""; }
        };
    }

    @Test
    void getWrapped_withWrappedSlide_returnsSlide()
    {
        // Arrange
        SlideItem item = createEmptyItem(slide, 1);

        // Act
        PresentationComponent wrapped = item.getWrapped();

        // Assert
        assertSame(slide, wrapped);
    }

    @Test
    void renderTo_withWrappedSlide_delegatesToWrappedFirst()
    {
        // Arrange
        int slideY = slide.renderTo(graphics, area, null);
        SlideItem item = createEmptyItem(slide, 1);

        // Act
        int itemY = item.renderTo(graphics, area, null);

        // Assert — item starts where slide left off, so itemY >= slideY
        assertTrue(itemY >= slideY);
    }

    @Test
    void renderTo_withWrappedSlide_returnsYGreaterThanWrapped()
    {
        // Arrange
        int slideY = slide.renderTo(graphics, area, null);
        SlideItem item = createEmptyItem(slide, 1);

        // Act
        int itemY = item.renderTo(graphics, area, null);

        // Assert — item added its own height (50) on top
        assertTrue(itemY > slideY);
    }

    @Test
    void getWrapped_withDoubleWrappedSlide_returnsCorrectChain()
    {
        // Arrange
        Slide innerSlide = new Slide();
        TextItem inner = new TextItem(innerSlide, 1, "hi");
        SlideItem outer = new TextItem(inner, 2, "you");

        // Act & Assert — outer wraps inner
        assertSame(inner, outer.getWrapped());
        // inner wraps the original slide
        assertSame(innerSlide, ((SlideItem) outer.getWrapped()).getWrapped());
    }
}