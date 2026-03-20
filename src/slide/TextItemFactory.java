package slide;

import accessor.XMLTags;
import presentation.PresentationComponent;

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
