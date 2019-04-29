// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2019 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.components.runtime;

import java.util.LinkedList;
import java.util.List;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.YaVersion;

import android.app.Activity;

@DesignerComponent(version = YaVersion.MENU_COMPONENT_VERSION,
category = ComponentCategory.LAYOUT,
description = "Component for options menu (one per Form) used to hold Menu Items",
showOnPalette = false)
@SimpleObject
public class Menu implements Component, ComponentContainer, OnCreateOptionsMenuListener {
  private static final String LOG_TAG = "Menu";
  
  private ComponentContainer container;
  private final Activity context;
  private android.view.Menu menu;
  private List<MenuItem> menuItems;
  
  public Menu(ComponentContainer container) {
    this.container = container;
    context = container.$context();
    menuItems = new LinkedList<MenuItem>();
    container.$form().registerForOnCreateOptionsMenu(this);
  }

  @Override
  public void onCreateOptionsMenu(android.view.Menu menu) {
    this.menu = menu;
    for (MenuItem item : menuItems) {
      item.addToMenu(menu);
    }
  }
  
  public void addMenuItem(MenuItem item) {
    menuItems.add(item);
    if (menu != null) {
      item.addToMenu(menu);
    }
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
