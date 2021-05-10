/*
 * Copyright (C) 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.android.apps.common.testing.accessibility.framework;

import android.view.View;
import com.google.android.apps.common.testing.accessibility.framework.checks.RedundantDescriptionCheck;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Checks to ensure that speakable text does not contain redundant information about the view's
 * type. Accessibility services are aware of the view's type and can use that information as needed
 * (ex: Screen readers may append "button" to the speakable text of a {@link
 * android.widget.Button}).
 *
 * @deprecated Replaced by {@link RedundantDescriptionCheck}
 */
@Deprecated
public class RedundantContentDescViewCheck extends AccessibilityViewHierarchyCheck {

  private static final RedundantDescriptionCheck DELEGATION_CHECK = new RedundantDescriptionCheck();

  @Override
  public List<AccessibilityViewCheckResult> runCheckOnViewHierarchy(
      View root, @Nullable Parameters parameters) {
    return super.runDelegationCheckOnView(root, this, DELEGATION_CHECK, parameters);
  }
}
