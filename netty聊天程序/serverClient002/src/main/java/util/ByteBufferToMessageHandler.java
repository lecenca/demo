package util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ByteBufferToMessageHandler extends SimpleChannelInboundHandler<ByteBuf>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("ByteBufferToMessageHandler read");
        int type = msg.readInt();
        String str = msg.toString(CharsetUtil.UTF_16);

        Message message = new Message(type,str);
        System.out.println("decode msg "+type+" "+str);
        ctx.fireChannelRead(message);
    }
}
