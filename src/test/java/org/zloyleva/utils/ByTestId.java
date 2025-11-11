package org.zloyleva.utils;

import org.openqa.selenium.By;

public class ByTestId {
  public static By testId (String testid) {
    return By.cssSelector("[data-testid='" + testid + "']");
  }
}
