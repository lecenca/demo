package util;

public class Message {

    private int type;
    private String content;

    public Message(int type, String content){
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    //消息类型
    // 1：活跃客户端列表
    public static final int CLIENT_LIST = 1;
    // 2：新增加的活跃客户端
    public static final int NEW_CLIENT = 2;
    // 3：退出的客户端
    public static final int OUT_CLIENT = 3;
    // 4：由客户端发给服务器的，客户端自己的IP和端口。服务器会将其转发给其它客户端
    public static final int IPPORT_FROM_CLIENT = 4;
    // 5：客户端相互通信
    public static final int CLIENT_CHAT = 5;
    // 6：另一客户端发来的连接请求
    public static final int CONNECT_REQUEST = 6;
}
