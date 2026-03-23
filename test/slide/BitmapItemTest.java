import org.junit.jupiter.api.Test;
import presentation.PresentationComponent;
import slide.BitmapItem;
import slide.Slide;

import static org.junit.jupiter.api.Assertions.*;

public class BitmapItemTest
{
    @Test
    void constructor_withLevelAndName_setsNameCorrectly()
    {
        BitmapItem item = new BitmapItem(1, "test.jpg");

        assertEquals("test.jpg", item.getName());
    }

    @Test
    void constructor_withLevelAndName_setsLevelCorrectly()
    {
        BitmapItem item = new BitmapItem(1, "test.jpg");

        assertEquals(1, item.getLevel());
    }

    @Test
    void
    constructor_withWrappedComponent_getWrapped_returnsWrappedSlide()
    {
        // Arrange
        Slide slide = new Slide();
        BitmapItem bitmapItem = new BitmapItem(slide, 1, "test.jpg");

        // Act
        PresentationComponent wrapped = bitmapItem.getWrapped();

        // Assert
        assertSame(slide, wrapped);
    }

    @Test
    void constructor_withWrappedComponent_setsLevelCorrectly()
    {
        // Arrange & Act
        BitmapItem bitmapItem = new BitmapItem(new Slide(), 2,
                "test.jpg");

        // Assert
        assertEquals(2, bitmapItem.getLevel());
    }

    @Test
    void constructor_withWrappedComponent_setsNameCorrectly()
    {
        // Arrange & Act
        BitmapItem bitmapItem = new BitmapItem(new Slide(), 1,
                "test.jpg");

        // Assert
        assertEquals("test.jpg", bitmapItem.getName());
    }
}