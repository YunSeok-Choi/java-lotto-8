package lotto.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LottoTickets {
    private final List<Lotto> tickets;

    public LottoTickets(List<Lotto> tickets) {
        this.tickets = List.copyOf(tickets);
    }

    public int getCount() {
        return tickets.size();
    }

    public List<Lotto> getTickets() {
        return List.copyOf(tickets);
    }

    public Map<Rank, Long> calculateRanks(WinningNumbers winningNumbers) {
        return tickets.stream()
                .map(winningNumbers::matchRank)
                .collect(Collectors.groupingBy(
                        rank -> rank,
                        Collectors.counting()
                ));
    }
}
