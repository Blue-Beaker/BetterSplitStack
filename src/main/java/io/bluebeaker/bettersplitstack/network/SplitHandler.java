package io.bluebeaker.bettersplitstack.network;

import io.bluebeaker.bettersplitstack.ActionSplitStack;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SplitHandler
{
    @SideOnly(Side.CLIENT)
    public static void handleSplitClient(int windowID,int slotID,int count){
        BSSNetworkHandler.INSTANCE.sendToServer(new SplitStackMessage(windowID,slotID,count));
    }

    public static void handleSplitServer(EntityPlayerMP serverPlayer, SplitStackMessage message){
        int windowID = message.getWindowID();
        int slotID = message.getSlotID();
        int count = message.getCount();

        serverPlayer.markPlayerActive();

        if(serverPlayer.openContainer.windowId == windowID){

            ActionSplitStack action = new ActionSplitStack(serverPlayer.openContainer, serverPlayer, slotID, count);
            boolean applied = action.apply();
            if(!applied){
                serverPlayer.sendContainerToPlayer(serverPlayer.openContainer);
            }
        }
    }
}
