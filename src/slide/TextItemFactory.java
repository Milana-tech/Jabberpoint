package slide;

import accessor.XMLTags;
import presentation.PresentationComponent;

/**
 * TextItemFactory is responsible for creating TextItem instances
 * <p>
 * Single Responsible Principle: Has only one responsibility which is creating and identifying text items
 * <p>
 * Open/Close Principle: Closed for changes; extend the factory system by adding
 * new factories, not this class.
 * <p>
 *  Liskov Substitution Principle: Completely substitutes SlideComponentFactory wherever it's required
 * */
public class TextItemFactory implements SlideComponentFactory
{
    @Override
    public SlideItem createComponent(PresentationComponent wrapped, int level, String content)
    {
        return new TextItem(wrapped, level, content);
    }

    @Override
    public String getType()
    {
        return XMLTags.TEXT.getTag();
    }

    @Override
    public boolean canHandle(SlideItem component)
    {
        return component instanceof TextItem;
    }
}
