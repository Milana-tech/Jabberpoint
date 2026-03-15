package controller;

import presentation.Presentation;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * controller.KeyController handles keyboard input and delegates to presentation.Presentation navigation methods.
 * <p>
 * SRP: Responsible only for translating key events into presentation actions.
 * DIP: Depends on presentation.Presentation, not on any UI component directly.
 * <p>
 * Key mappings:
 * controller.Actions.NEXT_SLIDE     — PgDn, Down arrow, Enter, '+'
 * controller.Actions.PREVIOUS_SLIDE — PgUp, Up arrow, '-'
 * controller.Actions. EXIT           — 'q' or 'Q'
 */
public class KeyController extends KeyAdapter
{

    private final Presentation presentation;

    public KeyController(Presentation presentation)
    {
        this.presentation = presentation;
    }

    public void keyPressed(KeyEvent keyEvent)
    {
        Actions action = this.resolveKeyAction(keyEvent);
        if (action != null)
        {
            this.executeAction(action);
        }
    }

    private Actions resolveKeyAction(KeyEvent keyEvent)
    {
        return switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER -> Actions.NEXT_SLIDE;
            case KeyEvent.VK_PAGE_UP, KeyEvent.VK_UP -> Actions.PREVIOUS_SLIDE;
            default ->  this.resolveCharAction(keyEvent.getKeyChar());
        };
    }

    private Actions resolveCharAction(char keyChar)
    {
        return switch (keyChar)
        {
            case '+' -> Actions.NEXT_SLIDE;
            case '-' -> Actions.PREVIOUS_SLIDE;
            case 'q', 'Q' -> Actions.EXIT;
            default -> null;
        };
    }

    private void executeAction(Actions action)
    {
        switch (action)
        {
            case NEXT_SLIDE ->  this.presentation.nextSlide();
            case PREVIOUS_SLIDE ->  this.presentation.prevSlide();
            case EXIT ->  this.presentation.exit(0);
            default ->
            {
            }
        }
    }
}