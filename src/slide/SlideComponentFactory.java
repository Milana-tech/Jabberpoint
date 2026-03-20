package slide;

import presentation.PresentationComponent;

public interface SlideComponentFactory
{
    SlideItem createComponent(PresentationComponent wrapped, int level, String content);

    String getType();

    boolean canHandle(SlideItem component);
}
