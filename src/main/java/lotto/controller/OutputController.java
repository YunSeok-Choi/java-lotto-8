package lotto.controller;

import lotto.domain.Lotto;
import lotto.domain.LottoTickets;
import lotto.domain.Rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutputController {
    private static final List<Rank> DISPLAY_ORDER = List.of(
            Rank.FIFTH,
            Rank.FOURTH,
            Rank.THIRD,
            Rank.SECOND,
            Rank.FIRST
    );

    public List<String> buildPurchaseOutput(LottoTickets lottoTickets) {
        List<String> lines = new ArrayList<>();
        lines.add(String.format("%d개를 구매했습니다.", lottoTickets.getCount()));
        lines.addAll(formatTicketNumbers(lottoTickets));
        return lines;
    }

    public List<String> buildStatisticsOutput(Map<Rank, Long> rankCounts, double profitRate) {
        List<String> lines = new ArrayList<>();
        lines.add("당첨 통계");
        lines.add("---");
        for (Rank rank : DISPLAY_ORDER) {
            long count = rankCounts.getOrDefault(rank, 0L);
            lines.add(formatRankLine(rank, count));
        }
        lines.add(String.format("총 수익률은 %.1f%%입니다.", profitRate));
        return lines;
    }

    private List<String> formatTicketNumbers(LottoTickets lottoTickets) {
        return lottoTickets.getTickets().stream()
                .map(Lotto::getNumbers)
                .map(List::toString)
                .toList();
    }

    private String formatRankLine(Rank rank, long count) {
        String prize = String.format("%,d", rank.getPrize());
        if (rank == Rank.SECOND) {
            return String.format("%d개 일치, 보너스 볼 일치 (%s원) - %d개", rank.getMatchCount(), prize, count);
        }
        return String.format("%d개 일치 (%s원) - %d개", rank.getMatchCount(), prize, count);
    }
}
