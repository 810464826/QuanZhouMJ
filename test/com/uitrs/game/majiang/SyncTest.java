package com.uitrs.game.majiang;

import java.util.ArrayList;
import java.util.List;

public class SyncTest {

	private List<String> hello;
	private List<String> helloSync;

	public String saySize(String who) {
		return who + " size: " + hello.size();
	}

	public String saySizeSync(String who) {
		return who + " size: " + helloSync.size();
	}

	public SyncTest() {
		super();
		this.hello = new ArrayList<String>();
		this.helloSync = new ArrayList<String>();
	}

	public synchronized void addHelloSync(String h) {
		this.helloSync.add(h);
		System.err.println(this.saySizeSync("this helloSync " + h));
	}

	public void addHello(String h) {
		this.hello.add(h);
		System.err.println(this.saySize("this hello " + h));
	}

	public void removeHello(int index) {
		this.hello.remove(index);
	}

	public int getSize() {
		return this.hello.size();
	}

	public int getSizeSync() {
		return this.helloSync.size();
	}
}
