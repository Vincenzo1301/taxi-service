package edu.hm.network.group12.dto;

public class LightStateDto {

    /**
     * The dynamic effect of the light. Currently “none” and “colorloop” are supported.
     */
    private final String effect;

    /**
     * The light is performing breathe cycles for 15 seconds for "lselect" or until an "alert": "none" command is received
     */
    private final String alert;

    /**
     * Color of the light. This is a wrapping value between 0 and 65535.
     */
    private final int hue;

    private LightStateDto(LightStateDtoBuilder lightStateDtoBuilder) {
        this.effect = lightStateDtoBuilder.effect;
        this.alert = lightStateDtoBuilder.alter;
        this.hue = lightStateDtoBuilder.hue;
    }

    public String getEffect() {
        return effect;
    }

    public String getAlert() {
        return alert;
    }

    public int getHue() {
        return hue;
    }

    public static class LightStateDtoBuilder {

        private String effect;
        private String alter;
        private int hue;

        public LightStateDtoBuilder effect(String effect) {
            this.effect = effect;
            return this;
        }

        public LightStateDtoBuilder alert(String alert) {
            this.alter = alert;
            return this;
        }

        public LightStateDtoBuilder hue(int hue) {
            if (hue < 0 || hue > 65535) {
                throw new IllegalArgumentException("The value for hue has to be between 0 and 65535.");
            }
            this.hue = hue;
            return this;
        }

        public LightStateDto build() {
            return new LightStateDto(this);
        }
    }
}
