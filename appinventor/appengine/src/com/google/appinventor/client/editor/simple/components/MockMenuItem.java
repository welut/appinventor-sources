// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2017 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.editor.simple.components;

import static com.google.appinventor.client.Ode.MESSAGES;
import com.google.appinventor.client.editor.simple.SimpleEditor;
import com.google.gwt.user.client.ui.InlineHTML;

/**
 * Mock Menu Item component.
 *
 * @author xy93@cornell.edu (Steven Ye)
 */
public final class MockMenuItem extends MockVisibleComponent {
  
  // Component type name
  public static final String TYPE = "MenuItem";
  
  // Property names
  private static final String PROPERTY_NAME_ICON = "Icon";

  // GWT widget used to mock a menu item
  private InlineHTML itemWidget;

  /**
   * Creates a new MockMenuItem component.
   *
   * @param editor  editor of source file the component belongs to
   */
  public MockMenuItem(SimpleEditor editor) {
    super(editor, TYPE, images.label());

    // Initialize mock menu item UI
    itemWidget = new InlineHTML();
    itemWidget.setStylePrimaryName("ode-SimpleMockComponent");
    initComponent(itemWidget);
  }

  @Override
  public void onCreateFromPalette() {
    // Change item text to component name
    changeProperty(PROPERTY_NAME_TEXT, MESSAGES.textPropertyValue(getName()));
  }

  /*
   * Sets the item's TextAlignment property to a new value.
   */
  private void setTextAlignmentProperty(String text) {
    MockComponentsUtil.setWidgetTextAlign(itemWidget, text);
  }

  /*
   * Sets the item's BackgroundColor property to a new value.
   */
  private void setBackgroundColorProperty(String text) {
    if (MockComponentsUtil.isDefaultColor(text)) {
      MockComponentsUtil.resetWidgetBackgroundColor(itemWidget);
    } else {
      MockComponentsUtil.setWidgetBackgroundColor(itemWidget, text);
    }
  }

  /*
   * Sets the item's Enabled property to a new value.
   */
  private void setEnabledProperty(String text) {
    MockComponentsUtil.setEnabled(this, text);
  }

  /*
   * Sets the item's FontBold property to a new value.
   */
  private void setFontBoldProperty(String text) {
    MockComponentsUtil.setWidgetFontBold(itemWidget, text);
  }

  /*
   * Sets the item's FontItalic property to a new value.
   */
  private void setFontItalicProperty(String text) {
    MockComponentsUtil.setWidgetFontItalic(itemWidget, text);
  }

  /*
   * Sets the item's FontSize property to a new value.
   */
  private void setFontSizeProperty(String text) {
    MockComponentsUtil.setWidgetFontSize(itemWidget, text);
  }

  /*
   * Sets the item's FontTypeface property to a new value.
   */
  private void setFontTypefaceProperty(String text) {
    MockComponentsUtil.setWidgetFontTypeface(itemWidget, text);
  }

  /*
   * Sets the menu item's Icon property to a new value.
   */
  private void setIconProperty(String text) {
    // Implement this to reflect icon change in designer
  }

  /*
   * Sets the item's Text property to a new value.
   */
  private void setTextProperty(String text) {
    itemWidget.setText(text);
  }

  /*
   * Sets the item's TextColor property to a new value.
   */
  private void setTextColorProperty(String text) {
    if (MockComponentsUtil.isDefaultColor(text)) {
      MockComponentsUtil.resetWidgetTextColor(itemWidget);
    } else {
      MockComponentsUtil.setWidgetTextColor(itemWidget, text);
    }
  }

  // PropertyChangeListener implementation

  @Override
  public void onPropertyChange(String propertyName, String newValue) {
    super.onPropertyChange(propertyName, newValue);

    // Apply changed properties to the mock component
    if (propertyName.equals(PROPERTY_NAME_TEXTALIGNMENT)) {
      setTextAlignmentProperty(newValue);
    } else if (propertyName.equals(PROPERTY_NAME_BACKGROUNDCOLOR)) {
      setBackgroundColorProperty(newValue);
    } else if (propertyName.equals(PROPERTY_NAME_ENABLED)) {
      setEnabledProperty(newValue);
    } else if (propertyName.equals(PROPERTY_NAME_FONTBOLD)) {
      setFontBoldProperty(newValue);
      refreshForm();
    } else if (propertyName.equals(PROPERTY_NAME_FONTITALIC)) {
      setFontItalicProperty(newValue);
      refreshForm();
    } else if (propertyName.equals(PROPERTY_NAME_FONTSIZE)) {
      setFontSizeProperty(newValue);
      refreshForm();
    } else if (propertyName.equals(PROPERTY_NAME_FONTTYPEFACE)) {
      setFontTypefaceProperty(newValue);
      refreshForm();
    } else if (propertyName.equals(PROPERTY_NAME_ICON)) {
      setIconProperty(newValue);
    } else if (propertyName.equals(PROPERTY_NAME_TEXT)) {
      setTextProperty(newValue);
      refreshForm();
    } else if (propertyName.equals(PROPERTY_NAME_TEXTCOLOR)) {
      setTextColorProperty(newValue);
    }
  }
}
