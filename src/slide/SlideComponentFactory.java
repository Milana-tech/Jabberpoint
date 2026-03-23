package slide;

import presentation.PresentationComponent;

/**
 * SlideComponentFactory is the Creator interface in the Factory Method pattern.
 * <p>
 * Factory Method pattern roles:
 * - Creator        : SlideComponentFactory (this interface) — declares createComponent()
 * - ConcreteCreator: TextItemFactory, BitmapItemFactory — implement createComponent()
 * - Product        : SlideItem
 * - ConcreteProduct: TextItem, BitmapItem
 * <p>
 * OCP: New slide item types are added by implementing this interface — no existing code changes.
 * LSP: All implementations are fully substitutable wherever SlideComponentFactory is expected.
 * DIP: XMLAccessor and DemoPresentationLoader depend on this abstraction, not on TextItem or BitmapItem directly.
 */
public interface SlideComponentFactory
{
    SlideItem createComponent(PresentationComponent wrapped, int level, String content);

    String getType();

    boolean canHandle(SlideItem component);
}
