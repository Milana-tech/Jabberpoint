import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import accessor.XMLAccessor;
import presentation.Presentation;
import presentation.PresentationComponent;
import slide.Slide;
import slide.SlideItem;
import slide.Style;
import slide.TextItem;
import slide.BitmapItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for XMLAccessor.
 * Covers save (full XML output) and load round-trip.
 */
class XMLAccessorTest
{
    @TempDir
    Path tempDir;

    private XMLAccessor accessor;

    @BeforeEach
    void setUp()
    {
        Style.createStyles();
        accessor = new XMLAccessor();
    }

    // ── Helper ───────────────────────────────────────────────────────────────

    /** Builds a minimal Presentation: one slide with one TextItem decorator. */
    private Presentation buildPresentation()
    {
        Presentation p = new Presentation();
        p.setTitle("Test Presentation");

        Slide base = new Slide();
        base.setTitle("Slide One");
        PresentationComponent chain = new TextItem(base, 1, "Hello World");
        p.append(chain);
        return p;
    }

    private String saveAndRead(Presentation p, String filename) throws IOException
    {
        File out = tempDir.resolve(filename).toFile();
        accessor.savePresentationToFile(p, out.getAbsolutePath());
        return Files.readString(out.toPath());
    }

    // ── Save tests ───────────────────────────────────────────────────────────

    @Test
    void save_createsFile() throws IOException
    {
        File out = tempDir.resolve("output.xml").toFile();
        accessor.savePresentationToFile(buildPresentation(), out.getAbsolutePath());
        assertTrue(out.exists());
    }

    @Test
    void save_containsXmlDeclaration() throws IOException
    {
        assertTrue(saveAndRead(buildPresentation(), "out.xml").contains("<?xml version=\"1.0\"?>"));
    }

    @Test
    void save_containsPresentationTitle() throws IOException
    {
        assertTrue(saveAndRead(buildPresentation(), "out.xml").contains("Test Presentation"));
    }

    @Test
    void save_containsSlideTitle() throws IOException
    {
        assertTrue(saveAndRead(buildPresentation(), "out.xml").contains("Slide One"));
    }

    @Test
    void save_containsTextItemContent() throws IOException
    {
        assertTrue(saveAndRead(buildPresentation(), "out.xml").contains("Hello World"));
    }

    @Test
    void save_textItem_hasKindText() throws IOException
    {
        assertTrue(saveAndRead(buildPresentation(), "out.xml").contains("kind=\"text\""));
    }

    @Test
    void save_textItem_hasCorrectLevel() throws IOException
    {
        assertTrue(saveAndRead(buildPresentation(), "out.xml").contains("level=\"1\""));
    }

    @Test
    void save_bitmapItem_hasKindImage() throws IOException
    {
        Presentation p = new Presentation();
        p.setTitle("P");
        Slide base = new Slide();
        base.setTitle("S");
        p.append(new BitmapItem(base, 2, "photo.jpg"));

        String content = saveAndRead(p, "bitmap.xml");
        assertTrue(content.contains("kind=\"image\""));
        assertTrue(content.contains("photo.jpg"));
        assertTrue(content.contains("level=\"2\""));
    }

    @Test
    void save_multipleSlides_preservesOrder() throws IOException
    {
        Presentation p = new Presentation();
        p.setTitle("Multi");
        Slide s1 = new Slide(); s1.setTitle("First");
        Slide s2 = new Slide(); s2.setTitle("Second");
        p.append(s1);
        p.append(s2);

        String content = saveAndRead(p, "multi.xml");
        assertTrue(content.indexOf("First") < content.indexOf("Second"));
    }

    @Test
    void save_emptyPresentation_doesNotThrow()
    {
        Presentation p = new Presentation();
        p.setTitle("Empty");
        File out = tempDir.resolve("empty.xml").toFile();
        assertDoesNotThrow(() -> accessor.savePresentationToFile(p, out.getAbsolutePath()));
    }

    // ── Round-trip test ──────────────────────────────────────────────────────

    @Test
    void saveAndLoad_roundTrip_preservesTitleAndSlide() throws IOException
    {
        // Save
        Presentation original = buildPresentation();
        File out = tempDir.resolve("roundtrip.xml").toFile();
        accessor.savePresentationToFile(original, out.getAbsolutePath());

        // Load
        Presentation loaded = new Presentation();
        accessor.loadPresentationFromFile(loaded, out.getAbsolutePath());

        assertEquals("Test Presentation", loaded.getTitle());
        assertEquals(1, loaded.getSize());
    }

    @Test
    void saveAndLoad_roundTrip_preservesSlideTitle() throws IOException
    {
        Presentation original = buildPresentation();
        File out = tempDir.resolve("roundtrip2.xml").toFile();
        accessor.savePresentationToFile(original, out.getAbsolutePath());

        Presentation loaded = new Presentation();
        accessor.loadPresentationFromFile(loaded, out.getAbsolutePath());

        // Unwrap chain to reach the base Slide
        PresentationComponent component = loaded.getSlide(0);
        while (component instanceof SlideItem si) { component = si.getWrapped(); }
        assertEquals("Slide One", ((Slide) component).getTitle());
    }

    @Test
    void saveAndLoad_roundTrip_preservesTextItem() throws IOException
    {
        Presentation original = buildPresentation();
        File out = tempDir.resolve("roundtrip3.xml").toFile();
        accessor.savePresentationToFile(original, out.getAbsolutePath());

        Presentation loaded = new Presentation();
        accessor.loadPresentationFromFile(loaded, out.getAbsolutePath());

        // Outermost decorator must be a TextItem with the correct text
        assertInstanceOf(TextItem.class, loaded.getSlide(0));
        assertEquals("Hello World", ((TextItem) loaded.getSlide(0)).getText());
    }
}
