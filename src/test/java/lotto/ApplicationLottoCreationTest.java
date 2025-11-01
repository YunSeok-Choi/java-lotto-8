package lotto;

import lotto.domain.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationLottoCreationTest {
    @DisplayName("무작위로 생성된 로또 번호는 6개이며 오름차순으로 정렬된다.")
    @Test
    void lottoNumbersAreSorted() {
        Lotto lotto = Application.createLotto();
        List<Integer> numbers = lotto.getNumbers();

        assertThat(numbers).hasSize(Application.LOTTO_NUMBER_COUNT);
        assertThat(isSorted(numbers)).isTrue();
    }

    @DisplayName("로또 번호는 1부터 45 범위의 중복되지 않은 값으로 구성된다.")
    @Test
    void lottoNumbersWithinRangeAndUnique() {
        Lotto lotto = Application.createLotto();
        List<Integer> numbers = lotto.getNumbers();

        assertThat(numbers).allMatch(number ->
                number >= Application.LOTTO_MIN_NUMBER && number <= Application.LOTTO_MAX_NUMBER);

        Set<Integer> unique = new HashSet<>(numbers);
        assertThat(unique).hasSize(Application.LOTTO_NUMBER_COUNT);
    }

    private boolean isSorted(List<Integer> numbers) {
        for (int index = 1; index < numbers.size(); index++) {
            if (numbers.get(index - 1) > numbers.get(index)) {
                return false;
            }
        }
        return true;
    }
}
