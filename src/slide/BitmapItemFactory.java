package slide;

import accessor.XMLTags;
import presentation.PresentationComponent;

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
