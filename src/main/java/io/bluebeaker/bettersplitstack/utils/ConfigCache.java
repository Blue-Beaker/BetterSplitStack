package io.bluebeaker.bettersplitstack.utils;

import io.bluebeaker.bettersplitstack.BetterSplitStackConfig;

public class ConfigCache {
    public static Colors colors = new Colors();

    public static class Colors{
        public int fgColor = 0xFF00B000;
        public int bgColor = 0xFF202020;
        public int borderColor = 0xFF000000;
        public void update(){
            BetterSplitStackConfig.Colors colors1 = BetterSplitStackConfig.client.colors;
            this.bgColor=colors1.bgColor.toInt();
            this.fgColor=colors1.fgColor.toInt();
            this.borderColor=colors1.borderColor.toInt();
        }
    }
}
