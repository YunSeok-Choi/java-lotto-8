package lotto;

import lotto.domain.LottoTickets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationGenerateTicketsTest {
    @DisplayName("구입 금액에 해당하는 로또 티켓을 생성한다.")
    @Test
    void createTicketsFromPurchaseAmount() {
        LottoTickets lottoTickets = Application.generateTickets(5_000);

        assertThat(lottoTickets.getCount()).isEqualTo(5);
        assertThat(Application.formatTicketNumbers(lottoTickets)).hasSize(5);
    }
}
