package lotto;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.domain.Lotto;
import lotto.domain.LottoTickets;

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

    static LottoTickets generateTickets(int purchaseAmount) {
        int ticketCount = calculateTicketCount(purchaseAmount);
        List<Lotto> tickets = new ArrayList<>();
        for (int index = 0; index < ticketCount; index++) {
            tickets.add(createLotto());
        }
        return new LottoTickets(tickets);
    }

    static List<String> formatTicketNumbers(LottoTickets lottoTickets) {
        return lottoTickets.getTickets().stream()
                .map(Lotto::getNumbers)
                .map(numbers -> numbers.toString())
                .toList();
    }
}
