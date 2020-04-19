package Logic.LogicCore.NetService;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import GlobalTools.DataBean.Action.Action;
import GlobalTools.DataBean.Screen;
import Logic.LogicCore.RelationAndStatusManager;
import Logic.Tools.*;

/**
 * 服务器类
 */
public class server {
    private ExecutorService service = Executors.newFixedThreadPool(getProperties.linkCount);

    public server() {
        start();
    }

    public void start() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(getProperties.port);
            while (true) {
                socket = serverSocket.accept();
                service.submit(new ServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ServerHandler implements Runnable {

        private Socket socket;

        public ServerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            PrintWriter writer = null;

            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);

                String threadName = Thread.currentThread().getName();
                String requestName = socket.getInetAddress().getHostName();
                String readerInfo = null;
                while (true) {
                    readerInfo = reader.readLine();
                    if (readerInfo == null) {
                        break;
                    }
                    System.out.println("接收到客户端消息：" + readerInfo + ",处理线程：" + threadName);
                    writer.println(requestName + " 你的请求已处理，处理线程：" + threadName);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (writer != null) {
                        writer.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 数据类操作
     */
    public String getAllAction(){
        List<Action> actionLinkedList= RelationAndStatusManager.getInstance().getRelationOp().getAllAction();
        Action[] actions=new Action[actionLinkedList.size()];
        actions= actionLinkedList.toArray(actions);
        String s=new Gson().toJson(actions);
        return s;
    }

    public String getActionById(int id){
        Action action=RelationAndStatusManager.getInstance().getRelationOp().getActionById(id);
        return new Gson().toJson(action);
    }

    public String getAllScreen(){
        List<Screen> screenLinkedList= RelationAndStatusManager.getInstance().getStatusOp().getAllStatus();
        Screen[] screens=new Screen[screenLinkedList.size()];
        screens=screenLinkedList.toArray(screens);
        String s=new Gson().toJson(screens);
        return s;
    }

    public String getScreenBuId(int id){
        Screen screen=RelationAndStatusManager.getInstance().getStatusOp().getStatusById(id);
        return new Gson().toJson(screen);
    }





}
