//package com.inori.executor.tools;
//
//import sun.tools.attach.BsdAttachProvider;
//import sun.tools.attach.HotSpotVirtualMachine;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.lang.management.ManagementFactory;
//import java.lang.management.RuntimeMXBean;
//import java.util.List;
//
///**
// * HeapTools
// *
// * @author inori
// * @date 2021/2/2
// */
//public class HeapTools {
//
//    // the current application process id.
//    private static final String pid;
//
//    static {
//        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
//        pid = runtime.getName().split("@")[0];
//    }
//
////    public static void main(String[] args) throws Exception {
////        Map<Integer, byte[]> map = new HashMap<>();
////        for (int i = 0; i < 100; i++) {
////            map.put(i, new byte[1024*1024]);
////        }
////        printObjectMap(false);
////        System.out.println(map);
////    }
//
//    public static List<String> objectMap(boolean live) {
//        return null;
//    }
//
//    public static void printObjectMap(boolean live) throws Exception {
//        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
//        final String pid = runtime.getName().split("@")[0];
//        final HotSpotVirtualMachine machine = (HotSpotVirtualMachine) new BsdAttachProvider().attachVirtualMachine(pid);
//        try (InputStream is = machine.heapHisto(live ? "-live" : "-all"); BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
//            String line;
//            int i = 0;
//            while (null != (line = reader.readLine())) {
//                if (++i > 3) {
//                    final String[] split = line.split(" +");
//                    String src = split[split.length - 1];
//                    String dest = ClassNameTransfer.transfer(src);
//                    if (!src.equals(dest)) line = line.replace(src, dest);
//                    src = split[split.length - 2];
//                    dest = ClassNameTransfer.bytesTransfer(src);
//                    if (!src.equals(dest)) line = line.replace(src, dest);
//                }
//                System.out.println(line);
//            }
//        } finally {
//            machine.detach();
//        }
//    }
//}
