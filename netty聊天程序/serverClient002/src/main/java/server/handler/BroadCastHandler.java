package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import server.Server;
import util.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import util.Util;

public class BroadCastHandler extends SimpleChannelInboundHandler<Message> {

    //客户端聊天地址与客户端和服务器连接的对应表
    //key为客户端聊天ip端口
    //value为客户端和服务器连接（channel）
    private ConcurrentSkipListMap<String,SocketChannel> activeClientList;

    //客户端的服务器连接ip端口与聊天ip端口的对应表
    //key为客户端的服务器连接ip端口
    //value为该客户端的聊天ip端口
    private ConcurrentHashMap<String,String> ipPortMap;

    public BroadCastHandler(Server server){
        super();
        this.activeClientList = server.getActiveClientList();
        this.ipPortMap = server.getIpPortMap();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        System.out.println("BroadCastHandler read");
        int type = msg.getType();
        String ipPort = msg.getContent();
        SocketChannel channel = (SocketChannel) ctx.channel();
        if(type==Message.IPPORT_FROM_CLIENT){
            sendClientList(channel); //把客户端列表发给新来的客户端
            broadcastNewClient(ipPort);  //把新来的客户端的ip端口广播给其他客户端
            activeClientList.put(ipPort,channel); //将新客户端加入活跃用户列表
            ipPortMap.put(Util.getRemoteIpPort(channel),ipPort);
        }else{
            //抛个异常
        }
    }

    private void sendClientList(SocketChannel channel) {
        System.out.println("sendClientList");
        if(activeClientList.isEmpty())
            return;
        StringBuilder stringBuilder = new StringBuilder();
        for(String item : activeClientList.keySet()){
            stringBuilder.append(item);
            stringBuilder.append('|');
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        channel.writeAndFlush(new Message(Message.CLIENT_LIST,stringBuilder.toString()));
    }

    private void broadcastNewClient(String ipPort){
        System.out.println("broadcastNewClient");
        System.out.println(ipPort);
        Message msg = new Message(Message.NEW_CLIENT,ipPort);
        if(activeClientList.isEmpty())
            return;
        for(Map.Entry<String,SocketChannel> item : activeClientList.entrySet()){
            item.getValue().writeAndFlush(msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        SocketChannel channel = (SocketChannel) ctx.channel();
        String remoteIpPort = Util.getRemoteIpPort(channel);
        String ipPort = ipPortMap.get(remoteIpPort);
        activeClientList.remove(ipPort);
        ipPortMap.remove(remoteIpPort);

        if(activeClientList.isEmpty())
            return;
        for(Map.Entry<String,SocketChannel> item : activeClientList.entrySet()){
            item.getValue().writeAndFlush(new Message(Message.OUT_CLIENT,ipPort));
        }
    }
}
