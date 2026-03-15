/**
 * PresentationObserver is the Observer interface for the Observer pattern.
 * Any component that needs to react to slide changes implements this interface.
 * This decouples Presentation (the subject) from SlideViewerComponent (the observer):
 * Presentation does not need to know about Swing or any specific viewer.
 * Observer pattern role: Observer
 * OCP: New observers can be added without changing Presentation.
 * DIP: Presentation depends on this abstraction, not on SlideViewerComponent directly.
 */
public interface PresentationObserver
{

    /**
     * Called by Presentation whenever the current slide changes.
     *
     * @param slide the slide that is now active, or null if the presentation is empty
     */
    void onSlideChanged(Slide slide);
}