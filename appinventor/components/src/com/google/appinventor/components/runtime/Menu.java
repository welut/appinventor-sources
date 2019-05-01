// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2019 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.components.runtime;

import java.util.LinkedList;
import java.util.List;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.util.YailList;

import android.app.Activity;
import android.view.MenuItem.OnMenuItemClickListener;

@DesignerComponent(version = YaVersion.MENU_COMPONENT_VERSION,
    category = ComponentCategory.LAYOUT,
    description = "Component for options menu (one per Screen) used to hold Menu Items. " +
    "Menu (along with the action bar) is only accessible if theme is not Classic.",
    showOnPalette = false)
@SimpleObject
public class Menu implements Component, ComponentContainer, OnCreateOptionsMenuListener, OnOptionsItemSelectedListener {
  private static final String LOG_TAG = "Menu";

  private ComponentContainer container;
  private final Activity context;
  private android.view.Menu menu;
  private List<MenuItem> menuItems;

  // Menu items for About and Stop options
  private MenuItem aboutItem;
  private MenuItem stopItem;

  // Visibility of About and Stop menu items
  private boolean showAboutItem = true;
  private boolean showStopItem = true;

  public Menu(ComponentContainer container) {
    this.container = container;
    context = container.$context();
    menuItems = new LinkedList<MenuItem>();
    container.$form().registerForOnCreateOptionsMenu(this);
    container.$form().registerForOnOptionsItemSelected(this);
  }

  @Override
  public void onCreateOptionsMenu(android.view.Menu menu) {
    this.menu = menu;
    for (MenuItem item : menuItems) {
      item.addToMenu(menu);
    }
    addAboutItemToMenu();
    addStopItemToMenu();
    Initialize();
  }

  public void addMenuItem(MenuItem item) {
    menuItems.add(item);
    if (menu != null) {
      item.addToMenu(menu);
    }
  }

  private void addAboutItemToMenu() {
    aboutItem = new MenuItem(this);
    aboutItem.Text("About this application");
    aboutItem.setIcon(android.R.drawable.sym_def_app_icon);
    aboutItem.Visible(showAboutItem);
    aboutItem.setOnClickListener(new OnMenuItemClickListener() {
      public boolean onMenuItemClick(android.view.MenuItem item) {
        container.$form().showAboutApplicationNotification();
        return false;
      }
    });
  }

  private void addStopItemToMenu() {
    stopItem = new MenuItem(this);
    stopItem.Text("Stop this application");
    stopItem.setIcon(android.R.drawable.ic_notification_clear_all);
    stopItem.Visible(showStopItem);
    stopItem.setOnClickListener(new OnMenuItemClickListener() {
      public boolean onMenuItemClick(android.view.MenuItem item) {
        container.$form().showExitApplicationNotification();
        return false;
      }
    });
  }

  /**
   * Returns true if an About option that displays additional info is shown.
   *
   * @return  {@code true} iff About option is visible
   */
  @SimpleProperty
  public boolean ShowAbout() {
    return showAboutItem;
  }

  /**
   * Specifies whether to show an About option that displays additional info.
   *
   * @param showAboutItem  {@code true} iff About option is visible
   */
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN,
      defaultValue = "True")
  @SimpleProperty
  public void ShowAbout(boolean showAboutItem) {
    this.showAboutItem = showAboutItem;
    if (aboutItem != null) {
      aboutItem.Visible(showAboutItem);
    }
  }

  /**
   * Returns true if a Stop option for users to exit the app is shown.
   *
   * @return  {@code true} iff Stop option is visible
   */
  @SimpleProperty
  public boolean ShowStop() {
    return showStopItem;
  }

  /**
   * Specifies whether to show a Stop option for users to exit the app.
   *
   * @param showStopItem  {@code true} iff Stop option is visible
   */
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN,
      defaultValue = "True")
  @SimpleProperty
  public void ShowStop(boolean showStopItem) {
    this.showStopItem = showStopItem;
    if (stopItem != null) {
      stopItem.Visible(showStopItem);
    }
  }

  /**
   * Event to handle when the menu is first created and its items loaded.
   */
  @SimpleEvent(description = "Menu created (occurs after screen initialization)")
  public void Initialize() {
    EventDispatcher.dispatchEvent(this, "Initialize");
  }

  @Override
  public boolean onOptionsItemSelected(android.view.MenuItem item) {
    ItemSelected(item.getOrder(), item.getTitle().toString());
    return true;
  }

  /**
   * Event to handle when the app user selects an item from the options menu.
   *
   * @param selectionIndex The index of menu item that is selected (hidden items still take up indices).
   * @param selection The text of menu item that is selected.
   */
  @SimpleEvent(description = "Event raised when user selects an item from the options menu.")
  public void ItemSelected(int selectionIndex, String selection) {
    EventDispatcher.dispatchEvent(this, "ItemSelected", selectionIndex, selection);
  }

  /**
   * Items property getter method: returns a YailList copy containing
   * names of items under this menu, or an empty list if no menu item exists.
   *
   * @return a YailList copy containing names of menu items (including non-visible ones)
   */
  @SimpleProperty(category = PropertyCategory.BEHAVIOR)
  public YailList Items() {
    List<String> menuNames = new LinkedList<String>();
    for (MenuItem item : menuItems) {
      menuNames.add(item.Text());
    }
    return YailList.makeList(menuNames);
  }

  @Override
  public HandlesEventDispatching getDispatchDelegate() {
    return container.$form();
  }

  @Override
  public Activity $context() {
    return context;
  }

  @Override
  public Form $form() {
    return container.$form();
  }

  @Override
  public void $add(AndroidViewComponent component) {
    throw new UnsupportedOperationException("Menu.$add() called");
  }

  @Override
  public void setChildWidth(AndroidViewComponent component, int width) {
    throw new UnsupportedOperationException("Menu.setChildWidth() called");
  }

  @Override
  public void setChildHeight(AndroidViewComponent component, int height) {
    throw new UnsupportedOperationException("Menu.setChildHeight() called");
  }

  @Override
  public int Width() {
    return 0;
  }

  @Override
  public int Height() {
    return 0;
  }

}
