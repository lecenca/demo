package client.handler;

import client.Client;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import util.ByteBufferToMessageHandler;
import util.MessageToByteBufferHandler;

public class ChatHandlerInitializer extends ChannelInitializer<SocketChannel> {

    private Client client;

    public ChatHandlerInitializer(Client client){
        this.client = client;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //inbound
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        pipeline.addLast(new ByteBufferToMessageHandler());
        pipeline.addLast(new ChatHandler(client));
        pipeline.addLast(new ConnectHandler(client));

        //outbound
        pipeline.addLast(new MessageToByteBufferHandler());
    }
}
