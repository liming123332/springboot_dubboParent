package com.dubbo.stu_service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class RedisLockUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    private RedisConnection redisConnection;

    private String lockSha;
    private String unlockSha;

    private ThreadLocal<String> threadLocal=new ThreadLocal<>();

    /**
     * 添加分布式锁的Lua脚本
     */
    private String lock = "local key = KEYS[1]\n" +
            "local value = ARGV[1]\n" +
            "local time = ARGV[2]\n" +
            "\n" +
            "local result = redis.call('setnx', key, value)\n" +
            "if result == 1 then\n" +
            "  --当前获得了分布式锁\n" +
            "  --设置锁的过期时间\n" +
            "  redis.call('expire', key, time)\t\n" +
            "  return true\t\n" +
            "end\n" +
            "\n" +
            "--没有获得分布式锁\n" +
            "return false";

    //解锁的lua脚本
    private String unlock = "--要删除的是什么锁\n" +
            "local key = KEYS[1]\n" +
            "local uuid = ARGV[1]\n" +
            "\n" +
            "--获取锁中的uuid\n" +
            "local lockUUID = redis.call('get', key)\n" +
            "\n" +
            "--判断是不是自己上的锁\n" +
            "if uuid == lockUUID then\n" +
            "  --是自己上的锁，删除\n" +
            "  redis.call('del', key)\n" +
            "  return true\n" +
            "end\n" +
            "\n" +
            "--不是自己上的锁\n" +
            "return false";

    @PostConstruct
    public void init(){
        redisConnection=redisTemplate.getRequiredConnectionFactory().getConnection();
        lockSha=redisConnection.scriptLoad(lock.getBytes());
        unlockSha=redisConnection.scriptLoad(unlock.getBytes());
    }

    public Boolean lock(String key,int timeout){
        String uuid= UUID.randomUUID().toString();
        threadLocal.set(uuid);
        Boolean flag = redisConnection.evalSha(lockSha, ReturnType.BOOLEAN, 1, key.getBytes(), uuid.getBytes(), (timeout + "").getBytes());
        return flag;
    }

    public Boolean unlock(String key){
        String uuid=threadLocal.get();
        Boolean flag = redisConnection.evalSha(unlockSha, ReturnType.BOOLEAN, 1, key.getBytes(), uuid.getBytes());
        return flag;
    }
}
