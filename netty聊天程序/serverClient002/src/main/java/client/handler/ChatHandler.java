package client.handler;

import client.Client;
import client.ClientController;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import util.ChannelPool;
import util.Message;
import util.Util;

public class ChatHandler extends SimpleChannelInboundHandler<Message> {

    private Client client;
    private ClientController controller;
    private ChannelPool channelPool;

    public ChatHandler(Client client){
        this.client = client;
        controller = client.getController();
        channelPool = controller.getChannelPool();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        System.out.println("ChatHandler read");

        int type = msg.getType();
        if(type==Message.CONNECT_REQUEST){
            ctx.fireChannelRead(msg);
            return;
        }
        SocketChannel channel = (SocketChannel) ctx.channel();
        String ipPort = Util.getRemoteIpPort(channel);
        controller.showMessage(msg.getContent());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ChatHandler inactive");
        String ipPort = Util.getRemoteIpPort((SocketChannel) ctx.channel());
        channelPool.remove(ipPort);
    }
}
