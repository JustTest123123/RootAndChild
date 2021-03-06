package thrift_demo.server;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/26 下午4:13
 */

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thrift.demo.UserServiceImpl;
import thrift_demo.UserService;
public class Server {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private void startServer() {
        UserService.Processor processor = new UserService.Processor<UserService.Iface>(new UserServiceImpl());
        try {
            TServerTransport transport = new TServerSocket(2345);
            TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(transport);
            tArgs.processor(processor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            tArgs.transportFactory(new TTransportFactory());
            tArgs.minWorkerThreads(10);
            tArgs.maxWorkerThreads(20);
            TServer server = new TThreadPoolServer(tArgs);
            server.serve();
        } catch (Exception e) {
            logger.error("thrift服务启动失败", e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }
}
