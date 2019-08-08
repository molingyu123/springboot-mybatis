//package com.example.demo.webSocket;
//
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import sun.rmi.runtime.Log;
//
//import javax.websocket.OnClose;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.concurrent.CopyOnWriteArraySet;
//
//@ServerEndpoint("/websocket/{sid}")
//@Component
//public class WebSocketServer {
//
//    static Log log= (Log) LoggerFactory.getLogger(WebSocketServer.class);
//
//    //记录当前线程数
//    private static int onlineCount =0;
//
//    //存放每个客户端对应的mywebSocket对象---arrySet 线程安全的
//    private static CopyOnWriteArraySet<WebSocketServer> webSocketServers = new CopyOnWriteArraySet<>();
//
//    // 通过session 进行会话
//    private Session session;
//
//    //接收sid
//    private String sid="";
//
//    /**
//     * 增加线程数
//     */
//    public static synchronized void addOnlineCount(){
//        WebSocketServer.onlineCount++;
//    }
//
//    /**
//     * 减去线程数
//     */
//    public static synchronized void subOnlineCount(){
//        WebSocketServer.onlineCount--;
//    }
//
//    /**
//     * 返回当前的线程数
//     * @return
//     */
//    public static synchronized  int getOnlineCount(){
//        return onlineCount;
//    }
//
//    /**
//     * 实现服务器主动推送
//     */
//    public void sendMessage(String message) throws Exception{
//        this.session.getBasicRemote().sendText(message);
//    }
//
//
//    /**
//     * 创建建立成功调用的方法
//     */
//    @OnOpen
//    public void onOpen(Session session, @PathParam("sid") String sid){
//        this.session=session;
//        // 添加到服务中
//        webSocketServers.add(this);
//        //线程数加1
//        addOnlineCount();
//        System.out.println("有新窗口开始监听:"+sid+",当前在线人数:"+getOnlineCount());
//        this.sid=sid;
//        try{
//            sendMessage("连接成功");
//        }catch (Exception e){
//            System.out.println("webSocket Io 异常");
//        }
//    }
//
//    /**
//     * 连接关闭调用的方法
//     */
//    @OnClose
//    public void onClose(){
//        webSocketServers.remove(this);//从上面的set 队列中删除
//        subOnlineCount();
//        System.out.println("有一连接关闭！当前在线人数为:"+getOnlineCount());
//    }
//
//    /**
//     * 收到客户端消息后调用的方法
//     */
//    public void onMessage(String message ,Session session){
//        System.out.println("收到来自窗口"+sid+"的信息:"+message);
//        //群发消息
//        for(WebSocketServer item:webSocketServers){
//            try {
//                item.sendMessage(message);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 群发自定义消息
//     */
//    public static void sendInfo(String message,@PathParam("sid") String sid)throws Exception{
//        System.out.println("推送消息到窗口"+sid+",推送内容:"+message);
//        for(WebSocketServer item:webSocketServers){
//            try{
//                //设置只推送给这个sid
//                if(sid==null){
//                    item.sendMessage(message);
//                }else if(item.equals(sid)){
//                    item.sendMessage(message);
//                }
//            }catch (Exception e){
//                continue;
//            }
//        }
//    }
//
//
//
//
//
//}
