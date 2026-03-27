package slide;

import accessor.XMLTags;
import presentation.PresentationComponent;

/**
 * BitmapItemFactory is responsible for creating BitmapItem instances
 * <p>
 * Factory Method pattern role: Concrete Creator - implements createComponent() to produce slide.BitmapItem
 * <p>
 * SRP: Has only one responsibility which is creating and identifying bitmap items
 * OCP: Closed for changes; extend the factory system by adding new factories, not this class.
 * LSP: Completely substitutes SlideComponentFactory wherever it's required
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
