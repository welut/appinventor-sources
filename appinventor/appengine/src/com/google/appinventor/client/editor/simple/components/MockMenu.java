package com.google.appinventor.client.editor.simple.components;

import com.google.appinventor.client.editor.simple.SimpleEditor;
import com.google.appinventor.client.editor.simple.palette.SimplePaletteItem;
import com.google.appinventor.client.widgets.dnd.DragSource;
import com.google.appinventor.components.common.ComponentConstants;
import com.google.gwt.user.client.ui.AbsolutePanel;

public final class MockMenu extends MockContainer {
  public static final String TYPE = "Menu";
  private AbsolutePanel menuWidget;

  /**
   * Creates a new MockMenu component.
   *
   * @param editor  editor of source file the component belongs to
   */
  public MockMenu(SimpleEditor editor) {
    super(editor, TYPE, images.listpicker(), new MockHVLayout(ComponentConstants.LAYOUT_ORIENTATION_VERTICAL));

    menuWidget = new AbsolutePanel();
    menuWidget.setPixelSize(100, 100);
    menuWidget.setStylePrimaryName("ode-SimpleMockMenu");

    initComponent(menuWidget);
  }

  @Override
  protected boolean acceptableSource(DragSource source) {
    MockComponent component = null;
    if (source instanceof MockComponent) {
      component = (MockComponent) source;
    } else if (source instanceof SimplePaletteItem) {
      component = (MockComponent) source.getDragWidget();
    }
    return component instanceof MockMenuItem;
  }

  /**
   * Toggle visibility of the mock menu.
   */
  public void toggle() {
    menuWidget.setVisible(!menuWidget.isVisible());
    refreshForm();
  }

}
