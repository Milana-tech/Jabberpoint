package controller;

import accessor.XMLAccessor;
import presentation.AboutBox;
import presentation.Presentation;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serial;

/**
 * MenuController builds the application menu bar and wires menu items to presentation actions.
 * <p>
 * SRP: Responsible only for constructing the menu and translating menu events into actions.
 * DIP: Depends on Presentation and Accessor abstractions, not on file formats or viewers.
 * <p>
 * Menu action mapping (see Actions enum):
 * Actions.OPEN_FILE         — File > Open
 * Actions.NEW_PRESENTATION  — File > New
 * Actions.SAVE_FILE         — File > Save
 * Actions. EXIT              — File > Exit
 * Actions.NEXT_SLIDE        — View > Next
 * Actions.PREVIOUS_SLIDE    — View > Prev
 * Actions.GO_TO_SLIDE       — View > Go to
 * Actions.SHOW_ABOUT        — Help > About
 */
public class MenuController extends MenuBar
{

    @Serial
    private static final long serialVersionUID = 227L;

    private static final String MENU_FILE = "File";
    private static final String MENU_VIEW = "View";
    private static final String MENU_HELP = "Help";

    private static final String ITEM_OPEN = "Open";
    private static final String ITEM_NEW = "New";
    private static final String ITEM_SAVE = "Save";
    private static final String ITEM_EXIT = "Exit";
    private static final String ITEM_NEXT = "Next";
    private static final String ITEM_PREV = "Prev";
    private static final String ITEM_GOTO = "Go to";
    private static final String ITEM_ABOUT = "About";

    private static final String DEFAULT_OPEN_FILE = "test.xml";
    private static final String DEFAULT_SAVE_FILE = "dump.xml";

    private static final String DIALOG_PAGE_NUMBER = "Page number?";
    private static final String ERROR_LOAD = "Load Error";
    private static final String ERROR_SAVE = "Save Error";

    private final Frame parentFrame;
    private final Presentation presentation;

    public MenuController(Frame parentFrame, Presentation presentation)
    {
        this.parentFrame = parentFrame;
        this.presentation = presentation;
        add(this.buildFileMenu());
        add(this.buildViewMenu());
        setHelpMenu(this.buildHelpMenu());
    }

    private Menu buildFileMenu()
    {
        Menu fileMenu = new Menu(MENU_FILE);

        MenuItem openItem = this.createMenuItem(ITEM_OPEN);
        openItem.addActionListener(e -> this.executeAction(Actions.OPEN_FILE));
        fileMenu.add(openItem);

        MenuItem newItem = this.createMenuItem(ITEM_NEW);
        newItem.addActionListener(e -> this.executeAction(Actions.NEW_PRESENTATION));
        fileMenu.add(newItem);

        MenuItem saveItem = this.createMenuItem(ITEM_SAVE);
        saveItem.addActionListener(e -> this.executeAction(Actions.SAVE_FILE));
        fileMenu.add(saveItem);

        fileMenu.addSeparator();

        MenuItem exitItem = this.createMenuItem(ITEM_EXIT);
        exitItem.addActionListener(e -> this.executeAction(Actions.EXIT));
        fileMenu.add(exitItem);

        return fileMenu;
    }

    private Menu buildViewMenu()
    {
        Menu viewMenu = new Menu(MENU_VIEW);

        MenuItem nextItem = this.createMenuItem(ITEM_NEXT);
        nextItem.addActionListener(e -> this.executeAction(Actions.NEXT_SLIDE));
        viewMenu.add(nextItem);

        MenuItem prevItem = this.createMenuItem(ITEM_PREV);
        prevItem.addActionListener(e -> this.executeAction(Actions.PREVIOUS_SLIDE));
        viewMenu.add(prevItem);

        MenuItem gotoItem = this.createMenuItem(ITEM_GOTO);
        gotoItem.addActionListener(e -> this.executeAction(Actions.GO_TO_SLIDE));
        viewMenu.add(gotoItem);

        return viewMenu;
    }

    private Menu buildHelpMenu()
    {
        Menu helpMenu = new Menu(MENU_HELP);

        MenuItem aboutItem = this.createMenuItem(ITEM_ABOUT);
        aboutItem.addActionListener(e -> this.executeAction(Actions.SHOW_ABOUT));
        helpMenu.add(aboutItem);

        return helpMenu;
    }

    /**
     * Central dispatcher — all menu actions pass through here.
     * Adding a new action means adding a case here, not touching the menu-building methods.
     */
    private void executeAction(Actions action)
    {
        switch (action)
        {
            case OPEN_FILE -> this.openPresentation();
            case NEW_PRESENTATION -> this.newPresentation();
            case SAVE_FILE -> this.savePresentation();
            case EXIT -> this.presentation.exit(0);
            case NEXT_SLIDE -> this.presentation.nextSlide();
            case PREVIOUS_SLIDE -> this.presentation.prevSlide();
            case GO_TO_SLIDE -> this.goToSlide();
            case SHOW_ABOUT -> AboutBox.show(this.parentFrame);
        }
    }

    private void openPresentation()
    {
        this.presentation.clear();
        try
        {
            new XMLAccessor().loadPresentationFromFile(this.presentation, DEFAULT_OPEN_FILE);
            this.presentation.setSlideNumber(0);
        }
        catch (IOException e)
        {
            this.showError("IO Exception: " + e.getMessage(), ERROR_LOAD);
        }
        this.parentFrame.repaint();
    }

    private void newPresentation()
    {
        this.presentation.clear();
        this.parentFrame.repaint();
    }

    private void savePresentation()
    {
        try
        {
            new XMLAccessor().savePresentationToFile(this.presentation, DEFAULT_SAVE_FILE);
        }
        catch (IOException e)
        {
            this.showError("IO Exception: " + e.getMessage(), ERROR_SAVE);
        }
    }

    private void goToSlide()
    {
        String input = JOptionPane.showInputDialog(DIALOG_PAGE_NUMBER);
        if (input == null)
        {
            return; // user cancelled
        }
        try
        {
            int pageNumber = Integer.parseInt(input);
            this.presentation.setSlideNumber(pageNumber - 1);
        }
        catch (NumberFormatException e)
        {
            this.showError("Please enter a valid page number.", ERROR_LOAD);
        }
    }

    private void showError(String message, String title)
    {
        JOptionPane.showMessageDialog(this.parentFrame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private MenuItem createMenuItem(String label)
    {
        return new MenuItem(label, new MenuShortcut(label.charAt(0)));
    }
}