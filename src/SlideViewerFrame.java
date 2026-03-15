import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * SlideViewerFrame is the main application window.
 * <p>
 * SRP: Responsible only for constructing and configuring the top-level window.
 */
public class SlideViewerFrame extends JFrame
{
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    private static final long serialVersionUID = 3227L;

    private static final String APPLICATION_TITLE = "JabberPoint 1.6 - OU";

    public SlideViewerFrame(String title, Presentation presentation)
    {
        super(title);
        SlideViewerComponent viewer = new SlideViewerComponent(presentation, this);
        this.setupWindow(viewer, presentation);
    }

    private void setupWindow(SlideViewerComponent viewer, Presentation presentation)
    {
        setTitle(APPLICATION_TITLE);
        getContentPane().add(viewer);
        addKeyListener(new KeyController(presentation));
        setMenuBar(new MenuController(this, presentation));
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                presentation.exit(0);
            }
        });
        setSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
    }
}