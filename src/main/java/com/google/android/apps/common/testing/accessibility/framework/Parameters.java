package com.google.android.apps.common.testing.accessibility.framework;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.android.apps.common.testing.accessibility.framework.utils.contrast.Image;
import org.checkerframework.checker.nullness.qual.Nullable;

/** Supplemental input data or preferences for an {@link AccessibilityHierarchyCheck}. */
public class Parameters {

  @Nullable private Image screenCapture;
  @Nullable private Double customTextContrastRatio;
  @Nullable private Double customImageContrastRatio;
  @Nullable private Integer customTouchTargetSize;
  @Nullable private Boolean enableEnhancedContrastEvaluation;
  @Nullable private Boolean saveViewImage;

  public Parameters() {
    super();
  }

  /**
   * Gets screen capture data.
   *
   * @return {@link Image} containing screen capture data, or {@code null} if no screen capture data
   *     was set.
   * @see #putScreenCapture(Image)
   */
  public @Nullable Image getScreenCapture() {
    return screenCapture;
  }

  /**
   * Sets screen capture data in {@code parameters}. This is typically used by checks that assess
   * properties of the visual appearance of an interface. For example, contrast evaluation samples
   * regions of the display to heuristically evaluate foreground/background contrast ratios.
   *
   * <p>Expected data: A full image of the device's default display at the time check execution
   * occurs. The image should have dimensions which match the DisplayMetrics obtained from
   * WindowManager's default display. The screen area should not be altered, cropped, or adjusted in
   * any way.
   *
   * @param image {@link Image} containing screen capture data
   */
  public void putScreenCapture(Image image) {
    screenCapture = checkNotNull(image);
  }

  /**
   * Specifies a preference for whether images of the subject Views should be preserved. These may
   * be useful for debugging.
   *
   * <p>Presently, this is only used for heuristic contrast evaluation.
   */
  public void setSaveViewImages(boolean save) {
    saveViewImage = save;
  }

  /** Gets the preference for whether images of the subject Views should be preserved. */
  public @Nullable Boolean getSaveViewImages() {
    return saveViewImage;
  }

  /**
   * Gets a user-defined minimum contrast ratio.
   *
   * @return The user-defined minimum contrast ratio, or {@code null} if a user-defined value has
   *     not been set.
   * @see #putCustomContrastRatio(double)
   * @deprecated Use {@link #getCustomTextContrastRatio} and {@link #getCustomImageContrastRatio}
   */
  @Deprecated
  public @Nullable Double getCustomContrastRatio() {
    throw new UnsupportedOperationException();
  }

  /**
   * Gets a user-defined minimum text contrast ratio.
   *
   * @return The user-defined minimum text contrast ratio, or {@code null} if a user-defined value
   *     has not been set.
   * @see #putCustomTextContrastRatio(double)
   */
  public @Nullable Double getCustomTextContrastRatio() {
    return customTextContrastRatio;
  }

  /**
   * Gets a user-defined minimum image contrast ratio.
   *
   * @return The user-defined minimum image contrast ratio, or {@code null} if a user-defined value
   *     has not been set.
   * @see #putCustomImageContrastRatio(double)
   */
  public @Nullable Double getCustomImageContrastRatio() {
    return customImageContrastRatio;
  }

  /**
   * Sets a user-defined minimum contrast ratio for use by {@link
   * com.google.android.apps.common.testing.accessibility.framework.checks.ImageContrastCheck} or
   * {@link
   * com.google.android.apps.common.testing.accessibility.framework.checks.TextContrastCheck}. A
   * value set here should override the default value used by those checks.
   *
   * @param contrastRatio a user-defined minimum contrast ratio
   * @deprecated Use {@link #putCustomTextContrastRatio(double)} and {@link
   *     #putCustomImageContrastRatio(double)}
   */
  @Deprecated
  public void putCustomContrastRatio(double contrastRatio) {
    putCustomTextContrastRatio(contrastRatio);
    putCustomImageContrastRatio(contrastRatio);
  }

  /**
   * Sets a user-defined minimum text contrast ratio for use by {@link
   * com.google.android.apps.common.testing.accessibility.framework.checks.TextContrastCheck}. A
   * value set here should override the default value used by this check.
   *
   * @param textContrastRatio a user-defined minimum text contrast ratio
   */
  public void putCustomTextContrastRatio(double textContrastRatio) {
    customTextContrastRatio = textContrastRatio;
  }

  /**
   * Sets a user-defined minimum contrast ratio for use by {@link
   * com.google.android.apps.common.testing.accessibility.framework.checks.ImageContrastCheck}. A
   * value set here should override the default value used by this check.
   *
   * @param imageContrastRatio a user-defined minimum image contrast ratio
   */
  public void putCustomImageContrastRatio(double imageContrastRatio) {
    customImageContrastRatio = imageContrastRatio;
  }

  /**
   * Gets a user-defined minimum touch target size from {@code parameters}.
   *
   * @return The user-defined minimum touch target size, or {@code null} if a user-defined value has
   *     not been set.
   * @see #putCustomTouchTargetSize(int)
   */
  public @Nullable Integer getCustomTouchTargetSize() {
    return customTouchTargetSize;
  }

  /**
   * Sets a user-defined minimum touch target size for use by {@link
   * com.google.android.apps.common.testing.accessibility.framework.checks.TouchTargetSizeCheck}. A
   * value set here should override the default value used by the check.
   *
   * @param touchTargetSize a user-defined minimum touch target size in pixels
   */
  public void putCustomTouchTargetSize(int touchTargetSize) {
    customTouchTargetSize = touchTargetSize;
  }

  /**
   * Gets an user-defined boolean value for enabling enhanced contrast evaluation from {@code
   * parameters}.
   *
   * @return The boolean value that turns on/off an enhanced contrast evaluation, or {@code null} if
   *     a user-defined value has not been set.
   * @see #putEnhancedContrastEvaluationMode(boolean)
   */
  public @Nullable Boolean getEnableEnhancedContrastEvaluation() {
    return enableEnhancedContrastEvaluation;
  }

  /**
   * Sets a user-defined boolean value for enabling enhanced contrast evaluation use by {@link
   * com.google.android.apps.common.testing.accessibility.framework.checks.ImageContrastCheck} and
   * {@link com.google.android.apps.common.testing.accessibility.framework.checks.TextContrastCheck}
   * that evaluates multiple foreground colors. A value set here should override the default value
   * used by the check.
   *
   * @param enableEnhancedContrastEvaluation {@code true} to enable enhanced contrast evaluation,
   *     {@code false} to disable.
   */
  public void putEnableEnhancedContrastEvaluation(boolean enableEnhancedContrastEvaluation) {
    this.enableEnhancedContrastEvaluation = enableEnhancedContrastEvaluation;
  }
}
