package accessor;

import presentation.Presentation;
import slide.BitmapItem;
import slide.Slide;
import slide.SlideComponentFactoryManager;

import java.io.IOException;

/**
 * accessor.DemoPresentationLoader builds the built-in demo presentation in memory.
 * <p>
 * SRP: Responsible only for constructing demo slide content.
 * IO concerns stay in accessor.XMLAccessor; demo content lives here.
 */
public class DemoPresentationLoader extends Accessor
{
    private static final SlideComponentFactoryManager FACTORY_MANAGER = new SlideComponentFactoryManager();

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
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 1, "The Java presentation.Presentation Tool"));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 2, "Copyright (c) 1996-2000: Ian Darwin"));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 2, "Copyright (c) 2000-now: Gert Florijn and Sylvia Stuurman"));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 4, "Running JabberPoint without a filename shows this demo"));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 1, "Navigation:"));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 3, "Next slide: PgDn or Enter"));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 3, "Previous slide: PgUp or up-arrow"));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 3, "Quit: q or Q"));

        return slide;
    }

    private Slide buildStylesSlide()
    {
        Slide slide = new Slide();
        slide.setTitle("Demonstration of levels and styles");
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 1, "Level 1"));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 2, "Level 2"));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 1, "Level 1 again"));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 1, "Level 1 uses style number 1"));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 2, "Level 2 uses style number 2"));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 3, "This is what level 3 looks like"));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 4, "And this is level 4"));
        return slide;
    }

    private Slide buildClosingSlide()
    {
        Slide slide = new Slide();
        slide.setTitle("The third slide");
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 1, "To open a new presentation"));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 2, "use File -> Open from the menu."));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 1, " "));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.TEXT.getTag(), 1, "This is the end of the demo presentation."));
        slide.append(FACTORY_MANAGER.createComponent(XMLTags.IMAGE.getTag(), 1, "Jabberpoint.jpg"));

        return slide;
    }
}