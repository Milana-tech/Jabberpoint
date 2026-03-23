package accessor;

/**
 * accessor.XMLTags enumerates all XML element and attribute names used in JabberPoint presentation files.
 * <p>
 * SRP: This enum owns the single responsibility of defining the XML vocabulary.
 * OCP: Adding a new tag means adding an enum constant here, not changing accessor.XMLAccessor.
 */
public enum XMLTags
{
    SHOW_TITLE("showtitle"),
    SLIDE_TITLE("title"),
    SLIDE("slide"),
    ITEM("item"),
    LEVEL("level"),
    KIND("kind"),
    TEXT("text"),
    IMAGE("image");

    private final String tag;

    XMLTags(String tag)
    {
        this.tag = tag;
    }

    public String getTag()
    {
        return this.tag;
    }
}