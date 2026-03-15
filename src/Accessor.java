import java.io.IOException;

/**
 * Accessor is the abstract base for all presentation loaders and savers.
 * <p>
 * SRP: Defines the contract for IO operations on a Presentation.
 * OCP: New file formats are supported by subclassing Accessor, not by modifying it.
 */
public abstract class Accessor
{

    public static final String DEMO_NAME = "Demo Presentation";
    public static final String DEFAULT_EXTENSION = ".xml";

    public static Accessor getDemoAccessor()
    {
        return new DemoPresentationLoader();
    }

    public abstract void loadPresentationFromFile(Presentation presentation, String filename) throws IOException;

    public abstract void savePresentationToFile(Presentation presentation, String filename) throws IOException;
}