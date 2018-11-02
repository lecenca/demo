package client;

import client.handler.ChatHandlerInitializer;
import client.handler.ClientChannelInitializer01;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class Client extends Application {

    private ClientController controller;
    private String ip;
    private int port;

    private NioEventLoopGroup group;
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;

    private NioServerSocketChannel serverSocketChannel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client.fxml"));
        Parent root = loader.load();
        controller = loader.<ClientController>getController();
        controller.setClient(this);

        primaryStage.setTitle("client");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        initComponent();

        ChannelFuture f1 = initChatEventLoop(bossGroup,workerGroup);
        serverSocketChannel = (NioServerSocketChannel) f1.channel();
        ChannelFuture f2 = initEventLoop(group);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        bossGroup.shutdownGracefully();
                        workerGroup.shutdownGracefully();
                        group.shutdownGracefully();
                    }
                });
            }
        });
    }

    private ChannelFuture initChatEventLoop(EventLoopGroup bossGroup,EventLoopGroup workerGroup) {
        ServerBootstrap sb = new ServerBootstrap();
        sb.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress("127.0.0.1", 0)
                .childHandler(new ChatHandlerInitializer(this));
        ChannelFuture future = null;
        try {
            future = sb.bind().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        NioServerSocketChannel serverSocketChannel = (NioServerSocketChannel) future.channel();
        ip = serverSocketChannel.localAddress().getHostName();
        port = serverSocketChannel.localAddress().getPort();
        controller.showLocalIPPort(ip, port);
        return future;
    }

    private ChannelFuture initEventLoop(EventLoopGroup group) {
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress("127.0.0.1", 9999))
                .handler(new ClientChannelInitializer01(this));
        ChannelFuture f = null;
        try {
            f = b.connect().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return f;
    }

    private void initComponent(){
        group = new NioEventLoopGroup();
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ClientController getController() {
        return controller;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getIpPort(){
        return ip+":"+port;
    }

    public NioEventLoopGroup getGroup() {
        return group;
    }

    public NioEventLoopGroup getBossGroup() {
        return bossGroup;
    }

    public NioEventLoopGroup getWorkerGroup() {
        return workerGroup;
    }

    public NioServerSocketChannel getServerSocketChannel() {
        return serverSocketChannel;
    }
}
