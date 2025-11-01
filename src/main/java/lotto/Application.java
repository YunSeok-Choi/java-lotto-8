package lotto;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.domain.Lotto;

import java.util.ArrayList;
import java.util.List;

public class Application {
    static final int LOTTO_PRICE = 1_000;
    static final int LOTTO_MIN_NUMBER = 1;
    static final int LOTTO_MAX_NUMBER = 45;
    static final int LOTTO_NUMBER_COUNT = 6;

    public static void main(String[] args) {
        // TODO: 프로그램 구현
    }

    static int calculateTicketCount(int purchaseAmount) {
        return purchaseAmount / LOTTO_PRICE;
    }

    static Lotto createLotto() {
        List<Integer> numbers = new ArrayList<>(Randoms.pickUniqueNumbersInRange(
                LOTTO_MIN_NUMBER,
                LOTTO_MAX_NUMBER,
                LOTTO_NUMBER_COUNT
        ));
        numbers.sort(Integer::compareTo);
        return new Lotto(numbers);
    }
}
