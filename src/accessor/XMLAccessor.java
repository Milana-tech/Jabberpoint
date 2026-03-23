package accessor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import presentation.Presentation;
import presentation.PresentationComponent;
import slide.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * accessor.XMLAccessor loads and saves presentations in the JabberPoint XML format.
 * <p>
 * SRP: Responsible only for XML file IO and parsing.
 * Uses accessor.XMLTags enum so tag names are never repeated as raw strings.
 */
public class XMLAccessor extends Accessor
{
    private static final SlideComponentFactoryManager FACTORY_MANAGER = new SlideComponentFactoryManager();
    private static final String PARSER_CONFIG_ERROR = "Parser configuration error";
    private static final String UNKNOWN_ITEM_TYPE = "Unknown slide item type: ";
    private static final String UNKNOWN_LEVEL = "Could not read item level, defaulting to 1";
    private static final int DEFAULT_ITEM_LEVEL = 1;

    @Override
    public void loadPresentationFromFile(Presentation presentation, String filename) throws IOException
    {
        try
        {
            Document document = this.parseXmlFile(filename);
            Element documentElement = document.getDocumentElement();
            presentation.setTitle(this.readTextContent(documentElement, XMLTags.SHOW_TITLE.getTag()));
            this.loadAllSlides(presentation, documentElement);
        }
        catch (SAXException e)
        {
            throw new IOException("XML parsing failed: " + e.getMessage(), e);
        }
        catch (ParserConfigurationException e)
        {
            throw new IOException(PARSER_CONFIG_ERROR, e);
        }
    }

    private Document parseXmlFile(String filename) throws IOException, SAXException, ParserConfigurationException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver((publicId, systemId) -> new org.xml.sax.InputSource(new java.io.StringReader("")));
        return builder.parse(new File(filename));
    }

    private void loadAllSlides(Presentation presentation, Element documentElement)
    {
        NodeList slideNodes = documentElement.getElementsByTagName(XMLTags.SLIDE.getTag());
        for (int i = 0; i < slideNodes.getLength(); i++)
        {
            Element slideElement = (Element) slideNodes.item(i);
            presentation.append(this.loadSlide(slideElement));
        }
    }

    private PresentationComponent loadSlide(Element slideElement)
    {
        Slide base = new Slide();
        base.setTitle(this.readTextContent(slideElement, XMLTags.SLIDE_TITLE.getTag()));
        PresentationComponent chain = base;
        NodeList itemNodes = slideElement.getElementsByTagName(XMLTags.ITEM.getTag());
        for (int i = 0; i < itemNodes.getLength(); i++)
        {
            chain = this.wrapItem(chain, (Element) itemNodes.item(i));
        }
        return chain;
    }

    private PresentationComponent wrapItem(PresentationComponent wrapped, Element itemElement)
    {
        NamedNodeMap attributes = itemElement.getAttributes();
        int level = this.readItemLevel(attributes);
        String kind = attributes.getNamedItem(XMLTags.KIND.getTag()).getTextContent();
        String content = itemElement.getTextContent();
        return FACTORY_MANAGER.createComponent(wrapped, kind, level, content);
    }

    private int readItemLevel(NamedNodeMap attributes)
    {
        String levelText = attributes.getNamedItem(XMLTags.LEVEL.getTag()).getTextContent();
        try
        {
            return Integer.parseInt(levelText);
        }
        catch (NumberFormatException e)
        {
            System.err.println(UNKNOWN_LEVEL);
            return DEFAULT_ITEM_LEVEL;
        }
    }

    @Override
    public void savePresentationToFile(Presentation presentation, String filename) throws IOException
    {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename)))
        {
            this.writeXmlHeader(out);
            this.writePresentationTitle(out, presentation);
            this.writeAllSlides(out, presentation);
            out.println("</presentation>");
        }
    }

    private void writeXmlHeader(PrintWriter out)
    {
        out.println("<?xml version=\"1.0\"?>");
        out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
        out.println("<presentation>");
    }

    private void writePresentationTitle(PrintWriter out, Presentation presentation)
    {
        out.println("<showtitle>" + presentation.getTitle() + "</showtitle>");
    }

    private void writeAllSlides(PrintWriter out, Presentation presentation)
    {
        for (int i = 0; i < presentation.getSize(); i++)
        {
            this.writeSlide(out, presentation.getSlide(i));
        }
    }

    private void writeSlide(PrintWriter out, PresentationComponent component)
    {
        // Traverse chain outermost → innermost, collect SlideItems, find Slide at the end
        List<SlideItem> items = new ArrayList<>();
        PresentationComponent current = component;

        while (current instanceof SlideItem slideItem)
        {
            items.add(slideItem);
            current = slideItem.getWrapped();
        }

        Slide slide = (Slide) current;
        Collections.reverse(items); // restore original top-to-bottom order

        out.println("<slide>");
        out.println("<title>" + slide.getTitle() + "</title>");
        for (SlideItem item : items)
        {
            this.writeSlideItem(out, item);
        }
        out.println("</slide>");
    }

    private void writeSlideItem(PrintWriter out, SlideItem item)
    {
        // OCP: delegate type resolution to the factory manager — no instanceof needed here
        String type = FACTORY_MANAGER.getType(item);
        if (type.isEmpty())
        {
            System.err.println("Skipping unknown slide.SlideItem type: " + item);
            return;
        }
        out.println("<item kind=\"" + type + "\" level=\"" + item.getLevel() + "\">"
                + item.getContent() + "</item>");
    }

    private String readTextContent(Element element, String tagName)
    {
        NodeList nodes = element.getElementsByTagName(tagName);
        return nodes.item(0).getTextContent();
    }
}