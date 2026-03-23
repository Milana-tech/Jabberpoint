package presentation;

import java.util.ArrayList;
import java.util.List;

/**
 * presentation.Presentation holds all slides and manages the current slide position.
 * <p>
 * Observer pattern role: Subject — notifies registered PresentationObservers on slide changes.
 * SRP: Owns presentation state and navigation. Delegates display updates to observers.
 * DIP: Depends on presentation.PresentationObserver interface, not on slide.SlideViewerComponent directly.
 * OCP: New observers can be registered without changing this class.
 */
public class Presentation
{

    private static final int NO_SLIDE = -1;

    private String title;
    private int currentSlideNumber;

    // Observer pattern: list of registered observers

    private final List<PresentationObserver> observers;

    // Decorator pattern: list of registered components

    private final List<PresentationComponent> components;

    public Presentation()
    {
        this.observers = new ArrayList<>();
        this.components = new ArrayList<>();
        this.currentSlideNumber = NO_SLIDE;
    }

    // Observer pattern registration

    /**
     * Registers an observer to be notified when the current slide changes.
     * Observer pattern: subscribe.
     */
    public void addObserver(PresentationObserver observer)
    {
        this.observers.add(observer);
    }

    /**
     * Removes a previously registered observer.
     * Observer pattern: unsubscribe.
     */
    public void removeObserver(PresentationObserver observer)
    {
        this.observers.remove(observer);
    }

    /**
     * Notifies all registered observers with the current slide.
     * Observer pattern: notify.
     */
    private void notifyObservers()
    {
        PresentationComponent currentSlide = this.getCurrentSlide();
        for (PresentationObserver observer : this.observers)
        {
            observer.onSlideChanged(currentSlide);
        }
    }

    // Getters and setters

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getSize()
    {
        return this.components.size();
    }

    public int getSlideNumber()
    {
        return this.currentSlideNumber;
    }

    public void setSlideNumber(int number)
    {
        this.currentSlideNumber = number;
        this.notifyObservers();
    }

    // Navigation

    public void nextSlide()
    {
        if (this.currentSlideNumber < this.components.size() - 1)
        {
            this.setSlideNumber(this.currentSlideNumber + 1);
        }
    }

    public void prevSlide()
    {
        if (this.currentSlideNumber > 0)
        {
            this.setSlideNumber(this.currentSlideNumber - 1);
        }
    }

    // slide.Slide management

    public void append(PresentationComponent component)
    {
        this.components.add(component);
    }

    public PresentationComponent getSlide(int number)
    {
        if (this.isValidSlideNumber(number))
        {
            return this.components.get(number);
        }
        return null;
    }

    public PresentationComponent getCurrentSlide()
    {
        return this.getSlide(this.currentSlideNumber);
    }

    public void clear()
    {
        this.components.clear();
        this.setSlideNumber(NO_SLIDE);
    }

    public void exit(int status)
    {
        System.exit(status);
    }

    private boolean isValidSlideNumber(int number)
    {
        return number >= 0 && number < this.components.size();
    }
}