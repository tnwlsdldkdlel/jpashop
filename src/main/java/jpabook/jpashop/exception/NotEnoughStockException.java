package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {

	public NotEnoughStockException(String string) {
		super(string);
	}

}
