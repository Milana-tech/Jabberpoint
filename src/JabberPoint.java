import accessor.Accessor;
import accessor.XMLAccessor;
import presentation.Presentation;
import slide.SlideViewerFrame;
import slide.Style;

import javax.swing.*;
import java.io.IOException;

/**
 * JabberPoint is the application entry point.
 * <p>
 * SRP: Responsible only for bootstrapping the application — wiring styles,
 * presentation, and viewer, then loading the initial content.
 */
public class JabberPoint
{

    private static final String APPLICATION_TITLE = "JabberPoint 1.6 - OU";
    private static final String STARTUP_ERROR_TITLE = "JabberPoint Error";

    public static void main(String[] args)
    {
        Style.createStyles();
        Presentation presentation = new Presentation();
        new SlideViewerFrame(APPLICATION_TITLE, presentation);

        try
        {
            loadInitialPresentation(presentation, args);
            presentation.setSlideNumber(0);
        }
        catch (IOException e)
        {
            showStartupError(e);
        }
    }

    private static void loadInitialPresentation(Presentation presentation, String[] args) throws IOException
    {
        if (args.length == 0)
        {
            Accessor.getDemoAccessor().loadPresentationFromFile(presentation, "");
        }
        else
        {
            new XMLAccessor().loadPresentationFromFile(presentation, args[0]);
        }
    }

    private static void showStartupError(IOException e)
    {
        JOptionPane.showMessageDialog(
                null,
                "IO Error: " + e.getMessage(),
                STARTUP_ERROR_TITLE,
                JOptionPane.ERROR_MESSAGE
        );
    }
}