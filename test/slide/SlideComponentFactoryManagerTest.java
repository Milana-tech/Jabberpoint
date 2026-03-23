import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slide.BitmapItem;
import slide.Slide;
import slide.SlideComponentFactoryManager;
import slide.SlideItem;
import slide.Style;
import slide.TextItem;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SlideComponentFactoryManager.
 * Covers: factory creation, type resolution, and unknown type handling.
 * Validates the Factory Method pattern dispatch.
 */
class SlideComponentFactoryManagerTest
{
    private SlideComponentFactoryManager manager;
    private Slide base;

    @BeforeEach
    void setUp()
    {
        Style.createStyles();
        manager = new SlideComponentFactoryManager();
        base = new Slide();
        base.setTitle("Base");
    }

    // ── createComponent ──────────────────────────────────────────────────────

    @Test
    void createComponent_text_returnsTextItem()
    {
        SlideItem item = manager.createComponent(base, "text", 1, "Hello");
        assertInstanceOf(TextItem.class, item);
    }

    @Test
    void createComponent_text_setsLevel()
    {
        SlideItem item = manager.createComponent(base, "text", 2, "Hello");
        assertEquals(2, item.getLevel());
    }

    @Test
    void createComponent_text_setsContent()
    {
        TextItem item = (TextItem) manager.createComponent(base, "text", 1, "Hello");
        assertEquals("Hello", item.getText());
    }

    @Test
    void createComponent_text_wrapsBase()
    {
        SlideItem item = manager.createComponent(base, "text", 1, "Hello");
        assertSame(base, item.getWrapped());
    }

    @Test
    void createComponent_image_returnsBitmapItem()
    {
        SlideItem item = manager.createComponent(base, "image", 1, "photo.jpg");
        assertInstanceOf(BitmapItem.class, item);
    }

    @Test
    void createComponent_image_setsContent()
    {
        BitmapItem item = (BitmapItem) manager.createComponent(base, "image", 1, "photo.jpg");
        assertEquals("photo.jpg", item.getName());
    }

    @Test
    void createComponent_unknownType_throwsIllegalArgumentException()
    {
        assertThrows(IllegalArgumentException.class,
                () -> manager.createComponent(base, "unknown", 1, "content"));
    }

    // ── getType ──────────────────────────────────────────────────────────────

    @Test
    void getType_textItem_returnsText()
    {
        TextItem item = new TextItem(1, "Hello");
        assertEquals("text", manager.getType(item));
    }

    @Test
    void getType_bitmapItem_returnsImage()
    {
        BitmapItem item = new BitmapItem(1, "photo.jpg");
        assertEquals("image", manager.getType(item));
    }
}
