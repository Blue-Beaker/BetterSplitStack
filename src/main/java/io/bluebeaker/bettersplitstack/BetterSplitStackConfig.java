package io.bluebeaker.bettersplitstack;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = BetterSplitStack.MODID,type = Type.INSTANCE,category = "general")
public class BetterSplitStackConfig {
    @Comment("")
    @LangKey("config.bettersplitstack.enabled.name")
    public static boolean enabled = true;
}