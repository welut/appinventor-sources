// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2018 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.components.runtime;

import java.io.IOException;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.util.MediaUtil;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem.OnMenuItemClickListener;

@DesignerComponent(version = YaVersion.MENUITEM_COMPONENT_VERSION,
    description = "A Menu Item can only be placed inside Menu components. " +
    "It displays a piece of text specified through the <code>Text</code> property, " +
    "as well as an optional icon. Additional properties include visibility and enabled/" + 
    "disabled, all of which can be set in the Designer or Blocks Editor. " +
    "Event is launched on user selection.",
    category = ComponentCategory.USERINTERFACE)
@SimpleObject
public final class MenuItem implements Component {
  private static final String LOG_TAG = "MenuItem";

	private android.view.MenuItem item;
	private ComponentContainer container;
	
  // Icon path
  private String iconPath = "";
	
	public MenuItem(ComponentContainer container) {
	  this.container = container;
	  Menu menu = container.$form().getOptionsMenu();
	  item = menu.add(Menu.NONE, Menu.NONE, menu.size(), "")
    .setOnMenuItemClickListener(new OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(android.view.MenuItem arg0) {
        Click();
        return true;
      }
    });
	  item.setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_IF_ROOM);
	}
	
  /**
   * Returns the text displayed by the menu item.
   *
   * @return  menu item text
   */
  @SimpleProperty(category = PropertyCategory.APPEARANCE)
  public String Text() {
    return item.getTitle().toString();
  }

  /**
   * Specifies the text displayed by the menu item.
   *
   * @param text  new text for menu item
   */
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING,
      defaultValue = "")
  @SimpleProperty
  public void Text(String text) {
    item.setTitle(text);
  }
  
  /**
   * Returns the path of the menu item's icon.
   *
   * @return  the path of the menu item's icon
   */
  @SimpleProperty(
      category = PropertyCategory.APPEARANCE,
      description = "Icon to display before menu item text.")
  public String Icon() {
    return iconPath;
  }

  /**
   * Specifies the path of the menu item's icon.
   *
   * <p/>See {@link MediaUtil#determineMediaSource} for information about what
   * a path can be.
   *
   * @param path  the path of the menu item's icon
   */
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET,
      defaultValue = "")
  @SimpleProperty(description = "Specifies the path of the menu item's icon.")
  public void Icon(String path) {
    // If it's the same as on the prior call and the prior load was successful,
    // do nothing.
    if (path.equals(iconPath) && item.getIcon() != null) {
      return;
    }

    iconPath = (path == null) ? "" : path;

    // Load image from file.
    if (iconPath.length() > 0) {
      try {
        Drawable iconDrawable = MediaUtil.getBitmapDrawable(container.$form(), iconPath);
        item.setIcon(iconDrawable);
      } catch (IOException ioe) {
        Log.e(LOG_TAG, "Unable to load " + iconPath);
      }
    }
  }
  
  /**
   * Returns true if the menu item is active and clickable.
   *
   * @return  {@code true} indicates enabled, {@code false} disabled
   */
  @SimpleProperty(
      category = PropertyCategory.BEHAVIOR,
      description = "If true, user can tap menu item to cause action.")
  public boolean Enabled() {
    return item.isEnabled();
  }

  /**
   * Specifies whether the menu item should be active and clickable.
   *
   * @param enabled  {@code true} for enabled, {@code false} disabled
   */
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN,
      defaultValue = "True")
  @SimpleProperty
  public void Enabled(boolean enabled) {
    item.setEnabled(enabled);
  }
  
  /**
   * Returns true if the menu item is visible, false otherwise.
   * 
   * @return  {@code true} iff the menu item is visible.
   */
  @SimpleProperty(
      category = PropertyCategory.APPEARANCE)
  public boolean Visible() {
    return item.isVisible();
  }

  /**
   * Specifies whether the menu item should be visible or hidden from menu.
   * 
   * @param  visibility  {@code true} iff the menu item should be visible.
   */
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_VISIBILITY,
      defaultValue = "True")
  @SimpleProperty(description = "Specifies whether the menu item should be visible. "
      + "Value is true if the component is showing and false if hidden from menu.")
  public void Visible(boolean visibility) {
    item.setVisible(visibility);
  }
  
  /**
   * Event to handle when user selects this menu item.
   */
  @SimpleEvent(description = "Event raised when user selects this menu item.")
  public void Click() {
    EventDispatcher.dispatchEvent(this, "Click");
  }

  @Override
  public HandlesEventDispatching getDispatchDelegate() {
    return container.$form();
  }

}
