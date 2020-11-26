package lockImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
* 描述：
* 读写锁实现缓存
*
* ReentrantReaWriteLock 只支持锁降级，不支持锁升级 并且只有写锁可以创建条件变量
* 读写锁允许多个线程同时读共享变量，当一个线程在写共享变量时，不允许其他线程执行读写操作
* */
public class ReadWriteLockDemo<K,V> {
    private final Map<K,V> m=new HashMap<>();

    private final ReadWriteLock rwl=new ReentrantReadWriteLock();

    /*读锁*/
    private final Lock readLock=rwl.readLock();

    /*写锁*/
    private final Lock writeLock=rwl.writeLock();

    /*写*/
    V put(K key,V value){
        writeLock.lock();  //tryLock()
        try {
            return m.put(key,value);
        }finally {
            writeLock.unlock();
        }
    }
    /*读*/
    V get(K key){
        readLock.tryLock();  //tryLock()
        try{
            return m.get(key);
        }finally {
            readLock.unlock();
        }
    }

    /*fix*/
    V get2(K key){
        V v=null;
        readLock.lock();
        try {
            v=m.get(key);
        }finally {
            readLock.unlock();
        }

        if (v!=null)
            return v;
        //缓存不存在，查询其他地方
        writeLock.lock();
        try {
            //TODO 查询
            //再次验证
            v=m.get(key);
            if (v==null)
                m.put(key,v);       //
        }finally {
            writeLock.unlock();
        }
        return v;
    }
}
