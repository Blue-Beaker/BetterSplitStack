package io.bluebeaker.bettersplitstack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSplitStack extends Gui {
    protected final int x;
    protected final int y;
    public final int totalCount;
    private int count;

    public final Slot slot;
    protected final int scale;
    protected final int left;

    protected final int guiLeft;
    protected final int guiTop;

    static FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
    public GuiSplitStack(int x, int y, Slot slot, int guiLeft, int guiTop){
        this.slot = slot;

        this.x=x;
        this.y=y;
        this.totalCount =slot.getStack().getCount();
        this.count=(int)Math.ceil((float)totalCount/2);
        this.guiLeft=guiLeft;
        this.guiTop=guiTop;

        this.scale=Math.max((int)Math.ceil((float)BetterSplitStackConfig.client.minSize/totalCount),BetterSplitStackConfig.client.scale);

        this.left=x-(this.scale*this.totalCount)/2;
    }
    public void updateCount(int mouseX,int mouseY){
        this.count=Math.max(Math.min((int)Math.ceil((float)(mouseX-left)/scale),this.totalCount),0);
    }
    public void draw(int mouseX,int mouseY){
        this.updateCount(mouseX-this.guiLeft,mouseY-this.guiTop);
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.translate(0,0,100.0F);
        drawRect(left,y-10,left+totalCount*scale,y,0xFF000000);
        drawRect(left,y-10,left+count*scale,y,0xFF00B000);
        drawCenteredString(fr,this.count+"/"+this.totalCount,x,y-9,0xFFFFFFFF);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    public int getCount() {
        return count;
    }
}
