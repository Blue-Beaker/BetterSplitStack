package io.bluebeaker.bettersplitstack;

import io.bluebeaker.bettersplitstack.mixin.AccessorGuiContainer;
import io.bluebeaker.bettersplitstack.network.SplitHandler;
import io.bluebeaker.bettersplitstack.utils.ContainerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.inventory.ClickType;
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

/**
 * Manager class for handling split operations on client.
 */
@SideOnly(Side.CLIENT)
public class SplitManagerClient {
    static Minecraft mc = Minecraft.getMinecraft();
    public static @Nullable GuiSplitStack guiSplitStack;
    static int mouseX;
    static int mouseY;

    @SubscribeEvent
    public static void onDrawForeground(GuiContainerEvent.DrawForeground event){
        if(!AvailabilityChecker.isAvailableAndEnabled()) return;
        mouseX=event.getMouseX();
        mouseY=event.getMouseY();
        if(guiSplitStack!=null){
            guiSplitStack.draw(event.getMouseX(), event.getMouseY());
        }
    }
    @SubscribeEvent
    public static void cancelTooltip(RenderTooltipEvent.Pre event){
        if(!AvailabilityChecker.isAvailableAndEnabled()) return;
        if(guiSplitStack!=null){
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void onMouse(GuiScreenEvent.MouseInputEvent.Pre event){

        if(!AvailabilityChecker.isAvailableAndEnabled()) return;
        if(guiSplitStack!=null){
            event.setCanceled(true);
        }
        //Right Click only
        if(Mouse.getEventButton()!=1) return;

        GuiScreen gui = event.getGui();
        if(!(gui instanceof GuiContainer)) return;
        GuiContainer container = (GuiContainer) gui;
        AccessorGuiContainer accessor = (AccessorGuiContainer) container;

        if(Mouse.getEventButtonState()){
            //Press
            if(accessor.getDragSplitting() || !ContainerUtils.getHeldStack(container).isEmpty()) return;
            Slot slot = container.getSlotUnderMouse();
            if(slot==null || !slot.canTakeStack(mc.player)) return;
            ItemStack stack = slot.getStack();
            if(stack.getCount()<=1) return;

            guiSplitStack=new GuiSplitStack(mouseX-container.getGuiLeft(),slot.yPos-1,container,slot);
            event.setCanceled(true);

        }else {
            //Release
            if(guiSplitStack!=null){
                if(container instanceof GuiContainerCreative){
                    splitCreative((GuiContainerCreative) container,guiSplitStack);
                }
                else{
                    splitCommon(container,guiSplitStack);
                }
            }
            guiSplitStack=null;
        }

    }

    private static void splitCommon(GuiContainer container,GuiSplitStack guiSplitStack){
        int newCount = guiSplitStack.getCount();
        int totalCount = guiSplitStack.getTotalCount();
        Slot slot = guiSplitStack.getSlot();
        int slotID = slot.slotNumber;
        int windowId = container.inventorySlots.windowId;

        if(newCount == totalCount){
            mc.playerController.windowClick(windowId, slotID,0,ClickType.PICKUP,mc.player);
            return;
        }
        if(newCount == (int) Math.ceil((float)totalCount/2)){
            mc.playerController.windowClick(windowId, slotID,1,ClickType.PICKUP,mc.player);
            return;
        }

        ActionSplitStack action = new ActionSplitStack(container.inventorySlots, mc.player, slotID,newCount);
        boolean applied = action.apply();
        if(applied){
            SplitHandler.handleSplitClient(windowId,slotID,newCount);
        }
    }

    private static void splitCreative(GuiContainerCreative container, GuiSplitStack guiSplitStack){
        Slot slot = guiSplitStack.getSlot();
        int slotID = slot.getSlotIndex();
        int newCount = guiSplitStack.getCount();

        ActionSplitStack action = new ActionSplitStack(container.inventorySlots, mc.player, slotID,newCount);
        boolean applied = action.apply();
    }
}
