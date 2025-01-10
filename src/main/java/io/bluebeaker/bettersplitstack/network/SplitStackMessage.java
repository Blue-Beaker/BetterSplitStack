package io.bluebeaker.bettersplitstack.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SplitStackMessage implements IMessage {
    public SplitStackMessage(){}

    private int slotID;
    private int count;

    public SplitStackMessage(int slotID,int count) {
        this.slotID = slotID;
        this.count = count;
    }

    @Override public void toBytes(ByteBuf buf) {
        buf.writeInt(slotID);
        buf.writeInt(count);
    }

    @Override public void fromBytes(ByteBuf buf) {
        slotID = buf.readInt();
        count = buf.readInt();
    }
}
