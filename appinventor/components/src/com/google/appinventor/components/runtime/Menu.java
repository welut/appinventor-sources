// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2019 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.YaVersion;

@DesignerComponent(version = YaVersion.MENU_COMPONENT_VERSION,
category = ComponentCategory.LAYOUT,
description = "Component for options menu (one per Form) used to hold Menu Items",
showOnPalette = false)
@SimpleObject
public class Menu implements Component {
  private static final String LOG_TAG = "Menu";
  
  private ComponentContainer container;
  
  public Menu(ComponentContainer container) {
    this.container = container;
  }

  @Override
  public HandlesEventDispatching getDispatchDelegate() {
    return container.$form();
  }
}
