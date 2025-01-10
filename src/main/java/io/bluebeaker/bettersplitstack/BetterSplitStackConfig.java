package io.bluebeaker.bettersplitstack;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = BetterSplitStackMod.MODID,type = Type.INSTANCE,category = "general")
public class BetterSplitStackConfig {
    @Comment("Values in this category are client-only, have no effect on server-side.")
    @LangKey("config.bettersplitstack.client.name")
    public static Client client = new Client();
    public static class Client{
        @Comment("Whether to enable this mod on your client.")
        @LangKey("config.bettersplitstack.client.enabled.name")
        public boolean enabled = true;
        @Comment({"Scale of the split bar. ",
                "Bar length = stack count * scale. "})
        @LangKey("config.bettersplitstack.client.scale.name")
        public int scale = 2;
        @Comment("Minimum length of the split bar")
        @LangKey("config.bettersplitstack.client.minSize.name")
        public int minSize = 16;
    }

}