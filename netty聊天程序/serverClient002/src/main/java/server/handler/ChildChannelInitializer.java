package server.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import server.Server;
import util.ByteBufferToMessageHandler;
import util.MessageToByteBufferHandler;

import java.util.concurrent.ConcurrentSkipListMap;

public class ChildChannelInitializer extends ChannelInitializer<SocketChannel> {

    private Server server;

    public ChildChannelInitializer(Server server){
        super();
        this.server = server;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //inbound
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        ch.pipeline().addLast(new ByteBufferToMessageHandler());
        ch.pipeline().addLast(new BroadCastHandler(server));

        //outbound
        ch.pipeline().addLast(new MessageToByteBufferHandler());
    }
}
