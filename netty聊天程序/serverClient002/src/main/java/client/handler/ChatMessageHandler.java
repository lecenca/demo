package client.handler;


import client.ClientController;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import util.Message;

public class ChatMessageHandler extends SimpleChannelInboundHandler<Message> {

    private ClientController controller;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

    }
}
