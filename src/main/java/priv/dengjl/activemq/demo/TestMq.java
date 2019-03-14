package priv.dengjl.activemq.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestMq {
	public static void main(String[] args) {
		ExecutorService executors = Executors.newCachedThreadPool();
		Producter producter = new Producter();
		producter.init();
		for (int i = 0; i < 5; i++) {
			executors.execute(new ProductorMq(producter));
		}
	}
}