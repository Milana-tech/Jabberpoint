package slide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.PresentationComponent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class TextItemTest
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
    void
    constructor_withWrappedComponent_getWrapped_returnsWrappedSlide()
    {
        // Arrange
        TextItem textItem = new TextItem(slide, 1, "Hello");

        // Act
        PresentationComponent wrapped = textItem.getWrapped();

        // Assert
        assertSame(slide, wrapped);
    }

    @Test
    void constructor_withWrappedComponent_setsLevelCorrectly()
    {
        // Arrange & Act
        TextItem textItem = new TextItem(slide, 2, "Hello");

        // Assert
        assertEquals(2, textItem.getLevel());
    }

    @Test
    void constructor_withWrappedComponent_setsTextCorrectly()
    {
        // Arrange & Act
        TextItem textItem = new TextItem(slide, 1, "Hello");

        // Assert
        assertEquals("Hello", textItem.getText());
    }

    @Test
    void renderTo_withWrappedSlide_returnsYGreaterThanSlideRenderTo()
    {
        // Arrange
        int slideY = slide.renderTo(graphics, area, null);
        TextItem textItem = new TextItem(slide, 1, "Hello");

        // Act
        int itemY = textItem.renderTo(graphics, area, null);

        // Assert
        assertTrue(itemY > slideY);
    }

    @Test
    void renderTo_secondTextItem_returnsYGreaterThanFirstTextItem()
    {
        // Arrange
        TextItem first = new TextItem(slide, 1, "First");
        TextItem second = new TextItem(first, 2, "Second");

        // Act
        int firstY = first.renderTo(graphics, area, null);
        int secondY = second.renderTo(graphics, area, null);

        // Assert
        assertTrue(secondY > firstY);
    }
}