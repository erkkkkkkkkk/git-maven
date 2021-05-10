/*
 * Copyright (C) 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.android.apps.common.testing.accessibility.framework;

import com.google.android.apps.common.testing.accessibility.framework.AccessibilityCheckResult.AccessibilityCheckResultType;
import com.google.common.collect.ImmutableBiMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

/**
 * Utility class for dealing with {@code AccessibilityCheckResult}s, without any dependency upon
 * Android classes.
 *
 * <p>This duplicates some of the methods in {@code AccessibilityCheckResultUtils} in order to
 * support separate Android and Java build targets while maintaining API compatibility.
 */
public final class AccessibilityCheckResultBaseUtils {

  private AccessibilityCheckResultBaseUtils() {}

  /**
   * Takes a list of {@code AccessibilityCheckResult}s and returns a list with only results obtained
   * from the given {@code AccessibilityCheck}.
   * <p>
   * NOTE: This method explicitly does not take subtypes of {@code checkClass} into account when
   * filtering results.
   *
   * @param results a list of {@code AccessibilityCheckResult}s
   * @param checkClass the {@code Class} of the {@code AccessibilityCheck} to get results for
   * @return a list of {@code AccessibilityCheckResult}s obtained from the given
   *         {@code AccessibilityCheck}.
   */
  public static <T extends AccessibilityCheckResult> List<T> getResultsForCheck(
      Iterable<T> results, Class<? extends AccessibilityCheck> checkClass) {
    return getResultsForCheck(results, checkClass, /* aliases= */ null);
  }

  /**
   * Takes a list of {@code AccessibilityCheckResult}s and returns a list with only results obtained
   * from the given {@code AccessibilityCheck}. If a BiMap of class aliases is provided, the
   * returned value will also include results obtained from the check class paired with the given
   * class in the BiMap.
   */
  static <T extends AccessibilityCheckResult> List<T> getResultsForCheck(
      Iterable<T> results,
      Class<? extends AccessibilityCheck> checkClass,
      @Nullable ImmutableBiMap<?, ?> aliases) {
    List<T> resultsForCheck = new ArrayList<T>();
    for (T result : results) {
      Class<? extends AccessibilityCheck> resultCheckClass = result.getSourceCheckClass();
      Object alias = getAlias(resultCheckClass, aliases);
      if (checkClass.equals(resultCheckClass) || checkClass.equals(alias)) {
        resultsForCheck.add(result);
      }
    }
    return resultsForCheck;
  }

  /**
   * Filters {@link AccessibilityCheckResult}s and returns a list with only results which match the
   * given {@link AccessibilityCheckResultType}.
   *
   * @param results an {@link Iterable} of {@link AccessibilityCheckResult}s
   * @param type the {@link AccessibilityCheckResultType} for the results to be returned
   * @return a list of {@link AccessibilityCheckResult}s with the given
   *         {@link AccessibilityCheckResultType}.
   */
  public static <T extends AccessibilityCheckResult> List<T> getResultsForType(
      Iterable<T> results, AccessibilityCheckResultType type) {
    List<T> resultsForType = new ArrayList<T>();
    for (T result : results) {
      if (result.getType() == type) {
        resultsForType.add(result);
      }
    }
    return resultsForType;
  }

  /**
   * Filters {@code AccessibilityCheckResult}s and returns a list with only results which match the
   * given {@link AccessibilityCheckResultType}s.
   *
   * @param results an {@link Iterable} of {@code AccessibilityCheckResult}s
   * @param types a {@link Set} of {@link AccessibilityCheckResultType}s that should be returned in
   *        the filtered list
   * @return a {@link List} containing only the {@link AccessibilityHierarchyCheckResult}s from
   *         {@code results} which are of a type included in {@code types}
   */
  public static <T extends AccessibilityCheckResult> List<T> getResultsForTypes(
      Iterable<T> results, Set<AccessibilityCheckResultType> types) {
    List<T> resultsForTypes = new ArrayList<T>();
    for (T result : results) {
      if (types.contains(result.getType())) {
        resultsForTypes.add(result);
      }
    }
    return resultsForTypes;
  }

  /**
   * Returns a {@link Matcher} for an {@link AccessibilityCheckResult} whose result type matches the
   * given matcher for {@link AccessibilityCheckResultType}.
   *
   * @param typeMatcher a {@code Matcher} for an {@code AccessibilityCheckResultType}
   * @return a {@code Matcher} for an {@code AccessibilityCheckResult}
   */
  public static Matcher<AccessibilityCheckResult> matchesTypes(
      final Matcher<? super AccessibilityCheckResultType> typeMatcher) {
    return new TypeSafeMemberMatcher<AccessibilityCheckResult>("result type", typeMatcher) {
      @Override
      public boolean matchesSafely(AccessibilityCheckResult result) {
        return typeMatcher.matches(result.getType());
      }
    };
  }

  /**
   * Returns a {@link Matcher} for an {@link AccessibilityCheckResult} whose source check class
   * matches the given matcher.
   *
   * <p>Note: Do not use {@link Matchers#is} for a {@link Class}, as the deprecated form will match
   * only objects of that class instead of the class object itself. Use {@link Matchers#equalTo}
   * instead.
   *
   * @param classMatcher a {@code Matcher} for a {@code Class<? extends AccessibilityCheck>}. Note:
   *     strict typing not enforced for Java 7 compatibility
   * @return a {@code Matcher} for a {@code AccessibilityCheckResult}
   */
  public static Matcher<AccessibilityCheckResult> matchesChecks(Matcher<?> classMatcher) {
    return matchesChecks(classMatcher, /* aliases= */ null);
  }

  /**
   * Returns a {@link Matcher} for an {@link AccessibilityCheckResult} whose source check class
   * matches the given matcher. If a BiMap of class aliases is provided, it can also match a class
   * paired with the source check class in the BiMap.
   *
   * <p>Note: Do not use {@link Matchers#is} for a {@link Class}, as the deprecated form will match
   * only objects of that class instead of the class object itself. Use {@link Matchers#equalTo}
   * instead.
   *
   * @param classMatcher a {@code Matcher} for a {@code Class<? extends AccessibilityCheck>}. Note:
   *     strict typing not enforced for Java 7 compatibility
   * @return a {@code Matcher} for a {@code AccessibilityCheckResult}
   */
  static Matcher<AccessibilityCheckResult> matchesChecks(
      final Matcher<?> classMatcher, final @Nullable ImmutableBiMap<?, ?> aliases) {
    return new TypeSafeMemberMatcher<AccessibilityCheckResult>("source check", classMatcher) {
      @Override
      public boolean matchesSafely(AccessibilityCheckResult result) {
        Class<? extends AccessibilityCheck> checkClass = result.getSourceCheckClass();
        if (classMatcher.matches(checkClass)) {
          return true;
        }
        Object alias = getAlias(checkClass, aliases);
        return (alias != null) && classMatcher.matches(alias);
      }
    };
  }

  /**
   * Returns a {@link Matcher} for an {@link AccessibilityCheckResult} whose source check class has
   * a simple name that matches the given matcher for a {@code String}.
   *
   * @param classNameMatcher a {@code Matcher} for a {@code String}
   * @return a {@code Matcher} for an {@code AccessibilityCheckResult}
   */
  public static Matcher<AccessibilityCheckResult> matchesCheckNames(
      Matcher<? super String> classNameMatcher) {
    return matchesCheckNames(classNameMatcher, /* aliases= */ null);
  }

  /**
   * Returns a {@link Matcher} for an {@link AccessibilityCheckResult} whose source check class has
   * a simple name that matches the given matcher for a {@code String}. If a BiMap of class aliases
   * is provided, it can also match a class paired with the source check class in the BiMap.
   *
   * @param classNameMatcher a {@code Matcher} for a {@code String}
   * @return a {@code Matcher} for an {@code AccessibilityCheckResult}
   */
  static Matcher<AccessibilityCheckResult> matchesCheckNames(
      final Matcher<? super String> classNameMatcher,
      final @Nullable ImmutableBiMap<?, ?> aliases) {
    return new TypeSafeMemberMatcher<AccessibilityCheckResult>(
        "source check name", classNameMatcher) {
      @Override
      public boolean matchesSafely(AccessibilityCheckResult result) {
        Class<? extends AccessibilityCheck> checkClass = result.getSourceCheckClass();
        if (classNameMatcher.matches(checkClass.getSimpleName())) {
          return true;
        }
        Object alias = getAlias(checkClass, aliases);
        return (alias instanceof Class)
            && classNameMatcher.matches(((Class<?>) alias).getSimpleName());
      }
    };
  }

  /**
   * Returns an alias for the given object, if it has one.
   *
   * <p>This supports aliasing in both directions. For example, a Matcher that specifies a new check
   * class can match a result with an old check class. Or a Matcher that specifies an old check
   * class can match a result with a new check class.
   */
  private static @Nullable Object getAlias(Object obj, @Nullable ImmutableBiMap<?, ?> aliases) {
    if (aliases == null) {
      return null;
    }
    Object alias = aliases.get(obj);
    return (alias != null) ? alias : aliases.inverse().get(obj);
  }

  abstract static class TypeSafeMemberMatcher<T> extends TypeSafeMatcher<T> {
    private static final String DESCRIPTION_FORMAT_STRING = "with %s: ";
    private final String memberDescription;
    private final Matcher<?> matcher;

    public TypeSafeMemberMatcher(String member, Matcher<?> matcher) {
      memberDescription = String.format(DESCRIPTION_FORMAT_STRING, member);
      this.matcher = matcher;
    }

    @Override
    public void describeTo(Description description) {
      description.appendText(memberDescription);
      matcher.describeTo(description);
    }
  }
}
