package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RankTest {
    @DisplayName("일치 개수와 보너스 일치 여부로 등수를 정확히 판별한다.")
    @ParameterizedTest
    @CsvSource({
            "6, false, FIRST",
            "5, true, SECOND",
            "5, false, THIRD",
            "4, false, FOURTH",
            "3, false, FIFTH",
            "2, false, NONE",
            "1, false, NONE",
            "0, false, NONE"
    })
    void 등수_판별(int matchCount, boolean hasBonus, Rank expectedRank) {
        Rank rank = Rank.of(matchCount, hasBonus);
        assertThat(rank).isEqualTo(expectedRank);
    }

    @DisplayName("1등의 상금은 2,000,000,000원이다.")
    @Test
    void 일등_상금_확인() {
        assertThat(Rank.FIRST.getPrize()).isEqualTo(2_000_000_000);
    }

    @DisplayName("2등의 상금은 30,000,000원이다.")
    @Test
    void 이등_상금_확인() {
        assertThat(Rank.SECOND.getPrize()).isEqualTo(30_000_000);
    }

    @DisplayName("3등의 상금은 1,500,000원이다.")
    @Test
    void 삼등_상금_확인() {
        assertThat(Rank.THIRD.getPrize()).isEqualTo(1_500_000);
    }

    @DisplayName("4등의 상금은 50,000원이다.")
    @Test
    void 사등_상금_확인() {
        assertThat(Rank.FOURTH.getPrize()).isEqualTo(50_000);
    }

    @DisplayName("5등의 상금은 5,000원이다.")
    @Test
    void 오등_상금_확인() {
        assertThat(Rank.FIFTH.getPrize()).isEqualTo(5_000);
    }
}
