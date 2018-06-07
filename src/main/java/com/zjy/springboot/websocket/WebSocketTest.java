package com.zjy.springboot.websocket;

import com.zjy.springboot.model.dto.WcMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * @ServerEndpoint 可以把当前类变成websocket服务类
 */

@ServerEndpoint(value = "/websocket/{userId}")
@Component
public class WebSocketTest {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketTest.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<Integer, WebSocketTest> webSocketSet = new ConcurrentHashMap<Integer, WebSocketTest>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //当前发消息的人员编号
    private Integer userId = 0;

    /**
     * 连接建立成功调用的方法
     * 此处的userNo是客户端写死在客户端，本来个人想由服务端分配id
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "userId") Integer userId, Session session, EndpointConfig config) {
        this.userId = userId;//接收到发送消息的人员编号
        this.session = session;
        webSocketSet.put(userId, this);//加入map中
        addOnlineCount();           //在线数加1
        logger.info("[WebSocket] 用户id：" + userId + "连接；当前连接人数" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (userId != 0) {
            webSocketSet.remove(0);  //从set中删除
            subOnlineCount();           //在线数减1
            logger.info("[WebSocket] 用户id：" + userId + "连接；当前连接人数" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
//        session.get
        //群发消息
        if (1 < 2) {
            sendAll(message);
        } else {
            //给指定的人发消息
            sendToUser(message);//这是原作者对与聊天室的那种模式做的，个人并未做修改，但是本人做的推送方法，已经写了其他方法
        }
    }

    /**
     * 给指定的人发送消息
     *
     * @param message
     */
    private void sendToUser(String message) {
        String sendUserno = message.split("[|]")[1];
        String sendMessage = message.split("[|]")[0];
        String now = getNowTime();
        try {
            if (webSocketSet.get(sendUserno) != null) {
                webSocketSet.get(sendUserno).sendMessage(now + "用户" + userId + "发来消息：" + " <br/> " + sendMessage);
            } else {
                logger.info("[WebSocket] 向用户id：" + userId + "发送失败，用户不在线！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*个人做推送封装的方法，上面的sendToUser是做聊天室用*/
    public void pushMsg(WcMsg wcMsg) {
        Integer userId = wcMsg.getUserId();
        String sendMessage = wcMsg.getMessage();
        try {
            if (webSocketSet.get(userId) != null) {
                webSocketSet.get(userId).sendMessage(sendMessage);
                logger.info("[WebSocket] 向用户id：" + userId + "推送成功！");
            } else {
                logger.info("[WebSocket] 向用户id：" + userId + "推送失败，用户不在线！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*个人做推送封装的方法，给所有人推送*/
    public void pushMsgAll(String msg) {
        for (Integer key : webSocketSet.keySet()) {
            try {
                webSocketSet.get(key).sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("[WebSocket] 向所有用户推送成功！");
    }

    /**
     * 给所有人发消息
     *
     * @param message
     */
    private void sendAll(String message) {
        String now = getNowTime();
        String sendMessage = message.split("[|]")[0];
        //遍历HashMap
        for (Integer key : webSocketSet.keySet()) {
            try {
                //判断接收用户是否是当前发消息的用户
                if (userId != key) {
                    webSocketSet.get(key).sendMessage(now + "用户" + userId + "发来消息：" + " <br/> " + sendMessage);
                    System.out.println("key = " + key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    private String getNowTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketTest.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketTest.onlineCount--;
    }


}