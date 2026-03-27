package accessor;

import presentation.Presentation;
import presentation.PresentationComponent;
import slide.Slide;
import slide.SlideComponentFactoryManager;

import java.io.IOException;

/**
 * accessor.DemoPresentationLoader builds the built-in demo presentation in memory.
 * <p>
 * SRP: Responsible only for constructing demo slide content.
 * IO concerns stay in accessor.XMLAccessor; demo content lives here.
 * OCP: Uses SlideComponentFactoryManager for item creation, so new item types require no changes to this class.
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

    private PresentationComponent buildIntroSlide()
    {
        Slide base = new Slide();
        base.setTitle("JabberPoint");

        PresentationComponent chain = base;
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 1, "The Java presentation.Presentation Tool");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 2, "Copyright (c) 1996-2000: Ian Darwin");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 2, "Copyright (c) 2000-now: Gert Florijn and Sylvia Stuurman");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 4, "Running JabberPoint without a filename shows this demo");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 1, "Navigation:");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 3, "Next slide: PgDn or Enter");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 3, "Previous slide: PgUp or up-arrow");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 3, "Quit: q or Q");
        return chain;
    }

    private PresentationComponent buildStylesSlide()
    {
        Slide base = new Slide();
        base.setTitle("Demonstration of levels and styles");
        PresentationComponent chain = base;
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 1, "Level 1");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 2, "Level 2");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 1, "Level 1 again");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 1, "Level 1 uses style number 1");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 2, "Level 2 uses style number 2");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 3, "This is what level 3 looks like");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 4, "And this is level 4");
        return chain;
    }

    private PresentationComponent buildClosingSlide()
    {
        Slide base = new Slide();
        base.setTitle("The third slide");

        PresentationComponent chain = base;
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 1, "To open a new presentation");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 2, "use File -> Open from the menu.");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 1, " ");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.TEXT.getTag(), 1, "This is the end of the demo presentation.");
        chain = FACTORY_MANAGER.createComponent(chain, XMLTags.IMAGE.getTag(), 1, "JabberPoint.gif");
        return chain;
    }
}