package client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import util.ChannelPool;
import util.Message;
import util.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ClientController implements Initializable {
    @FXML
    private TextField ipField;
    @FXML
    private TextField portField;
    @FXML
    private TextArea chatBox;
    @FXML
    private TableView clientList;
    @FXML
    private TableColumn sendColumn;
    @FXML
    private TableColumn clientColumn;
    @FXML
    private TextArea messageBox;

    private ObservableList<ClientListItem> clientItemList;

    //channelPool保存了连接到其它客户端的channel
    //key为对方客户端的ip端口
    //value为channel
    private ChannelPool channelPool;
    private Client client;

    public void showClientInfo(String ipPort){
//        String clientInfo = client.remoteAddress().toString();
        clientItemList.add(new ClientListItem(false,ipPort));
//        messageBox.appendText("1 connect from "+clientInfo+"\n");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        StringConverter<Object> sc = new StringConverter<Object>() {
            @Override
            public String toString(Object t) {
                return t == null ? null : t.toString();
            }

            @Override
            public Object fromString(String string) {
                return string;
            }
        };

        clientItemList = FXCollections.observableArrayList();

        sendColumn.setCellValueFactory(new PropertyValueFactory("send"));
        sendColumn.setCellFactory(CheckBoxTableCell.forTableColumn(sendColumn));
        clientColumn.setCellValueFactory(new PropertyValueFactory("IPPort"));
        clientColumn.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        clientList.setItems(clientItemList);
        channelPool = new ChannelPool();
    }

    public void showLocalIPPort(String ip,int port) {
        ipField.setText(ip);
        portField.setText(String.valueOf(port));
    }

    public void showMessage(String message) {
        messageBox.appendText(message+"\n");
    }

    @FXML
    private void send() throws IOException {
        String message = chatBox.getText();
        chatBox.clear();
        String iPPort;
        SocketChannel client;
        for(ClientListItem item: clientItemList){
            if(item.sendProperty().getValue()){
                iPPort = item.IPPortProperty().getValue();
                client = channelPool.get(iPPort);
                client.writeAndFlush(new Message(Message.CLIENT_CHAT,this.client.getIpPort()+" "+message));
            }
        }
    }

    public void removeClient(String iPPort) {
        for(ClientListItem item: clientItemList){
            if(item.IPPortProperty().getValue().equals(iPPort)){
                clientItemList.remove(item);
                return;
            }
        }
    }

    public void addChannel(SocketChannel channel) {
        channelPool.put(Util.getRemoteIpPort(channel),channel);
    }

    public void setClient(Client client) {
        this.client = client;
        channelPool.setClient(client);
    }

    public ChannelPool getChannelPool() {
        return channelPool;
    }
}
