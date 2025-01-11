package io.bluebeaker.bettersplitstack;

import io.bluebeaker.bettersplitstack.utils.ConfigCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSplitStack extends Gui {
    protected final int x;
    protected final int y;

    private final int totalCount;
    private int count;

    private final GuiContainer container;
    private final Slot slot;
    protected final int scale;
    protected final int left;
    protected final int width;

    private final long startTime;
    private boolean active = false;

    static FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
    public GuiSplitStack(int x, int y,GuiContainer container, Slot slot){
        this.slot = slot;
        this.container = container;

        this.x=x;
        this.y=y;
        this.totalCount =slot.getStack().getCount();
        this.count=(int)Math.ceil((float) getTotalCount() /2);

        this.scale=Math.max((int)Math.ceil((float)BetterSplitStackConfig.client.minSize/ getTotalCount()),BetterSplitStackConfig.client.scale);

        this.left=x-(this.scale* this.getTotalCount())/2;
        this.width=getTotalCount() *scale;

        this.startTime=System.currentTimeMillis();
    }
    public void updateCount(int mouseX,int mouseY){
        this.count=Math.max(Math.min((int)Math.ceil((float)(mouseX-left)/scale), this.getTotalCount()),0);
    }
    public void draw(int mouseX,int mouseY){

        if(this.active || System.currentTimeMillis()-BetterSplitStackConfig.client.delay>this.startTime){
            this.active=true;
            this.updateCount(mouseX,mouseY);
        }

        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        //Border
        drawRect(left,y-11,left+width,y+1, ConfigCache.colors.borderColor);
        drawRect(left-1,y-10,left+width+1,y, ConfigCache.colors.borderColor);

        drawRect(left,y-10,left+width,y, ConfigCache.colors.bgColor);
        drawRect(left,y-10,left+count*scale,y, ConfigCache.colors.fgColor);

        drawCenteredString(fr,this.count+"/"+ this.getTotalCount(),x,y-9,0xFFFFFFFF);
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    public int getCount() {
        return count;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public Slot getSlot() {
        return slot;
    }

    public GuiContainer getContainer() {
        return container;
    }
}
