package com.eco.beminichat.util.avatar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ParameterAvatar {
    NAME("name"),
    SIZE("size"),
    BACKGROUND("background"),
    COLOR("color"),
    LENGTH("length"),
    FONT_SIZE("font-size"),
    ROUNDED("rounded"),
    UPPERCASE("uppercase"),
    BOLD("bold"),
    FORMAT("format");

    private final String value;

}
