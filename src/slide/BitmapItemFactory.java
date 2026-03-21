package slide;

import accessor.XMLTags;
import presentation.PresentationComponent;

/**
 * BitmapItemFactory is responsible for creating TextItem instances
 * <p>
 * Single Responsible Principle: Has only one responsibility which is creating and identifying bitmap items
 * <p>
 * Open/Close Principle: Closed for changes; extend the factory system by adding
 * new factories, not this class.
 * <p>
 *  Liskov Substitution Principle: Completely substitutes SlideComponentFactory wherever it's required
 * */
public class BitmapItemFactory implements SlideComponentFactory
{
    @Override
    public SlideItem createComponent(PresentationComponent wrapped, int level, String content)
    {
        return new BitmapItem(wrapped, level, content);
    }

    @Override
    public String getType()
    {
        return XMLTags.IMAGE.getTag();
    }

    @Override
    public boolean canHandle(SlideItem component)
    {
        return component instanceof BitmapItem;
    }
}
