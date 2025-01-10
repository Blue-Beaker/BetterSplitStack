package io.bluebeaker.bettersplitstack.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
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

        if(serverPlayer.openContainer.windowId == windowID){
            if(slotID>=serverPlayer.openContainer.inventorySlots.size()) return;

            ItemStack stack1 = serverPlayer.openContainer.getSlot(slotID).getStack();

            ItemStack stack2 = stack1.copy();
            stack2.setCount(count);
            serverPlayer.inventory.setItemStack(stack2);

            stack1.setCount(stack1.getCount()-count);
        }
    }
}
