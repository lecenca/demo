package client.handler;

import client.Client;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import util.ChannelPool;
import util.Message;

public class ConnectHandler extends SimpleChannelInboundHandler<Message> {

    private Client client;
    private ChannelPool channelPool;

    public ConnectHandler(Client client){
        this.client = client;
        channelPool = client.getController().getChannelPool();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        String ipPort = msg.getContent().split("\\&")[0];
        channelPool.put(ipPort, (SocketChannel) ctx.channel());
    }
}
