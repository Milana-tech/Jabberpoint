package presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.Presentation;
import presentation.PresentationComponent;
import presentation.PresentationObserver;
import slide.Slide;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Observer pattern in presentation.Presentation.
 * <p>
 * Tests verify that:
 * - Observers are notified when the slide changes
 * - Multiple observers all receive notifications
 * - Removed observers stop receiving notifications
 * - The correct slide is passed to observers
 * - Observers are notified on clear()
 * - Navigation methods (nextSlide, prevSlide) trigger notifications
 */
class PresentationObserverTest
{

    /**
     * A simple test double that records every slide it receives via onSlideChanged().
     * This lets us assert exactly how many times and with what data the observer was called.
     */

    static class RecordingObserver implements PresentationObserver
    {
        private final List<PresentationComponent> receivedSlides = new ArrayList<>();

        @Override
        public void onSlideChanged(PresentationComponent slide)
        {
            receivedSlides.add(slide);
        }

        public int getNotificationCount()
        {
            return receivedSlides.size();
        }

        public PresentationComponent getLastReceivedSlide()
        {
            if (receivedSlides.isEmpty())
            {
                return null;
            }
            return receivedSlides.get(receivedSlides.size() - 1);
        }
    }

    private Presentation presentation;
    private Slide firstSlide;
    private Slide secondSlide;

    @BeforeEach
    void setUp()
    {
        presentation = new Presentation();
        firstSlide = new Slide();
        firstSlide.setTitle("First slide.Slide");
        secondSlide = new Slide();
        secondSlide.setTitle("Second slide.Slide");
        presentation.append(firstSlide);
        presentation.append(secondSlide);
    }

    // Registration

    @Test
    void observerIsNotifiedWhenSlideNumberChanges()
    {
        RecordingObserver observer = new RecordingObserver();
        presentation.addObserver(observer);

        presentation.setSlideNumber(0);

        assertEquals(1, observer.getNotificationCount());
    }

    @Test
    void observerReceivesCorrectSlide()
    {
        RecordingObserver observer = new RecordingObserver();
        presentation.addObserver(observer);

        presentation.setSlideNumber(0);

        assertSame(firstSlide, observer.getLastReceivedSlide());
    }

    @Test
    void observerReceivesSecondSlideWhenNavigatingToIt()
    {
        RecordingObserver observer = new RecordingObserver();
        presentation.addObserver(observer);

        presentation.setSlideNumber(1);

        assertSame(secondSlide, observer.getLastReceivedSlide());
    }

    // Multiple observers

    @Test
    void allRegisteredObserversAreNotified()
    {
        RecordingObserver observerA = new RecordingObserver();
        RecordingObserver observerB = new RecordingObserver();
        presentation.addObserver(observerA);
        presentation.addObserver(observerB);

        presentation.setSlideNumber(0);

        assertEquals(1, observerA.getNotificationCount());
        assertEquals(1, observerB.getNotificationCount());
    }

    @Test
    void allObserversReceiveTheSameSlide()
    {
        RecordingObserver observerA = new RecordingObserver();
        RecordingObserver observerB = new RecordingObserver();
        presentation.addObserver(observerA);
        presentation.addObserver(observerB);

        presentation.setSlideNumber(1);

        assertSame(observerA.getLastReceivedSlide(), observerB.getLastReceivedSlide());
    }

    // Removal

    @Test
    void removedObserverIsNoLongerNotified()
    {
        RecordingObserver observer = new RecordingObserver();
        presentation.addObserver(observer);

        presentation.setSlideNumber(0);          // notified once
        presentation.removeObserver(observer);
        presentation.setSlideNumber(1);          // should NOT be notified

        assertEquals(1, observer.getNotificationCount());
    }

    @Test
    void remainingObserverIsStillNotifiedAfterAnotherIsRemoved()
    {
        RecordingObserver observerA = new RecordingObserver();
        RecordingObserver observerB = new RecordingObserver();
        presentation.addObserver(observerA);
        presentation.addObserver(observerB);

        presentation.removeObserver(observerA);
        presentation.setSlideNumber(0);

        assertEquals(0, observerA.getNotificationCount());
        assertEquals(1, observerB.getNotificationCount());
    }

    // Navigation methods

    @Test
    void nextSlideNotifiesObserverWithNextSlide()
    {
        RecordingObserver observer = new RecordingObserver();
        presentation.addObserver(observer);
        presentation.setSlideNumber(0);

        presentation.nextSlide();

        assertSame(secondSlide, observer.getLastReceivedSlide());
    }

    @Test
    void prevSlideNotifiesObserverWithPreviousSlide()
    {
        RecordingObserver observer = new RecordingObserver();
        presentation.addObserver(observer);
        presentation.setSlideNumber(1);

        presentation.prevSlide();

        assertSame(firstSlide, observer.getLastReceivedSlide());
    }

    @Test
    void nextSlideDoesNotNotifyWhenAlreadyOnLastSlide()
    {
        RecordingObserver observer = new RecordingObserver();
        presentation.addObserver(observer);
        presentation.setSlideNumber(1); // last slide

        int countBeforeAttempt = observer.getNotificationCount();
        presentation.nextSlide(); // should do nothing

        assertEquals(countBeforeAttempt, observer.getNotificationCount());
    }

    @Test
    void prevSlideDoesNotNotifyWhenAlreadyOnFirstSlide()
    {
        RecordingObserver observer = new RecordingObserver();
        presentation.addObserver(observer);
        presentation.setSlideNumber(0); // first slide

        int countBeforeAttempt = observer.getNotificationCount();
        presentation.prevSlide(); // should do nothing

        assertEquals(countBeforeAttempt, observer.getNotificationCount());
    }

    // Clear

    @Test
    void clearNotifiesObserverWithNullSlide()
    {
        RecordingObserver observer = new RecordingObserver();
        presentation.addObserver(observer);
        presentation.setSlideNumber(0);

        presentation.clear();

        // clear() resets slide number to -1, so getCurrentSlide() returns null
        assertNull(observer.getLastReceivedSlide());
    }

    // Edge case

    @Test
    void setSlideNumberWithNoObserversDoesNotThrow()
    {
        assertDoesNotThrow(() -> presentation.setSlideNumber(0));
    }
}