package io.bluebeaker.bettersplitstack.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SplitStackMessage implements IMessage {
    public SplitStackMessage(){
    }

    private int slotID;
    private int count;
    private int windowID;

    public SplitStackMessage(int windowID, int slotID, int count) {
        this.slotID = slotID;
        this.count = count;
        this.windowID = windowID;
    }

    @Override public void toBytes(ByteBuf buf) {
        buf.writeInt(windowID);
        buf.writeInt(slotID);
        buf.writeInt(count);
    }

    @Override public void fromBytes(ByteBuf buf) {
        windowID = buf.readInt();
        slotID = buf.readInt();
        count = buf.readInt();
    }

    public int getCount() {
        return count;
    }

    public int getSlotID() {
        return slotID;
    }

    public int getWindowID() {
        return windowID;
    }

    public static class SplitStackMessageHandler implements IMessageHandler<SplitStackMessage, IMessage> {

        @Override public IMessage onMessage(SplitStackMessage message, MessageContext ctx) {
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            serverPlayer.getServerWorld().addScheduledTask(() -> {
                SplitPacketHandler.handleSplitServer(serverPlayer, message);
            });
            // No response packet
            return null;
        }
    }
}
