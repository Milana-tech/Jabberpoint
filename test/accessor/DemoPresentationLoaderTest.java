package accessor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.Presentation;
import presentation.PresentationComponent;
import slide.Slide;
import slide.SlideItem;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DemoPresentationLoader.
 * Covers: three demo slides are built correctly; save throws UnsupportedOperationException.
 */
class DemoPresentationLoaderTest
{
    private DemoPresentationLoader loader;
    private Presentation presentation;

    /** Unwraps the decorator chain to reach the base Slide. */
    private Slide getBaseSlide(PresentationComponent component)
    {
        while (component instanceof SlideItem si)
        {
            component = si.getWrapped();
        }
        return (Slide) component;
    }

    private int
    countItems(PresentationComponent
                       component)
    {
        int count = 0;
        while (component instanceof SlideItem si)
        {
            count++;
            component = si.getWrapped();
        }
        return count;
    }

    @BeforeEach
    void setUp()
    {
        loader = new DemoPresentationLoader();
        presentation = new Presentation();
    }

    @Test
    void load_setsTitle()
    {
        loader.loadPresentationFromFile(presentation, "ignored");
        assertNotNull(presentation.getTitle());
    }

    @Test
    void load_addsThreeSlides()
    {
        loader.loadPresentationFromFile(presentation, "ignored");
        assertEquals(3, presentation.getSize());
    }

    @Test
    void load_firstSlideTitle_isJabberPoint()
    {
        loader.loadPresentationFromFile(presentation, "ignored");
        Slide base = getBaseSlide(presentation.getSlide(0));
        assertEquals("JabberPoint", base.getTitle());
    }

    @Test
    void load_secondSlideTitle_isDemonstrationOfLevels()
    {
        loader.loadPresentationFromFile(presentation, "ignored");
        Slide base = getBaseSlide(presentation.getSlide(1));
        assertEquals("Demonstration of levels and styles", base.getTitle());
    }

    @Test
    void load_thirdSlideTitle_isTheThirdSlide()
    {
        loader.loadPresentationFromFile(presentation, "ignored");
        Slide base = getBaseSlide(presentation.getSlide(2));
        assertEquals("The third slide", base.getTitle());
    }

    @Test
    void load_firstSlide_outerItemIsSlideItem()
    {
        loader.loadPresentationFromFile(presentation, "ignored");
        assertInstanceOf(SlideItem.class, presentation.getSlide(0));
    }

    @Test
    void load_filenameIsIgnored()
    {
        // The demo loader ignores the filename — both calls produce identical slide counts
        Presentation p2 = new Presentation();
        loader.loadPresentationFromFile(presentation, "any.xml");
        loader.loadPresentationFromFile(p2, "other.xml");
        assertEquals(presentation.getSize(), p2.getSize());
    }

    @Test
    void load_firstSlide_hasEightItems()
    {
        loader.loadPresentationFromFile(presentation, "ignored");
        assertEquals(8,
                countItems(presentation.getSlide(0)));
    }


    @Test
    void save_throwsUnsupportedOperationException()
    {
        assertThrows(UnsupportedOperationException.class,
                () -> loader.savePresentationToFile(presentation, "file.xml"));
    }
}
