package lotto;

import lotto.domain.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationProfitRateTest {
    @DisplayName("당첨이 없으면 수익률은 0%다.")
    @Test
    void zeroProfitRate() {
        Map<Rank, Long> rankCounts = new EnumMap<>(Rank.class);

        double profitRate = Application.calculateProfitRate(rankCounts, 8_000);

        assertThat(profitRate).isEqualTo(0.0);
    }

    @DisplayName("5등 한 장 당첨 시 수익률은 500.0%다.")
    @Test
    void fifthPrizeProfitRate() {
        Map<Rank, Long> rankCounts = singleRankCount(Rank.FIFTH, 1);

        double profitRate = Application.calculateProfitRate(rankCounts, 1_000);

        assertThat(profitRate).isEqualTo(500.0);
    }

    @DisplayName("3등 한 장 당첨 시 수익률은 30,000.0%다.")
    @Test
    void thirdPrizeProfitRate() {
        Map<Rank, Long> rankCounts = singleRankCount(Rank.THIRD, 1);

        double profitRate = Application.calculateProfitRate(rankCounts, 5_000);

        assertThat(profitRate).isEqualTo(30_000.0);
    }

    @DisplayName("1등 한 장 당첨 시 수익률은 20,000,000.0%다.")
    @Test
    void firstPrizeProfitRate() {
        Map<Rank, Long> rankCounts = singleRankCount(Rank.FIRST, 1);

        double profitRate = Application.calculateProfitRate(rankCounts, 10_000);

        assertThat(profitRate).isEqualTo(20_000_000.0);
    }

    private Map<Rank, Long> singleRankCount(Rank rank, long count) {
        Map<Rank, Long> counts = new EnumMap<>(Rank.class);
        counts.put(rank, count);
        return counts;
    }
}
