package com.dongfang.netty.nio.selector;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 *
 Selector
 1、java的nio，用非阻塞的io方式，可以用一个线程，处理多个客户端的连接，就会用到selector（选择器）

 2、Selector能够检测多个注册到通道上是否有事件发生

 注册到通道
 是否有事件发生
 多个channel以事件的方式可以注册到同一个selector

 如果有事件发生，便获取事件，然后针对 每个事件进行相应的处理，这样就可以只用一个单线程去管理多个通道，也就是管理多个连接和请求

 只有在通道真正有读写事件发生时，能会进行读写，就大大减少了系统开销，并且不必为每个连接都创建一个线程，不用去维护多个线程

 避免了多线程之间的上下文切换导致的开销



 特点再说明：
 1） Netty的io线程NioEventLoop聚合了Selector（选择器，也叫多路复用器），可以同时并发处理成百上千个客户端连接
 2）当线程从某客户端Socket通道读取数据时，若没有数据可用时，该线程会进行其他任务
 3）线程通道将非阻塞IO的空闲时间用在其他通道上执行IO操作，所以单独的线程可以管理多个输入和输出通道
 4）由于读写操作都是非阻塞的，这就可以充分提升IO线程的运行效率，避免由于频繁IO阻塞导致的线程挂起
 5）一个IO线程可以并发处理N个客户端连接和读写操作，这从根本上解决了传统同步阻塞IO一连接一线程模型，架构的性能，弹性伸缩能力和可靠性都得到了极大的提升。

 Selector
 SelectionKey
 ServerSocketChannel
 SocketChannel


 1、当客户端连接时，通过ServerSocketChannel得到SocketChannel
 selector开始监听
 2、将得到SocketChannel注册到Selector，方法是register(Select sel, int ops)，一个Selector上可以注册多个SocketChannel
 3、注册后会返回一个SelectionKey，会和该Selector通过集合关联
 4、Selector进行监听，select()方法，会返回当前有事件发生的通道的个数
 5、进一步得到各个SelectionKey（有事件发生的）
 6、再通过SelectionKey反向获取SocketChannel，方法channel
 7、可以通过得到的Channel完成业务处理

 */
public class NioServer {
    @Test
    public void testNioServer() throws IOException {
        // 创建ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 得到一个Selector对象
        Selector selector = Selector.open();

        //绑定一个端口6666，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 为什么不用这个serverSocketChannel.bind()

        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 把ServerSocketChannel 注册到Seletcor，关心的事件是为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环等待客户端连接
        while (true) {
            // 这是我们等待1秒，如果1秒后没有事件发生，返回
            if (selector.select(1_000) == 0) { // 没有事件发生
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }

            // 如果返回的 > 0，就获取到相关的SelectionKey集合
            // 1、如果返回的 > 0，表示已经获取到关注的事件
            // 2、selector.selectedKeys()返回的是关注事件的集合
            // 通过selectionKeys反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            // 遍历集合Set<SelectionKey>，使用迭代器遍历
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {
                // 获取到SelectionKey
                SelectionKey key = keyIterator.next();
                // 根据key对应的通道发生的事件做相应的处理
                if (key.isAcceptable()) { // 如果是OP_ACCEPT，有新的客户端连接我，马上就连接，不会阻塞
                    // 给该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    System.out.println("客户端连接成功，生成了一个SocketChannel" + socketChannel.hashCode());
                    // 必须设置通道为 非阻塞，才能向 Selector 注册
                    // java.nio.channels.IllegalBlockingModeException
                    socketChannel.configureBlocking(false);

                    // 将当前的SocketChannel注册到Selector，关注事件为OP_READ，同时给这个SocketChannel关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) { // 发生了OP_READ事件
                    // 通过key反向获取到对应的Channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 获取该Channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("客户端发送的数据是 " + new String(buffer.array()));
                }
                // 手动从集合中移除当前的SelectionKey，防止重复操作
                keyIterator.remove();

            }

        }
    }
}
