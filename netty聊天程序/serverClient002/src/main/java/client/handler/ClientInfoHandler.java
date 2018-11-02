package client.handler;

import client.ClientController;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import util.Message;

public class ClientInfoHandler extends SimpleChannelInboundHandler<Message>{

    private ClientController controller;

    public ClientInfoHandler(ClientController controller){
        this.controller = controller;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        System.out.println("ClientInfoHandler read");
        int type = msg.getType();
        if(type==Message.NEW_CLIENT){
            controller.showClientInfo(msg.getContent());
        }else if(type==Message.CLIENT_LIST){
            String str = msg.getContent();
            String[] list = str.split("\\|");
            for(String info: list){
                controller.showClientInfo(info);
            }
        }else if(type == Message.OUT_CLIENT){
            controller.removeClient(msg.getContent());
        }
    }
}
