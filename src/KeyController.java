import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * KeyController handles keyboard input and delegates to Presentation navigation methods.
 * <p>
 * SRP: Responsible only for translating key events into presentation actions.
 * DIP: Depends on Presentation, not on any UI component directly.
 * <p>
 * Key mappings:
 * Actions.NEXT_SLIDE     — PgDn, Down arrow, Enter, '+'
 * Actions.PREVIOUS_SLIDE — PgUp, Up arrow, '-'
 * Actions. EXIT           — 'q' or 'Q'
 */
public class KeyController extends KeyAdapter
{

    private final Presentation presentation;

    public KeyController(Presentation presentation)
    {
        this.presentation = presentation;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER -> this.presentation.nextSlide(); // Actions.NEXT_SLIDE
            case KeyEvent.VK_PAGE_UP, KeyEvent.VK_UP -> this.presentation.prevSlide(); // Actions.PREVIOUS_SLIDE
            default -> this.handleCharKey(keyEvent.getKeyChar());
        }
    }

    private void handleCharKey(char keyChar)
    {
        switch (keyChar)
        {
            case '+' -> this.presentation.nextSlide();   // Actions.NEXT_SLIDE
            case '-' -> this.presentation.prevSlide();   // Actions.PREVIOUS_SLIDE
            case 'q', 'Q' -> this.presentation.exit(0); // Actions.EXIT
            default ->
            { /* unhandled key soo basically do nothing */ }
        }
    }
}