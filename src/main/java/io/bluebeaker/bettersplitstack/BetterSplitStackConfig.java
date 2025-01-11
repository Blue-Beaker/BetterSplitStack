package io.bluebeaker.bettersplitstack;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = BetterSplitStackMod.MODID,type = Type.INSTANCE,category = "general")
public class BetterSplitStackConfig {

    @Comment("Values in this category are client-side only, and have no effects on server-side.")
    @LangKey("config.bettersplitstack.client.name")
    public static Client client = new Client();

    public static class Client{
        @Comment("Whether to enable this mod on your client.")
        @LangKey("config.bettersplitstack.client.enabled.name")
        public boolean enabled = true;
        @Comment({"Scale of the slider. ",
                "Slider length = stack count * scale. "})
        @LangKey("config.bettersplitstack.client.scale.name")
        @Config.RangeInt(min = 1)
        public int scale = 2;
        @Comment("Minimum length of the slider")
        @LangKey("config.bettersplitstack.client.minSize.name")
        @Config.RangeInt(min = 1)
        public int minSize = 24;

        public Colors colors = new Colors();

    }

    public static class Colors{
        public Color fgColor = new Color(0xFF00B000);
        public Color bgColor = new Color(0xFF202020);
        public Color borderColor = new Color(0xFF000000);
    }

    public static class Color{
        @Config.RangeInt(min = 0,max = 255)
        public int red;
        @Config.RangeInt(min = 0,max = 255)
        public int green;
        @Config.RangeInt(min = 0,max = 255)
        public int blue;
        @Config.RangeInt(min = 0,max = 255)
        public int alpha;

        Color(int red, int green, int blue, int alpha) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }

        Color(int color){
            this.alpha = color>>24 & 0xFF;
            this.red = color>>16 & 0xFF;
            this.green = color>>8 & 0xFF;
            this.blue = color & 0xFF;
        }

        public int toInt(){
            return (this.alpha<<24) + (this.red<<16) + (this.green<<8) + this.blue;
        }

    }

}