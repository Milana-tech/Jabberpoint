package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.Presentation;

import java.awt.Panel;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for KeyController.
 * Verifies all key mappings are translated into the correct Presentation actions.
 * Uses SpyPresentation to intercept calls without triggering System.exit.
 */
class KeyControllerTest
{
    /**
     * Records calls to nextSlide, prevSlide, and exit so tests can assert which action was triggered.
     * Overrides exit() to prevent System.exit(0) from terminating the test runner.
     */
    static class SpyPresentation extends Presentation
    {
        int nextSlideCount = 0;
        int prevSlideCount = 0;
        int exitCount = 0;
        int lastExitStatus = -1;

        @Override public void nextSlide() { nextSlideCount++; }
        @Override public void prevSlide() { prevSlideCount++; }
        @Override public void exit(int status) { exitCount++; lastExitStatus = status; }
    }

    private SpyPresentation presentation;
    private KeyController controller;
    private Panel source;

    @BeforeEach
    void setUp()
    {
        presentation = new SpyPresentation();
        controller = new KeyController(presentation);
        source = new Panel();
    }

    private KeyEvent codeEvent(int keyCode)
    {
        return new KeyEvent(source, KeyEvent.KEY_PRESSED, 0L, 0, keyCode, KeyEvent.CHAR_UNDEFINED);
    }

    private KeyEvent charEvent(char keyChar)
    {
        return new KeyEvent(source, KeyEvent.KEY_PRESSED, 0L, 0, KeyEvent.VK_UNDEFINED, keyChar);
    }

    // ── NEXT_SLIDE ───────────────────────────────────────────────────────────

    @Test
    void pgDn_callsNextSlide()
    {
        controller.keyPressed(codeEvent(KeyEvent.VK_PAGE_DOWN));
        assertEquals(1, presentation.nextSlideCount);
    }

    @Test
    void downArrow_callsNextSlide()
    {
        controller.keyPressed(codeEvent(KeyEvent.VK_DOWN));
        assertEquals(1, presentation.nextSlideCount);
    }

    @Test
    void enter_callsNextSlide()
    {
        controller.keyPressed(codeEvent(KeyEvent.VK_ENTER));
        assertEquals(1, presentation.nextSlideCount);
    }

    @Test
    void plus_callsNextSlide()
    {
        controller.keyPressed(charEvent('+'));
        assertEquals(1, presentation.nextSlideCount);
    }

    // ── PREVIOUS_SLIDE ───────────────────────────────────────────────────────

    @Test
    void pgUp_callsPrevSlide()
    {
        controller.keyPressed(codeEvent(KeyEvent.VK_PAGE_UP));
        assertEquals(1, presentation.prevSlideCount);
    }

    @Test
    void upArrow_callsPrevSlide()
    {
        controller.keyPressed(codeEvent(KeyEvent.VK_UP));
        assertEquals(1, presentation.prevSlideCount);
    }

    @Test
    void minus_callsPrevSlide()
    {
        controller.keyPressed(charEvent('-'));
        assertEquals(1, presentation.prevSlideCount);
    }

    // ── EXIT ─────────────────────────────────────────────────────────────────

    @Test
    void q_callsExitWithStatus0()
    {
        controller.keyPressed(charEvent('q'));
        assertEquals(1, presentation.exitCount);
        assertEquals(0, presentation.lastExitStatus);
    }

    @Test
    void Q_callsExitWithStatus0()
    {
        controller.keyPressed(charEvent('Q'));
        assertEquals(1, presentation.exitCount);
        assertEquals(0, presentation.lastExitStatus);
    }

    // ── Unknown key ──────────────────────────────────────────────────────────

    @Test
    void unknownKeyCode_doesNothing()
    {
        controller.keyPressed(codeEvent(KeyEvent.VK_ESCAPE));
        assertEquals(0, presentation.nextSlideCount);
        assertEquals(0, presentation.prevSlideCount);
        assertEquals(0, presentation.exitCount);
    }

    @Test
    void unknownChar_doesNothing()
    {
        controller.keyPressed(charEvent('x'));
        assertEquals(0, presentation.nextSlideCount);
        assertEquals(0, presentation.prevSlideCount);
        assertEquals(0, presentation.exitCount);
    }
}
