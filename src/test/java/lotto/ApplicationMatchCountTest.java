package lotto;

import lotto.domain.Lotto;
import lotto.domain.LottoTickets;
import lotto.domain.WinningNumbers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationMatchCountTest {
    @DisplayName("구매한 로또와 당첨 번호의 일치 개수를 계산한다.")
    @Test
    void matchCounts() {
        LottoTickets lottoTickets = new LottoTickets(List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                new Lotto(List.of(1, 2, 3, 4, 5, 7)),
                new Lotto(List.of(1, 2, 3, 8, 9, 10))
        ));
        WinningNumbers winningNumbers = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);

        List<Integer> matchCounts = Application.calculateMatchCounts(lottoTickets, winningNumbers);

        assertThat(matchCounts).containsExactly(6, 5, 3);
    }
}
