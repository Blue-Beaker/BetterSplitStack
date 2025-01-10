package io.bluebeaker.bettersplitstack.network;

import io.bluebeaker.bettersplitstack.BetterSplitStackMod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class BSSNetworkHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(BetterSplitStackMod.MODID);

    public static void init(){
        INSTANCE.registerMessage(SplitStackMessage.SplitStackMessageHandler.class, SplitStackMessage.class, 1, Side.SERVER);
    }

}
