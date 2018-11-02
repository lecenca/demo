package client.handler;

import client.Client;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import util.Message;

public class ActiveHandler extends ChannelInboundHandlerAdapter {

    private Client client;

    public ActiveHandler(Client client){
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        System.out.println("ActiveHandler active");
        ctx.channel().writeAndFlush(new Message(Message.IPPORT_FROM_CLIENT,client.getIpPort()));
    }
}
