package slide;

public interface SlideComponentFactory
{
    public SlideItem createComponent(int level, String content);

    public String getType();

    public boolean canHandle(SlideItem component);
}
