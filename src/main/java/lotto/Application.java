package lotto;

public class Application {
    static final int LOTTO_PRICE = 1_000;

    public static void main(String[] args) {
        // TODO: 프로그램 구현
    }

    static int calculateTicketCount(int purchaseAmount) {
        return purchaseAmount / LOTTO_PRICE;
    }
}
