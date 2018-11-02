package util;

import io.netty.channel.socket.SocketChannel;

public class Util {
    public static String getRemoteIpPort(SocketChannel channel){
        return channel.remoteAddress().getHostName()+":"+channel.remoteAddress().getPort();
    }
}
