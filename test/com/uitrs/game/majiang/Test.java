package com.uitrs.game.majiang;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.uitrs.game.majiang.common.msg.ReceiveMsg;
import com.uitrs.game.majiang.common.msg.sub.OptCheckMsg;

public class Test {

	// private final static Object lock = new Object();

	public static void main(String[] args) {
		String[] a = new String[]{"1","2"};
		Arrays.fill(a, "3");
		System.err.println(Arrays.toString(a));
//		Map<String, Boolean> optNumber = new ConcurrentHashMap<String, Boolean>();
//		optNumber.put("1111", false);
//		optNumber.put("2222", false);
//		
//		System.err.println(optNumber);
//		
//		System.err.println(optNumber.containsValue(true));
//		optNumber.clear();
//		System.err.println(optNumber);
//		for (int i = 1; i <= 18; i++) {
//			double bei =  (16 / 4 / (double)i);
//			System.err.println("bei:" + bei);
//			int useQuanNum = bei >= 1 ? 1 : (bei >= 0.5 ? 2 : (bei >= ((double) 1 / (double) 3) ? 3 : 4));
//			System.err.println(i + ":" + useQuanNum);
//		}

		// List<String> al = new ArrayList<String>();
		// al.add("a");
		// al.add("b");
		// al.add("b");
		// al.add("c");
		// al.add("d");
		//
		// System.err.println(al.size());
		// al.remove("b");
		// System.err.println(al.size());
		// jsonTest2();
		// int[] opt = new int[]{2,3,4};
		//// boolean result = (0<= Arrays.binarySearch(opt, 1));
		// System.err.println(0<=Arrays.binarySearch(opt, 0));
		// System.err.println(0<=Arrays.binarySearch(opt, 1));
		// System.err.println(0<=Arrays.binarySearch(opt, 2));
		// System.err.println(0<=Arrays.binarySearch(opt, 3));
		// System.err.println(0<=Arrays.binarySearch(opt, 4));
		// System.err.println(0<=Arrays.binarySearch(opt, 5));
		// System.err.println(0<=Arrays.binarySearch(opt, 6));
		// System.err.println(result);
		// fan();
		// jsonTest();

		// syncTest();
	}

	public static void jsonTest2() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t", 13);
		map.put("c", "t3");
		map.put("m", new String[] { "t1", "t3", "t2" });
		String json = JsonUtils.toJson(map);
		System.err.println("json:: " + json);
		System.err.println("json:: " + JsonUtils.parse(json, OptCheckMsg.class));
	}

	public static void fan() {
		int len = 5;
		int yu = len % 3;
		System.err.println(yu);
		int bei = ((len - 3) / 3);
		System.err.println(bei);
		int fan = (len - 3) / 3 * 3 + len % 3 + 1;
		System.err.println(fan * 3);
	}

	public static void jsonTest() {
		String message = "{\"p\":\"sdsdf\",\"t\":\"0\"}";
		ReceiveMsg action = JsonUtils.parse(message, ReceiveMsg.class);
		System.err.println("action--" + action);

		ReceiveMsg receiveMsg = new ReceiveMsg();
		receiveMsg.setM(action.toJson());
		receiveMsg.setP(815001);
		receiveMsg.setT(1);

		// String msgJson = JsonUtils.toJson(receiveMsg);
		ReceiveMsg receiveMsg2 = JsonUtils.parse(receiveMsg.toJson(), ReceiveMsg.class);
		System.err.println("msgJson--" + receiveMsg2);
	}

	public static void syncTest() {
		SyncTest josh = new SyncTest();
		SyncTest lucio = new SyncTest();
		final Map<String, SyncTest> testMap = new HashMap<String, SyncTest>();
		testMap.put("josh", josh);
		testMap.put("lucio", lucio);
		final Set<Integer> sizeMap = new HashSet<Integer>();
		final Set<Integer> sizeMapSync = new HashSet<Integer>();
		for (int i = 1; i <= 1000; i++) {
			final int num = i;
			// Thread sync1 = new Thread(new Runnable() {
			//
			// @Override
			// public void run() {
			// SyncTest josh = testMap.get("josh");
			// SyncTest lucio = testMap.get("lucio");
			// synchronized (josh) {
			// lucio.addHello("h" + num);
			// sizeMap.add(lucio.getSize());
			//
			// // josh.addHello("h" + num);
			// // sizeMap.put(josh.getSize(), num);
			// // System.err.println(josh.saySize("syncjosh1 " + num +
			// // " "));
			// }
			// System.err.println(lucio.saySize("synclucio1 " + num + " "));
			// // lucio.addHello("l" + num);
			// // System.err.println(lucio.saySize("synclucio1" + num + "
			// // "));
			// }
			// }, "sync" + i);
			// sync1.start();

			Thread sync2 = new Thread(new Runnable() {

				@Override
				public void run() {
					SyncTest lucio = testMap.get("lucio");
					SyncTest josh = testMap.get("josh");
					synchronized (josh) {
						lucio.addHello("h" + num);
						sizeMap.add(lucio.getSize());
						// System.err.println(lucio.saySize("synclucio2 " + num
						// + "
						// "));
					}
					lucio.addHelloSync(num + " t: " + Thread.currentThread().getName());
					sizeMapSync.add(lucio.getSizeSync());
					System.err.println("lucio.getSizeSync(): " + "t: " + Thread.currentThread().getName() + " "
							+ lucio.getSizeSync());
					// System.err.println("sizeMapSync.size(): "+"t" + num+"
					// " + sizeMapSync.size());
					// josh.addHello("h" + num);
					// sizeMap.put(josh.getSize(), num);
					// System.err.println(josh.saySize("syncjosh2 " + num +
					// "
					// "));
					// }
					// josh.addHello("l" + num);
					// System.err.println(josh.saySize("syncjosh2" + num + "
					// "));

				}
			}, "sync" + i);

			sync2.start();
		}
		try {
			Thread.sleep(1000);
			System.err.println("sizeMap.size(): " + sizeMap.size());
			System.err.println("sizeMapSync.size(): " + sizeMapSync.size());
			System.err.println("lucio.size(): " + testMap.get("lucio").getSize());
			System.err.println("lucioSync.size(): " + testMap.get("lucio").getSizeSync());

			// Iterator<Integer> i = sizeMap.iterator();
			// while (i.hasNext()) {
			// System.err.println(i.next());
			// }

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
