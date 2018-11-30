package com.hellofresh.challenge;
import com.thoughtworks.gauge.Gauge;

public class GaugeLog {
	public static void Add(String message) {
		Gauge.writeMessage(message);
		System.out.println(message);
	}
}
