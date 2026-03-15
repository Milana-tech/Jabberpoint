import javax.swing.*;
import java.awt.*;

public class SlideViewerComponent extends JComponent
{
    private static final long serialVersionUID = 227L;

    private static final Color BGCOLOR = Color.white;
    private static final Color COLOR = Color.black;
    private static final String FONTNAME = "Dialog";
    private static final int FONTSTYLE = Font.BOLD;
    private static final int FONTHEIGHT = 10;
    private static final int XPOS = 1100;
    private static final int YPOS = 20;

    private Slide slide;
    private Font labelFont = null;
    private Presentation presentation = null;
    private JFrame frame = null;

    public SlideViewerComponent(Presentation pres, JFrame frame)
    {
        setBackground(BGCOLOR);
        this.presentation = pres;
        this.labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
        this.frame = frame;
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(Slide.WIDTH, Slide.HEIGHT);
    }

    public void update(Presentation presentation, Slide data)
    {
        if (data == null)
        {
            repaint();
            return;
        }
        this.presentation = presentation;
        this.slide = data;
        repaint();
        this.frame.setTitle(presentation.getTitle());
    }

    public void paintComponent(Graphics g)
    {
        g.setColor(BGCOLOR);
        g.fillRect(0, 0, getSize().width, getSize().height);
        if (this.presentation.getSlideNumber() < 0 || this.slide == null)
        {
            return;
        }
        g.setFont(this.labelFont);
        g.setColor(COLOR);
        g.drawString("Slide " + (1 + this.presentation.getSlideNumber()) + " of " +
                this.presentation.getSize(), XPOS, YPOS);
        Rectangle area = new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
        this.slide.draw(g, area, this);
    }
}
