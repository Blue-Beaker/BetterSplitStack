package io.bluebeaker.bettersplitstack;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ActionSplitStack {
    public final Container container;
    public final EntityPlayer player;
    public final int slotID;
    public final int newCount;

    public ActionSplitStack(Container container, EntityPlayer player , int slotID, int newCount) {
        this.container = container;
        this.player=player;
        this.slotID = slotID;
        this.newCount = newCount;
    }

    public boolean apply() {
        if(slotID>container.inventorySlots.size())
            return false;

        Slot slot = container.getSlot(slotID);
        if(!slot.canTakeStack(player))
            return false;

        ItemStack newStack = slot.decrStackSize(newCount);
        ItemStack newStack2 = slot.onTake(player, newStack);

        player.inventory.setItemStack(newStack2);
        return true;
    }
}
