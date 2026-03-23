import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slide.Slide;
import slide.Style;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class SlideTest
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

    @Test
    void renderTo_withTitle_returnsYGreaterThanStartY()
    {
        // Arrange
        int startY = area.y;
        // Act
        int resultY = slide.renderTo(graphics, area, null);
        // Assert
        assertTrue(resultY > startY);
    }

    @Test
    void renderTo_calledTwice_returnsSameY()
    {
        // Arrange - rendering should be deterministic
        // Act
        int firstY = slide.renderTo(graphics, area, null);
        int secondY = slide.renderTo(graphics, area, null);
        // Assert
        assertEquals(firstY, secondY);
    }
}