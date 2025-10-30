package lotto;

import java.util.List;

public class WinningNumbers {
    private static final int LOTTO_MIN_NUMBER = 1;
    private static final int LOTTO_MAX_NUMBER = 45;

    private final Lotto winningLotto;
    private final int bonusNumber;

    public WinningNumbers(List<Integer> winningNumbers, int bonusNumber) {
        this.winningLotto = new Lotto(winningNumbers);
        validateBonusNumber(bonusNumber, winningNumbers);
        this.bonusNumber = bonusNumber;
    }

    private void validateBonusNumber(int bonusNumber, List<Integer> winningNumbers) {
        validateBonusRange(bonusNumber);
        validateBonusDuplicate(bonusNumber, winningNumbers);
    }

    private void validateBonusRange(int bonusNumber) {
        if (bonusNumber < LOTTO_MIN_NUMBER || bonusNumber > LOTTO_MAX_NUMBER) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    private void validateBonusDuplicate(int bonusNumber, List<Integer> winningNumbers) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    public Rank matchRank(Lotto lotto) {
        int matchCount = lotto.countMatch(winningLotto.getNumbers());
        boolean hasBonus = lotto.contains(bonusNumber);
        return Rank.of(matchCount, hasBonus);
    }
}
