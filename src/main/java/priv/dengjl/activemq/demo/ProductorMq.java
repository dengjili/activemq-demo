package priv.dengjl.activemq.demo;

public class ProductorMq implements Runnable {
	Producter producter;

	public ProductorMq(Producter producter) {
		this.producter = producter;
	}

	@Override
	public void run() {
		while (true) {
			try {
				producter.sendMessage("test-MQ");
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
