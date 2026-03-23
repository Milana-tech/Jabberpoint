package slide;

import presentation.PresentationComponent;

/**
 * SlideComponentFactory defines the contract for all slide item factories
 * Open/Closed Principle: New item types can be added by implementing this interface
 * without any modifications.
 * <p>
 * Liskov Substitution Principle: All implementations are fully changeable wherever
 * this interface is expected.
 * <p>
 * Dependency Inversion Principle: XMLAccessor & Slide depend on this abstraction,
 * not on concrete classes like TextItem or BitmapItem.
 */
public interface SlideComponentFactory
{
    SlideItem createComponent(PresentationComponent wrapped, int level, String content);

    String getType();

    boolean canHandle(SlideItem component);
}
