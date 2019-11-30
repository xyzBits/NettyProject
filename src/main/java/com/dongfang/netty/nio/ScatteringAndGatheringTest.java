package com.dongfang.netty.nio;

import org.junit.jupiter.api.Test;

/**
 * Scattering 将数据写入到buffer时，可以采用buffer数组，依次写入，分散
 * Gathering  从buffer中读取数据时，也可以采用buffer数组，按照顺序依次读取
 */
public class ScatteringAndGatheringTest {
    @Test
    public void testScatter() {
        // 使用ServerSocketChannel 和 SocketChannel
    }
}
