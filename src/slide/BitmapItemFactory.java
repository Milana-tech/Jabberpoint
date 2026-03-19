package slide;

import accessor.XMLTags;

public class BitmapItemFactory implements SlideComponentFactory
{
    @Override
    public SlideItem createComponent(int level, String content)
    {
        return new BitmapItem(level, content);
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
