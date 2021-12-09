package cn.microboat.mybatisplusdemo.utils;
/**
 * description  雪花算法生成的UUID
 * @author zhouwei
 */

public class UUIDGenerator {
    // 工作机器 id
    private long workerId;
    // 数据中心 id
    private long datacenterId;
    // 序列号
    private long sequence = 0;

    // 固定的时间戳，用于计算时间戳差值部分
    private final long twepoch = 1611072299L;

    // 机器 id 长度为 5
    private final long workerIdBits = 5L;
    // 机房 id 长度为 5
    private final long datacenterIdBits = 5L;
    // 序列号长度为 12
    private final long sequenceBits = 12L;

    // 机器 id 需要左移的长度为 12
    private long workerIdShift = sequenceBits;
    // 机房 id 需要左移的长度为 17
    private long datacenterIdShift = sequenceBits + workerIdBits;
    // 时间戳 需要左移的长度为 22
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    // 上次时间戳，初始值为 -1
    private long lastTimestamp = -1L;

    // 得到初始UUID，0000000000000000000000000000000000000000000000000000111111111111
    // 序列号最大值 4095，0-4095，支持4096个数
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    //最大值 -1 左移 5，得结果a，-1 异或 a：利用位运算计算出5位能表示的最大正整数是多少。
    private long maxWorkerId = -1L ^ (-1L << workerIdBits); //31
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits); // 31

    // 可自定义的 机器id 和 机房 id
    public UUIDGenerator(long workerId, long datacenterId){
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        //时间回拨，抛出异常处理
        //通常来说如果时间回拨时间短，比如配置5ms以内，那么可以直接等待一定的时间，让机器的时间追上来。
        //还可以利用扩展位来直接赋值
        if (timestamp < lastTimestamp) {
            System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        // 并发访问的控制
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    /**
     * 当前ms已经满了
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    // 获取当前时间
    private long timeGen(){
        return System.currentTimeMillis();
    }

}

