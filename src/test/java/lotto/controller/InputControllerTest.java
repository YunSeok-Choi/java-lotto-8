package lotto.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputControllerTest {
    private final InputController inputController = new InputController();

    @DisplayName("구입 금액을 정상적으로 파싱한다.")
    @Test
    void parsePurchaseAmount() {
        int purchaseAmount = inputController.parsePurchaseAmount("8000");
        assertThat(purchaseAmount).isEqualTo(8_000);
    }

    @DisplayName("구입 금액이 숫자가 아니면 예외가 발생한다.")
    @Test
    void parsePurchaseAmountWithNonNumericValue() {
        assertThatThrownBy(() -> inputController.parsePurchaseAmount("8천원"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구입 금액이 0이하이거나 1,000원 단위가 아니면 예외가 발생한다.")
    @Test
    void parsePurchaseAmountWithInvalidRange() {
        assertThatThrownBy(() -> inputController.parsePurchaseAmount("0"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> inputController.parsePurchaseAmount("-1000"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> inputController.parsePurchaseAmount("1500"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 번호를 정상적으로 파싱한다.")
    @Test
    void parseWinningNumbers() {
        List<Integer> winningNumbers = inputController.parseWinningNumbers("1,2,3,4,5,6");
        assertThat(winningNumbers).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @DisplayName("당첨 번호가 6개가 아니면 예외가 발생한다.")
    @Test
    void parseWinningNumbersWithInvalidCount() {
        assertThatThrownBy(() -> inputController.parseWinningNumbers("1,2,3,4,5"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> inputController.parseWinningNumbers("1,2,3,4,5,6,7"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void parseWinningNumbersWithDuplicate() {
        assertThatThrownBy(() -> inputController.parseWinningNumbers("1,2,3,4,5,5"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 번호에 범위를 벗어난 숫자가 있으면 예외가 발생한다.")
    @Test
    void parseWinningNumbersWithOutOfRangeNumber() {
        assertThatThrownBy(() -> inputController.parseWinningNumbers("0,1,2,3,4,5"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> inputController.parseWinningNumbers("1,2,3,4,5,46"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 번호에 숫자가 아닌 문자가 포함되면 예외가 발생한다.")
    @Test
    void parseWinningNumbersWithNonNumericValue() {
        assertThatThrownBy(() -> inputController.parseWinningNumbers("1,2,3,사,5,6"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보너스 번호를 정상적으로 파싱한다.")
    @Test
    void parseBonusNumber() {
        int bonusNumber = inputController.parseBonusNumber("7", List.of(1, 2, 3, 4, 5, 6));
        assertThat(bonusNumber).isEqualTo(7);
    }

    @DisplayName("보너스 번호가 숫자가 아니면 예외가 발생한다.")
    @Test
    void parseBonusNumberWithNonNumericValue() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        assertThatThrownBy(() -> inputController.parseBonusNumber("보너스", winningNumbers))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보너스 번호가 범위를 벗어나면 예외가 발생한다.")
    @Test
    void parseBonusNumberWithOutOfRangeNumber() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        assertThatThrownBy(() -> inputController.parseBonusNumber("0", winningNumbers))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> inputController.parseBonusNumber("46", winningNumbers))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    @Test
    void parseBonusNumberWithDuplicate() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        assertThatThrownBy(() -> inputController.parseBonusNumber("6", winningNumbers))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구입 금액 입력 실패 시 에러 메시지를 출력하고 재시도한다.")
    @Test
    void requestPurchaseAmountRetry() {
        InputController controller = new InputController();
        controller.setScriptedInputs(List.of("오천원", "8000"));

        int amount = controller.requestPurchaseAmount();

        assertThat(amount).isEqualTo(8_000);
        controller.clearScript();
    }
}
