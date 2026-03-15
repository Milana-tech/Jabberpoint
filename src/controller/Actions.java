package controller;

/**
 * controller.Actions enumerates all user triggered actions in JabberPoint.
 * SRP: This enum owns the single responsibility of naming available actions.
 * OCP: New actions are added here without changing controller.KeyController or controller.MenuController logic.
 */

public enum Actions
{
    NEXT_SLIDE,
    PREVIOUS_SLIDE,
    GO_TO_SLIDE,
    OPEN_FILE,
    SAVE_FILE,
    NEW_PRESENTATION,
    EXIT,
    SHOW_ABOUT
}