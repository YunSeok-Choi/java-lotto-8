package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WinningNumbersTest {
    @DisplayName("보너스 번호가 1부터 45 사이의 범위를 벗어나면 예외가 발생한다.")
    @Test
    void 보너스_번호_범위_검증() {
        assertThatThrownBy(() -> new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 46))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    @Test
    void 보너스_번호_중복_검증() {
        assertThatThrownBy(() -> new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 6))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또와 당첨 번호를 비교하여 등수를 정확히 판별한다.")
    @Test
    void 등수_판별() {
        WinningNumbers winningNumbers = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);

        Lotto lotto1 = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        assertThat(winningNumbers.matchRank(lotto1)).isEqualTo(Rank.FIRST);

        Lotto lotto2 = new Lotto(List.of(1, 2, 3, 4, 5, 7));
        assertThat(winningNumbers.matchRank(lotto2)).isEqualTo(Rank.SECOND);

        Lotto lotto3 = new Lotto(List.of(1, 2, 3, 4, 5, 8));
        assertThat(winningNumbers.matchRank(lotto3)).isEqualTo(Rank.THIRD);

        Lotto lotto4 = new Lotto(List.of(1, 2, 3, 4, 8, 9));
        assertThat(winningNumbers.matchRank(lotto4)).isEqualTo(Rank.FOURTH);

        Lotto lotto5 = new Lotto(List.of(1, 2, 3, 8, 9, 10));
        assertThat(winningNumbers.matchRank(lotto5)).isEqualTo(Rank.FIFTH);

        Lotto lotto6 = new Lotto(List.of(1, 2, 8, 9, 10, 11));
        assertThat(winningNumbers.matchRank(lotto6)).isEqualTo(Rank.NONE);
    }
}
