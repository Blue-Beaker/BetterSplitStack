package io.bluebeaker.bettersplitstack.utils;

import io.bluebeaker.bettersplitstack.mixin.AccessorGuiContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContainerUtils {
    static Minecraft mc = Minecraft.getMinecraft();
    public static ItemStack getHeldStack(GuiContainer container){
        ItemStack draggedStack = ((AccessorGuiContainer) container).getDraggedStack();
        return draggedStack.isEmpty() ? mc.player.inventory.getItemStack() : draggedStack;
    }
}
