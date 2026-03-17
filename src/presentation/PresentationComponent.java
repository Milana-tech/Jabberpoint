package presentation;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * presentation.PresentationComponent is the component interface for Decorator pattern
 * Presentation can wrap Slide in multiple layers as long as it works with all components via this interface
 *
 */

public interface PresentationComponent
{
    int renderTo(Graphics g, Rectangle area, ImageObserver observer);
}
