package io.bluebeaker.bettersplitstack.mixin;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiContainer.class)
public interface AccessorGuiContainer {
    @Accessor("dragSplitting")
    public boolean getDragSplitting();
    @Accessor("draggedStack")
    public ItemStack getDraggedStack();
}
