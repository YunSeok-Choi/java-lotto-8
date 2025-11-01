package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTicketCountTest {
    @DisplayName("구입 금액을 기준으로 발행할 로또 장 수를 계산한다.")
    @Test
    void purchaseAmountToTicketCount() {
        int ticketCount = Application.calculateTicketCount(8_000);
        assertThat(ticketCount).isEqualTo(8);
    }

    @DisplayName("구입 금액이 로또 한 장 가격과 같으면 1장을 발행한다.")
    @Test
    void minimumTicketCount() {
        int ticketCount = Application.calculateTicketCount(Application.LOTTO_PRICE);
        assertThat(ticketCount).isEqualTo(1);
    }
}
