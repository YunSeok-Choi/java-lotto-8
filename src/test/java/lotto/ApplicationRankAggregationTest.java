package lotto;

import lotto.domain.Lotto;
import lotto.domain.LottoTickets;
import lotto.domain.Rank;
import lotto.domain.WinningNumbers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationRankAggregationTest {
    private static final WinningNumbers WINNING_NUMBERS = new WinningNumbers(
            List.of(1, 2, 3, 4, 5, 6),
            7
    );

    @DisplayName("로또 결과를 등수별로 누적한다")
    @ParameterizedTest
    @MethodSource("rankCases")
    void aggregateRanks(Rank expectedRank, List<Integer> numbers) {
        LottoTickets lottoTickets = new LottoTickets(List.of(new Lotto(numbers)));

        Map<Rank, Long> rankCounts = Application.calculateRankCounts(lottoTickets, WINNING_NUMBERS);

        assertThat(rankCounts.get(expectedRank)).isEqualTo(1L);
        assertThat(rankCounts.entrySet()).allSatisfy(entry -> {
            if (entry.getKey() != expectedRank) {
                assertThat(entry.getValue()).isZero();
            }
        });
    }

    private static Stream<Arguments> rankCases() {
        return Stream.of(
                Arguments.of(Rank.FIRST, List.of(1, 2, 3, 4, 5, 6)),
                Arguments.of(Rank.SECOND, List.of(1, 2, 3, 4, 5, 7)),
                Arguments.of(Rank.THIRD, List.of(1, 2, 3, 4, 5, 8)),
                Arguments.of(Rank.FOURTH, List.of(1, 2, 3, 4, 8, 9)),
                Arguments.of(Rank.FIFTH, List.of(1, 2, 3, 8, 9, 10))
        );
    }
}
