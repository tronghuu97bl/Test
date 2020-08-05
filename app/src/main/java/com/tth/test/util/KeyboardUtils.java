package com.tth.test.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public class KeyboardUtils {

  private KeyboardUtils() {
    // hides public constructor
  }

  public static void showKeyboard (View view) {
    if (view == null) {
      return;
    }

    view.requestFocus();

    InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(
            Context.INPUT_METHOD_SERVICE);
    inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);

    ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);

    if (!isKeyboardShowed(view)) {
      inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
  }
  public static void showKeyboard2 (View view) {
    view.requestFocus();
    InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);
      inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
  }

  public static boolean isKeyboardShowed (View view) {
    if (view == null) {
      return false;
    }
    InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(
        Context.INPUT_METHOD_SERVICE);
    return inputManager.isActive(view);
  }


  public static void hideKeyboard (View view) {
    if (view == null) {
      return;
    }
    InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    if (!imm.isActive()) {
      return;
    }
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }

}
