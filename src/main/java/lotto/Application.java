package lotto;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.domain.Lotto;
import lotto.domain.LottoTickets;
import lotto.domain.Rank;
import lotto.domain.WinningNumbers;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

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

    static List<Integer> calculateMatchCounts(LottoTickets lottoTickets, WinningNumbers winningNumbers) {
        return lottoTickets.getTickets().stream()
                .map(winningNumbers::countMatch)
                .toList();
    }

    static Map<Rank, Long> calculateRankCounts(LottoTickets lottoTickets, WinningNumbers winningNumbers) {
        Map<Rank, Long> counts = lottoTickets.calculateRanks(winningNumbers);
        Map<Rank, Long> aligned = new EnumMap<>(Rank.class);
        for (Rank rank : Rank.values()) {
            if (rank == Rank.NONE) {
                continue;
            }
            aligned.put(rank, counts.getOrDefault(rank, 0L));
        }
        return aligned;
    }

    static double calculateProfitRate(Map<Rank, Long> rankCounts, int purchaseAmount) {
        long totalPrize = 0;
        for (Rank rank : Rank.values()) {
            if (rank == Rank.NONE) {
                continue;
            }
            long count = rankCounts.getOrDefault(rank, 0L);
            totalPrize += (long) rank.getPrize() * count;
        }
        double rate = (totalPrize * 100.0) / purchaseAmount;
        return Math.round(rate * 10.0) / 10.0;
    }
}
