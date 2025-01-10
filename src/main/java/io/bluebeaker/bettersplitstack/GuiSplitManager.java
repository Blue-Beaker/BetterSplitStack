package io.bluebeaker.bettersplitstack;

import io.bluebeaker.bettersplitstack.mixin.AccessorGuiContainer;
import io.bluebeaker.bettersplitstack.network.SplitPacketHandler;
import io.bluebeaker.bettersplitstack.utils.ContainerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class GuiSplitManager {
    static Minecraft mc = Minecraft.getMinecraft();
    public static @Nullable GuiSplitStack guiSplitStack;
    static int mouseX;
    static int mouseY;

    @SubscribeEvent
    public static void onDrawForeground(GuiContainerEvent.DrawForeground event){
        mouseX=event.getMouseX();
        mouseY=event.getMouseY();
        if(guiSplitStack!=null){
            guiSplitStack.draw(event.getMouseX(), event.getMouseY());
        }
    }
    @SubscribeEvent
    public static void cancelTooltip(RenderTooltipEvent.Pre event){
        if(guiSplitStack!=null){
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void onMouse(GuiScreenEvent.MouseInputEvent.Pre event){
        if(guiSplitStack!=null){
            event.setCanceled(true);
        }
        if(Mouse.getEventButton()!=1) return;
        GuiScreen gui = event.getGui();
        if(!(gui instanceof GuiContainer)) return;
        GuiContainer container = (GuiContainer) gui;
        AccessorGuiContainer accessor = (AccessorGuiContainer) container;

        if(Mouse.getEventButtonState()){
            if(accessor.getDragSplitting() || !ContainerUtils.getHeldStack(container).isEmpty()) return;
            Slot slot = container.getSlotUnderMouse();
            if(slot==null) return;
            ItemStack stack = slot.getStack();
            if(stack.getCount()<=1) return;

            guiSplitStack=new GuiSplitStack(mouseX-container.getGuiLeft(),slot.yPos,slot,container.getGuiLeft(),container.getGuiTop());
            event.setCanceled(true);
        }else {
            if(guiSplitStack!=null){
                int newCount = guiSplitStack.getCount();
                int slotID = guiSplitStack.slot.slotNumber;
                ItemStack newStack = guiSplitStack.slot.getStack().copy();
                newStack.setCount(newCount);
                guiSplitStack.slot.getStack().setCount(guiSplitStack.totalCount-newCount);
                mc.player.inventory.setItemStack(newStack);
                SplitPacketHandler.handleSplitClient(container.inventorySlots.windowId,slotID, newCount);
            }
            guiSplitStack=null;
        }

    }
}
