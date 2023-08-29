package com.eco.beminichat.util.avatar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AvatarGenerator {

    private final String AVATAR_URL = "https://ui-avatars.com/api/";

    private final CoupleColor[] coupleColors = new CoupleColor[]{
            color("343148", "D7C49E"),
            color("FFD662", "00539C"),
            color("00539C", "FFD662"),
            color("422057", "FCF951"),
            color("FCF951", "422057"),
            color("28334A", "FBDE44"),
            color("949398", "F4DF4E"),
            color("F4DF4E", "E69A8D"),
            color("E69A8D", "F4DF4E"),
            color("ADEFD1", "00203F"),
            color("CCF381", "4831D4"),
            color("E2D1F9", "317773"),
            color("FCEDDA", "EE4E34"),
            color("ADD8E6", "00008B"),
            color("99F443", "EC449B"),
            color("F7C5CC", "CC313D"),
            color("C5FAD5", "AA96DA"),

    };

    private final Map<ParameterAvatar, String> param = new HashMap<>();
    ;

    private CoupleColor color(String bg, String color) {
        return new CoupleColor(bg, color);
    }

    private CoupleColor getRandomColor() {
        return coupleColors[(int) (Math.random() * coupleColors.length)];
    }

    public AvatarGenerator() {
        param.put(ParameterAvatar.SIZE, "200");
        param.put(ParameterAvatar.BOLD, "true");
        param.put(ParameterAvatar.FONT_SIZE, "0.55");
    }

    public String generate(String name) {

        param.put(ParameterAvatar.NAME, name);
        CoupleColor coupleColor = getRandomColor();
        param.put(ParameterAvatar.BACKGROUND, coupleColor.getBackground());
        param.put(ParameterAvatar.COLOR, coupleColor.getColor());
        return AVATAR_URL + "?" + param
                .entrySet()
                .stream()
                .map(entry -> entry.getKey().getValue() + "=" + entry.getValue())
                .reduce((a, b) -> a + "&" + b)
                .orElse("");
    }


    @AllArgsConstructor
    @Getter
    protected static class CoupleColor {
        private String background;
        private String color;
    }


}

