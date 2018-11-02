package util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

public class MessageToByteBufferHandler extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        System.out.println("MessageToByteBufferHandler encode");
        int length = 4 + msg.getContent().length()*2 + 2;
        out.writeInt(length);
        System.out.println("length:"+length);
        out.writeInt(msg.getType());
        out.writeCharSequence(msg.getContent(), CharsetUtil.UTF_16);
    }
}
