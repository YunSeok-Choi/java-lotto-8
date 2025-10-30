package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LottoTicketsTest {
    @DisplayName("로또 티켓의 개수를 정확히 반환한다.")
    @Test
    void 티켓_개수_확인() {
        List<Lotto> tickets = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                new Lotto(List.of(7, 8, 9, 10, 11, 12)),
                new Lotto(List.of(13, 14, 15, 16, 17, 18))
        );
        LottoTickets lottoTickets = new LottoTickets(tickets);
        assertThat(lottoTickets.getCount()).isEqualTo(3);
    }

    @DisplayName("당첨 번호와 비교하여 등수별 당첨 횟수를 계산한다.")
    @Test
    void 등수별_당첨_횟수_계산() {
        List<Lotto> tickets = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),      // 1등
                new Lotto(List.of(1, 2, 3, 4, 5, 7)),      // 2등
                new Lotto(List.of(1, 2, 3, 4, 5, 8)),      // 3등
                new Lotto(List.of(1, 2, 3, 4, 9, 10)),     // 4등
                new Lotto(List.of(1, 2, 3, 11, 12, 13))    // 5등
        );
        LottoTickets lottoTickets = new LottoTickets(tickets);
        WinningNumbers winningNumbers = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);

        Map<Rank, Long> ranks = lottoTickets.calculateRanks(winningNumbers);

        assertThat(ranks.get(Rank.FIRST)).isEqualTo(1);
        assertThat(ranks.get(Rank.SECOND)).isEqualTo(1);
        assertThat(ranks.get(Rank.THIRD)).isEqualTo(1);
        assertThat(ranks.get(Rank.FOURTH)).isEqualTo(1);
        assertThat(ranks.get(Rank.FIFTH)).isEqualTo(1);
    }
}
