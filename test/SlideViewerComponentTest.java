import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.Presentation;
import presentation.PresentationComponent;
import slide.Slide;
import slide.SlideViewerComponent;
import slide.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SlideViewerComponent.
 * <p>
 * Observer pattern role: ConcreteObserver — reacts to onSlideChanged().
 * Tests verify: preferred size, observer registration, repaint trigger, and paintComponent guards.
 * <p>
 * Skipped automatically in headless environments (e.g. CI without a display).
 */
class SlideViewerComponentTest
{
    /**
     * Overrides repaint() so tests can assert it was called
     * without triggering actual Swing repaints on the EDT.
     */
    static class SpyViewer extends SlideViewerComponent
    {
        boolean repaintCalled = false;

        SpyViewer(Presentation p, JFrame f) { super(p, f); }

        @Override
        public void repaint() { repaintCalled = true; }
    }

    private Presentation presentation;
    private JFrame frame;
    private SpyViewer viewer;

    @BeforeEach
    void setUp()
    {
        // SlideViewerComponent and JFrame require a display — skip on headless CI
        Assumptions.assumeFalse(
                GraphicsEnvironment.isHeadless(),
                "Skipped: no display available"
        );
        Style.createStyles();
        presentation = new Presentation();
        presentation.setTitle("Test Show");
        frame = new JFrame();
        viewer = new SpyViewer(presentation, frame);
    }

    // ── Preferred size ───────────────────────────────────────────────────────

    @Test
    void getPreferredSize_widthMatchesSlideWidth()
    {
        assertEquals(Slide.WIDTH, viewer.getPreferredSize().width);
    }

    @Test
    void getPreferredSize_heightMatchesSlideHeight()
    {
        assertEquals(Slide.HEIGHT, viewer.getPreferredSize().height);
    }

    // ── Observer registrati ────────────────────────────────────────────────

    @Test
    void constructor_registersAsObserver_frameTitleUpdatedOnSlideChange()
    {
        // If the viewer is registered, any slide change should trigger onSlideChanged
        // which calls parentFrame.setTitle(presentation.getTitle())
        presentation.setSlideNumber(-1);
        assertEquals("Test Show", frame.getTitle());
    }

    // ── onSlideChanged ───────────────────────────────────────────────────────

    @Test
    void onSlideChanged_updatesFrameTitle()
    {
        viewer.onSlideChanged(null);
        assertEquals("Test Show", frame.getTitle());
    }

    @Test
    void onSlideChanged_withSlide_triggersRepaint()
    {
        Slide slide = new Slide();
        slide.setTitle("S");
        viewer.onSlideChanged(slide);
        assertTrue(viewer.repaintCalled);
    }

    // ── paintComponent ───────────────────────────────────────────────────────

    @Test
    void paintComponent_withNoCurrentSlide_doesNotThrow()
    {
        // slideNumber is -1 (initial) — paintComponent returns early
        BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        assertDoesNotThrow(() -> viewer.paintComponent(g));
    }

    @Test
    void paintComponent_afterSlideChanged_doesNotThrow()
    {
        Slide base = new Slide();
        base.setTitle("Title");
        presentation.append(base);
        // triggers onSlideChanged → currentSlide is set
        presentation.setSlideNumber(0);

        viewer.setSize(Slide.WIDTH, Slide.HEIGHT);
        BufferedImage bi = new BufferedImage(Slide.WIDTH, Slide.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        assertDoesNotThrow(() -> viewer.paintComponent(g));
    }
}
