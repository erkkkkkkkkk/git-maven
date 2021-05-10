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
import com.google.android.apps.common.testing.accessibility.framework.checks.DuplicateClickableBoundsCheck;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Developers sometimes have containers marked clickable when they don't process click events. This
 * error is difficult to detect, but when a container shares its bounds with a child view, that is a
 * clear error. This class catches that case.
 *
 * @deprecated Replaced by {@link DuplicateClickableBoundsCheck}
 */
@Deprecated
public class DuplicateClickableBoundsViewCheck extends AccessibilityViewHierarchyCheck {

  private static final DuplicateClickableBoundsCheck DELEGATION_CHECK =
      new DuplicateClickableBoundsCheck();

  @Override
  public List<AccessibilityViewCheckResult> runCheckOnViewHierarchy(
      View root, @Nullable Parameters parameters) {
    return super.runDelegationCheckOnView(root, this, DELEGATION_CHECK, parameters);
  }
}
