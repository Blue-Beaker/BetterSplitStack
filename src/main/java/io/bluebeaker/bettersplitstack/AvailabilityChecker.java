package io.bluebeaker.bettersplitstack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AvailabilityChecker {

    private static boolean availableOnServer = false;

    public static void onConnected(boolean availableOnServer){
        AvailabilityChecker.availableOnServer=availableOnServer;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isAvailableAndEnabled(){
        return availableOnServer && BetterSplitStackConfig.client.enabled;
    }
}
