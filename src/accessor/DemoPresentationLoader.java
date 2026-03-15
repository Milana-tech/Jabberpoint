package accessor;

import presentation.Presentation;
import slide.BitmapItem;
import slide.Slide;

import java.io.IOException;

/**
 * accessor.DemoPresentationLoader builds the built-in demo presentation in memory.
 * <p>
 * SRP: Responsible only for constructing demo slide content
 * IO concerns stay in accessor.XMLAccessor; demo content lives here.
 */
public class DemoPresentationLoader extends Accessor
{

    @Override
    public void loadPresentationFromFile(Presentation presentation, String filename)
    {
        presentation.setTitle("Demo presentation.Presentation");
        presentation.append(this.buildIntroSlide());
        presentation.append(this.buildStylesSlide());
        presentation.append(this.buildClosingSlide());
    }

    @Override
    public void savePresentationToFile(Presentation presentation, String filename) throws IOException
    {
        throw new UnsupportedOperationException("Cannot save the demo presentation to a file.");
    }

    private Slide buildIntroSlide()
    {
        Slide slide = new Slide();
        slide.setTitle("JabberPoint");
        slide.append(1, "The Java presentation.Presentation Tool");
        slide.append(2, "Copyright (c) 1996-2000: Ian Darwin");
        slide.append(2, "Copyright (c) 2000-now: Gert Florijn and Sylvia Stuurman");
        slide.append(4, "Running JabberPoint without a filename shows this demo");
        slide.append(1, "Navigation:");
        slide.append(3, "Next slide: PgDn or Enter");
        slide.append(3, "Previous slide: PgUp or up-arrow");
        slide.append(3, "Quit: q or Q");
        return slide;
    }

    private Slide buildStylesSlide()
    {
        Slide slide = new Slide();
        slide.setTitle("Demonstration of levels and styles");
        slide.append(1, "Level 1");
        slide.append(2, "Level 2");
        slide.append(1, "Level 1 again");
        slide.append(1, "Level 1 uses style number 1");
        slide.append(2, "Level 2 uses style number 2");
        slide.append(3, "This is what level 3 looks like");
        slide.append(4, "And this is level 4");
        return slide;
    }

    private Slide buildClosingSlide()
    {
        Slide slide = new Slide();
        slide.setTitle("The third slide");
        slide.append(1, "To open a new presentation,");
        slide.append(2, "use File -> Open from the menu.");
        slide.append(1, " ");
        slide.append(1, "This is the end of the demo presentation.");
        slide.append(new BitmapItem(1, "JabberPoint.jpg"));
        return slide;
    }
}