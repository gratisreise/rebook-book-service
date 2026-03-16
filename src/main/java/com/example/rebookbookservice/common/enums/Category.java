package com.example.rebookbookservice.common.enums;

public enum Category {
  소설,
  자기계발,
  어린이_청소년,
  IT_컴퓨터,
  예술_문화,
  경영_경제,
  건강_취미;

  public static Category fromName(String name) {
    for (Category category : values()) {
      if (category.name().equals(name)) {
        return category;
      }
    }
    throw new IllegalArgumentException("Unknown category: " + name);
  }

  public static String allCategory() {
    StringBuilder sb = new StringBuilder();
    for (Category category : values()) {
      sb.append(category.name()).append(", ");
    }
    return sb.substring(0, sb.lastIndexOf(","));
  }
}
