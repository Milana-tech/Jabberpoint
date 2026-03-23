package accessor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for XMLTags enum.
 * Verifies every constant returns its expected XML tag string.
 */
class XMLTagsTest
{
    @Test
    void showTitle_tagValue()
    {
        assertEquals("showtitle", XMLTags.SHOW_TITLE.getTag());
    }

    @Test
    void slideTitle_tagValue()
    {
        assertEquals("title", XMLTags.SLIDE_TITLE.getTag());
    }

    @Test
    void slide_tagValue()
    {
        assertEquals("slide", XMLTags.SLIDE.getTag());
    }

    @Test
    void item_tagValue()
    {
        assertEquals("item", XMLTags.ITEM.getTag());
    }

    @Test
    void level_tagValue()
    {
        assertEquals("level", XMLTags.LEVEL.getTag());
    }

    @Test
    void kind_tagValue()
    {
        assertEquals("kind", XMLTags.KIND.getTag());
    }

    @Test
    void text_tagValue()
    {
        assertEquals("text", XMLTags.TEXT.getTag());
    }

    @Test
    void image_tagValue()
    {
        assertEquals("image", XMLTags.IMAGE.getTag());
    }
}
