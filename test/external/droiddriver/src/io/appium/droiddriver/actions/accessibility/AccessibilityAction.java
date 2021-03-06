/*
 * Copyright (C) 2013 DroidDriver committers
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.droiddriver.actions.accessibility;

import android.view.accessibility.AccessibilityNodeInfo;

import io.appium.droiddriver.UiElement;
import io.appium.droiddriver.actions.Action;
import io.appium.droiddriver.actions.BaseAction;
import io.appium.droiddriver.uiautomation.UiAutomationElement;

/**
 * Implements {@link Action} via the Accessibility API.
 */
public abstract class AccessibilityAction extends BaseAction {
  protected AccessibilityAction(long timeoutMillis) {
    super(timeoutMillis);
  }

  @Override
  public final boolean perform(UiElement element) {
    return perform(((UiAutomationElement) element).getRawElement(), element);
  }

  /**
   * Performs the action via the Accessibility API.
   *
   * @param node the AccessibilityNodeInfo used to create the UiElement
   * @param element the UiElement to perform the action on
   * @return Whether the action is successful. Some actions throw exceptions in
   *         case of failure, when that behavior is more appropriate.
   */
  protected abstract boolean perform(AccessibilityNodeInfo node, UiElement element);
}
