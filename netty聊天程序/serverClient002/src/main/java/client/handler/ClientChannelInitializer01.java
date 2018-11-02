package client.handler;

import client.Client;
import client.ClientController;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import util.ByteBufferToMessageHandler;
import util.MessageToByteBufferHandler;

public class ClientChannelInitializer01 extends ChannelInitializer<SocketChannel> {

    private Client client;

    public ClientChannelInitializer01(Client client){
        this.client = client;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //inbound
        pipeline.addLast(new ActiveHandler(client));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        pipeline.addLast(new ByteBufferToMessageHandler());
        pipeline.addLast(new ClientInfoHandler(client.getController()));

        //outbound
        pipeline.addLast(new MessageToByteBufferHandler());
    }
}
