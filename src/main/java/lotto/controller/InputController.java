package lotto.controller;

import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InputController {
    private static final String PURCHASE_PROMPT = "구입금액을 입력해 주세요.";
    private static final String WINNING_PROMPT = "당첨 번호를 입력해 주세요.";
    private static final String BONUS_PROMPT = "보너스 번호를 입력해 주세요.";
    private static final String NUMBER_DELIMITER = ",";
    private static final int LOTTO_MIN_NUMBER = 1;
    private static final int LOTTO_MAX_NUMBER = 45;
    private static final int PURCHASE_UNIT = 1_000;
    private static final int WINNING_NUMBER_COUNT = 6;

    private final List<String> scriptedInputs = new ArrayList<>();
    private int scriptIndex = 0;

    public int requestPurchaseAmount() {
        while (true) {
            try {
                String input = readLine(PURCHASE_PROMPT);
                return parsePurchaseAmount(input);
            } catch (IllegalArgumentException exception) {
                printError(exception);
            }
        }
    }

    public List<Integer> requestWinningNumbers() {
        while (true) {
            try {
                String input = readLine(WINNING_PROMPT);
                return parseWinningNumbers(input);
            } catch (IllegalArgumentException exception) {
                printError(exception);
            }
        }
    }

    public int requestBonusNumber(List<Integer> winningNumbers) {
        while (true) {
            try {
                String input = readLine(BONUS_PROMPT);
                return parseBonusNumber(input, winningNumbers);
            } catch (IllegalArgumentException exception) {
                printError(exception);
            }
        }
    }

    public int parsePurchaseAmount(String input) {
        String trimmed = trim(input);
        int purchaseAmount = parseInteger(trimmed, "[ERROR] 구입 금액은 숫자여야 합니다.");
        validatePositive(purchaseAmount);
        validatePurchaseUnit(purchaseAmount);
        return purchaseAmount;
    }

    public List<Integer> parseWinningNumbers(String input) {
        String trimmed = trim(input);
        return splitToIntegers(trimmed);
    }

    public int parseBonusNumber(String input, List<Integer> winningNumbers) {
        String trimmed = trim(input);
        int bonusNumber = parseInteger(trimmed, "[ERROR] 보너스 번호는 숫자여야 합니다.");
        validateNumberRange(bonusNumber);
        validateBonusDuplicate(bonusNumber, winningNumbers);
        return bonusNumber;
    }

    private String readLine(String prompt) {
        System.out.println(prompt);
        if (scriptIndex < scriptedInputs.size()) {
            return scriptedInputs.get(scriptIndex++);
        }
        return Console.readLine();
    }

    private void printError(IllegalArgumentException exception) {
        System.out.println(exception.getMessage());
    }

    private String trim(String input) {
        if (input == null) {
            throw new IllegalArgumentException("[ERROR] 입력값이 비어 있습니다.");
        }
        return input.trim();
    }

    private int parseInteger(String input, String errorMessage) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private void validatePositive(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 0보다 커야 합니다.");
        }
    }

    private void validatePurchaseUnit(int amount) {
        if (amount % PURCHASE_UNIT != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위로 입력해야 합니다.");
        }
    }

    private List<Integer> splitToIntegers(String input) {
        String[] tokens = input.split(NUMBER_DELIMITER);
        List<Integer> numbers = new ArrayList<>();
        for (String token : tokens) {
            String trimmedToken = token.trim();
            int number = parseInteger(trimmedToken, "[ERROR] 당첨 번호는 숫자여야 합니다.");
            numbers.add(number);
        }
        validateWinningNumbers(tokens, numbers);
        return numbers;
    }

    private void validateWinningNumbers(String[] tokens, List<Integer> numbers) {
        validateWinningCount(tokens);
        validateWinningDuplicates(numbers);
        validateWinningRange(numbers);
    }

    private void validateWinningCount(String[] tokens) {
        if (tokens.length != WINNING_NUMBER_COUNT) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 6개여야 합니다.");
        }
    }

    private void validateWinningDuplicates(List<Integer> numbers) {
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);
        if (uniqueNumbers.size() != numbers.size()) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호에 중복된 숫자가 있습니다.");
        }
    }

    private void validateWinningRange(List<Integer> numbers) {
        for (int number : numbers) {
            validateNumberRange(number);
        }
    }

    private void validateNumberRange(int number) {
        if (number < LOTTO_MIN_NUMBER || number > LOTTO_MAX_NUMBER) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    private void validateBonusDuplicate(int bonusNumber, List<Integer> winningNumbers) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    void setScriptedInputs(List<String> inputs) {
        scriptedInputs.clear();
        scriptedInputs.addAll(inputs);
        scriptIndex = 0;
    }

    void clearScript() {
        scriptedInputs.clear();
        scriptIndex = 0;
    }
}
