package util;

import client.Client;
import client.handler.ChatHandlerInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelPool {

    private ConcurrentHashMap<String, SocketChannel> channelList;

    private Client client;

    public ChannelPool() {
        channelList = new ConcurrentHashMap<String, SocketChannel>();
    }

    public void put(String ipPort, SocketChannel channel) {
        channelList.put(ipPort, channel);
    }

    public SocketChannel get(String ipPort) {
        if (channelList.contains(ipPort))
            return channelList.get(ipPort);

        String arg[] = ipPort.split(":");
        String ip = arg[0];
        String port = arg[1];
        NioSocketChannel socketChannel = new NioSocketChannel();
        socketChannel.pipeline().addLast(new ChatHandlerInitializer(client));
        NioEventLoopGroup eventLoopGroup = client.getGroup();
        eventLoopGroup.register(socketChannel);
        try {
            socketChannel.connect(new InetSocketAddress(ip, Integer.parseInt(port))).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        socketChannel.writeAndFlush(new Message(Message.CONNECT_REQUEST,client.getIpPort()));
        channelList.put(ipPort,socketChannel);
        return socketChannel;
    }

    public boolean contains(String ipPort) {
        return channelList.contains(ipPort);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void remove(String ipPort) {
        channelList.remove(ipPort);
    }
}
