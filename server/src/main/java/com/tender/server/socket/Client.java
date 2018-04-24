package com.tender.server.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by boyu on 2018/4/24.
 */

public class Client {

    private static Socket socket;

    public void start() {
        try {
            Scanner scanner = new Scanner(System.in);
            setName(scanner);
            // 接收服务器端发送过来的信息的线程启动
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new ListenerServer());

            // 建立输出流，给服务端发信息
            PrintWriter pw = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            while(true) {
                pw.println(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println("读取服务器异常～");
        } finally {
            if(socket != null){
                try {
                    socket.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 循环读取服务端发送过来的信息并输出到客户端的控制台
    private class ListenerServer implements Runnable {

        @Override
        public void run() {
            try {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), "UTF-8"));
                String msgString;
                while((msgString = br.readLine()) != null) {
                    System.out.println(msgString);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setName(Scanner scanner) throws  Exception {
        String name;
        PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(), "UTF-8"),true);
        //创建输入流
        BufferedReader br = new BufferedReader(
                new InputStreamReader(socket.getInputStream(),"UTF-8"));
        while(true) {
            System.out.println("请创建您的昵称：");
            name = scanner.nextLine();
            if (name.trim().equals("")) {
                System.out.println("昵称不得为空");
            } else {
                pw.println(name);
                String pass = br.readLine();
                if (pass != null && (!pass.equals("OK"))) {
                    System.out.println("昵称已经被占用，请重新输入：");
                } else {
                    System.out.println("昵称“"+ name +"”已设置成功，可以开始聊天了");
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        socket = new Socket("localhost", 9999);
        Client client = new Client();
        client.start();
    }
}
