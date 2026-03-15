import javax.swing.*;
import java.awt.*;
import java.io.Serial;

/**
 * SlideViewerComponent renders the current slide inside the application window.
 * <p>
 * Observer pattern role: Concrete Observer reacts to slide changes via onSlideChanged().
 * SRP: Responsible only for rendering a slide onto the screen.
 * DIP: Registers itself with Presentation via the PresentationObserver interface
 * Presentation never references SlideViewerComponent directly.
 */
public class SlideViewerComponent extends JComponent implements PresentationObserver
{

    @Serial
    private static final long serialVersionUID = 227L;

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color LABEL_COLOR = Color.BLACK;
    private static final String LABEL_FONT_NAME = "Dialog";
    private static final int LABEL_FONT_STYLE = Font.BOLD;
    private static final int LABEL_FONT_SIZE = 10;
    private static final int SLIDE_LABEL_X = 1100;
    private static final int SLIDE_LABEL_Y = 20;

    private Slide currentSlide;
    private final Font slideNumberFont;
    private final Presentation presentation;
    private final JFrame parentFrame;

    public SlideViewerComponent(Presentation presentation, JFrame parentFrame)
    {
        this.presentation = presentation;
        this.parentFrame = parentFrame;
        this.slideNumberFont = new Font(LABEL_FONT_NAME, LABEL_FONT_STYLE, LABEL_FONT_SIZE);
        setBackground(BACKGROUND_COLOR);

        // Observer pattern: register this component as an observer
        presentation.addObserver(this);
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(Slide.WIDTH, Slide.HEIGHT);
    }

    /**
     * Called by Presentation whenever the current slide changes.
     * Observer pattern: update method.
     */
    @Override
    public void onSlideChanged(Slide slide)
    {
        this.currentSlide = slide;
        repaint();
        this.parentFrame.setTitle(this.presentation.getTitle());
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, getSize().width, getSize().height);

        if (this.presentation.getSlideNumber() < 0 || this.currentSlide == null)
        {
            return;
        }

        this.drawSlideNumberLabel(g);
        Rectangle drawArea = new Rectangle(0, SLIDE_LABEL_Y, getWidth(), getHeight() - SLIDE_LABEL_Y);
        this.currentSlide.draw(g, drawArea, this);
    }

    private void drawSlideNumberLabel(Graphics g)
    {
        g.setFont(this.slideNumberFont);
        g.setColor(LABEL_COLOR);
        String label = "Slide " + (1 + this.presentation.getSlideNumber())
                + " of " + this.presentation.getSize();
        g.drawString(label, SLIDE_LABEL_X, SLIDE_LABEL_Y);
    }
}