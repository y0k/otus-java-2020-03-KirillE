package ru.otus.hwGC;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/*
О формате логов
http://openjdk.java.net/jeps/158

-Xms512m
-Xmx512m
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
-XX:+UseG1GC
-XX:MaxGCPauseMillis=
*/

public class GcDemo {
  public static void main(String... args) throws Exception {
    System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
    switchOnMonitoring();


    int loopCounter = 1_000_000;
    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

    ObjectName name = new ObjectName("ru.otus:type=Benchmark");

    Benchmark mbean = new Benchmark(loopCounter);
    mbs.registerMBean(mbean, name);
    mbean.run();

  }

  private static void switchOnMonitoring() {
    List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
    for (GarbageCollectorMXBean gcbean : gcbeans) {
      System.out.println("GC name:" + gcbean.getName());
      NotificationEmitter emitter = (NotificationEmitter) gcbean;
      NotificationListener listener = (notification, handback) -> {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
          GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
          String gcName = info.getGcName();
          String gcAction = info.getGcAction();
          String gcCause = info.getGcCause();

          long startTime = info.getGcInfo().getStartTime();
          long duration = info.getGcInfo().getDuration();

          System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
        }
      };
      emitter.addNotificationListener(listener, null, null);
    }
  }

}
