package slide;

import accessor.XMLTags;

public class TextItemFactory implements SlideComponentFactory
{
    @Override
    public SlideItem createComponent(int level, String content)
    {
        return new TextItem(level, content);
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
