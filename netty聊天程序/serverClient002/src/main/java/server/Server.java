package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import server.handler.ChildChannelInitializer;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class Server {

    //客户端聊天地址与客户端和服务器连接的对应表
    //key为客户端聊天ip端口
    //value为客户端和服务器连接（channel）
    private ConcurrentSkipListMap<String,SocketChannel> activeClientList;

    //客户端的服务器连接ip端口与聊天ip端口的对应表
    //key为客户端的服务器连接ip端口
    //value为该客户端的聊天ip端口
    private ConcurrentHashMap<String,String> ipPortMap;

    public static void main(String[] args) throws InterruptedException {
        new Server().run();
    }

    public void run() throws InterruptedException {
        activeClientList = new ConcurrentSkipListMap<String, SocketChannel>();
        ipPortMap = new ConcurrentHashMap<String, String>();
        initEventLoop();
    }

    private void initEventLoop() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress("127.0.0.1", 9999))
                    .childHandler(new ChildChannelInitializer(this));

            ChannelFuture f = sb.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }
    }

    public ConcurrentSkipListMap<String, SocketChannel> getActiveClientList() {
        return activeClientList;
    }

    public ConcurrentHashMap<String, String> getIpPortMap() {
        return ipPortMap;
    }
}
