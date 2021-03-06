package com.tender.server.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by boyu on 2018/4/24.
 */

public class Server {

    private ServerSocket serverSocket;

    /**
     * 创建线程池来管理客户端的连接线程
     * 避免系统资源过度浪费
     */
    private ExecutorService exec;

    // 存放客户端之间私聊的信息
    private Map<String,PrintWriter> storeInfo;

    public Server() {
        try {
            serverSocket = new ServerSocket(9999);
            storeInfo = new HashMap<String, PrintWriter>();
            exec = Executors.newCachedThreadPool();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 将客户端的信息以Map形式存入集合中
    private void putInfo(String key, PrintWriter value) {
        synchronized(this) {
            storeInfo.put(key, value);
        }
    }

    // 将给定的输出流从共享集合中删除
    private synchronized void remove(String key) {
        storeInfo.remove(key);
        System.out.println("当前在线人数为："+ storeInfo.size());
    }

    // 将给定的消息转发给所有客户端
    private synchronized void sendToAll(String message) {
        for(PrintWriter out: storeInfo.values()) {
            out.println(message);
        }
    }

    // 将给定的消息转发给私聊的客户端
    private synchronized void sendToSomeone(String name, String message) {
        PrintWriter pw = storeInfo.get(name); //将对应客户端的聊天信息取出作为私聊内容发送出去
        if(pw != null) pw.println(message);
    }

    private void start() {
        try {
            while (true) {
                System.out.println("等待客户端连接～");
                Socket socket = serverSocket.accept();
                InetAddress address = socket.getInetAddress();
                System.out.println("客户端：“" + address.getHostAddress() + "”连接成功！ ");
                 //启动一个线程，由线程来处理客户端的请求，这样可以再次监听下一个客户端的连接
                exec.execute(new ListenerClient(socket)); //通过线程池来分配线程
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ListenerClient implements Runnable {

        private Socket socket;
        private String name;

        public ListenerClient(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
                name = getName();
                //将客户昵称和其所说的内容存入共享集合HashMap中
                putInfo(name, pw);
                Thread.sleep(100);
                // 服务端通知所有客户端，某用户上线
                sendToAll("[系统通知] “" + name + "”已上线");

                //通过客户端的Socket获取输入流读取客户端发送来的信息
                BufferedReader bReader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), "UTF-8"));
                String msgString = null;
                while ((msgString = bReader.readLine()) != null) {
                    // 检验是否为私聊（格式：@昵称：内容）
                    if(msgString.startsWith("@")) {
                        int index = msgString.indexOf(":");
                        if(index >= 0) {
                            //获取昵称
                            String theName = msgString.substring(1, index);
                            String info = msgString.substring(index+1, msgString.length());
                            info =  name + "："+ info;
                            //将私聊信息发送出去
                            sendToSomeone(theName, info);
                            continue;
                        }
                    }
                    // 遍历所有输出流，将该客户端发送的信息转发给所有客户端
                    System.out.println(name+"："+ msgString);
                    sendToAll(name+"："+ msgString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                remove(name);
                // 通知所有客户端，某某客户已经下线
                sendToAll("[系统通知] "+ name + "已经下线了。");

                if(socket!=null) {
                    try {
                        socket.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private String getName() throws Exception {
            try {
                //服务端的输入流读取客户端发送来的昵称输出流
                BufferedReader bReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                //服务端将昵称验证结果通过自身的输出流发送给客户端
                PrintWriter ipw = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), "UTF-8"),true);
                //读取客户端发来的昵称
                while(true) {
                    String nameString = bReader.readLine();
                    if ((nameString.trim().length() == 0) || storeInfo.containsKey(nameString)) {
                        ipw.println("FAIL");
                    } else {
                        ipw.println("OK");
                        return nameString;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.start();
    }
}
