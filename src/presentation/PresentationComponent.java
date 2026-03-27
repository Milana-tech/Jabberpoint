package presentation;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * presentation.PresentationComponent is the Component interface for Decorator pattern
 * presentation.Presentation depends on this interface to render any slide, regardless of how many slide.SlideItem decorators are stacked on top of a slide.Slide.
 * Decorator pattern role: Component — declares the common interface that both slide.Slide (Concrete Component) and slide.SlideItem (Base Decorator) must follow.
 * <p>
 * OCP: New decorator types can be added without modifying this interface.
 * DIP: presentation.Presentation and slide.SlideViewerComponent depend on this abstraction, not on slide.Slide or slide.SlideItem directly.
 */

public interface PresentationComponent
{
    int renderTo(Graphics g, Rectangle area, ImageObserver observer);
}
