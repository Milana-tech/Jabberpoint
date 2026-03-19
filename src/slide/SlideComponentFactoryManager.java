package slide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlideComponentFactoryManager
{
    private Map<String, SlideComponentFactory> factories;
    private List<SlideComponentFactory> factoryList;

    public SlideComponentFactoryManager()
    {
        this.factories = new HashMap<>();
        this.factoryList = new ArrayList<>();

        this.registerFactory(new TextItemFactory());
        this.registerFactory(new BitmapItemFactory());
    }

    public Map<String, SlideComponentFactory> getFactories()
    {
        return this.factories;
    }

    public void setFactories(Map<String, SlideComponentFactory> factories)
    {
        this.factories = factories;
    }

    public List<SlideComponentFactory> getFactoryList()
    {
        return this.factoryList;
    }

    public void setFactoryList(List<SlideComponentFactory> factoryList)
    {
        this.factoryList = factoryList;
    }

    public void registerFactory(SlideComponentFactory factory)
    {
        this.factoryList.add(factory);
        this.factories.put(factory.getType(), factory);
    }

    public SlideItem createComponent(String type, int level, String content)
    {
        SlideComponentFactory factory = this.factories.get(type);

        if (factory == null)
        {
            throw new IllegalArgumentException("Unknown component type: " + type);
        }

        return factory.createComponent(level, content);
    }

    public String getType(SlideItem component)
    {
        for (SlideComponentFactory factory : this.factoryList)
        {
            if (factory.canHandle(component))
            {
                return factory.getType();
            }
        }

        return "";
    }
}
