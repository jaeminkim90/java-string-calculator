package step2;

import java.util.Collections;
import java.util.List;

public class Calculator {

    private static final int NUMBERS_MIN_SIZE = 2;
    private static final int OPERATORS_MIN_SIZE = 1;
    private static final int NUMBERS_AND_OPERATORS_INTERVAL = 1;

    private final List<Integer> numbers; // 숫자만 모아놓은 List
    private final List<Operator> operators; // 연산자만 모아놓은 List

    // 계산기 인스턴스는 생성자를 이용해 계산의 재료가 되는 숫자와 연산자 모음을 필수로 전달 받는다.
    public Calculator(List<Integer> numbers, List<Operator> operators) {
        validateMinSize(numbers, operators); // 연산에 필요한 최소 조건을 검증
        validateFormat(numbers, operators); // 연산자의 갯수가 숫자의 갯수보다 1개 작은지 검증
        this.numbers = Collections.unmodifiableList(numbers); // Read-Only 처리하여 필드로 넘긴다.
        this.operators = Collections.unmodifiableList(operators); // Read-Only 처리하여 필드로 넘긴다.
    }

    // 계산을 수행할 수 있는 정수와 연산자의 최소 조건(갯수) 검증
    private void validateMinSize(List<Integer> numbers, List<Operator> operators) {
        if (numbers.size() < NUMBERS_MIN_SIZE) {
            // 계산의 대상이 되는 숫자가 2개보다 작을 경우 예외 발생
            throw new IllegalArgumentException(String.format("방정식의 숫자의 수는 %d개 이상이어야 합니다.", NUMBERS_MIN_SIZE));
        }

        if (operators.size() < OPERATORS_MIN_SIZE) {
            // 계산에 사용되는 연산자가 없을 경우 예외 발생
            throw new IllegalArgumentException(String.format("방정식의 연산자의 수는 %d개 이상이어야 합니다.", OPERATORS_MIN_SIZE));
        }
    }

    // 숫자와 연산자의 갯수 차이를 통해 정상적인 계산이 가능한지 검증
    private void validateFormat(List<Integer> numbers, List<Operator> operators) {
        int interval = numbers.size() - operators.size();
        if (interval != NUMBERS_AND_OPERATORS_INTERVAL) {
            // (숫자 - 연산자)의 수가 1이 아닐 경우 예외 발생
            throw new IllegalArgumentException("방정식의 올바른 형식이 아닙니다.");
        }
    }

    // Calculator 클래스가 상태로 가지고 있는 숫자와 연산자의 통합 계산을 수행하고, 결과를 반환한다.
    public int calculate() {
        int result = numbers.get(0); // 첫번째 숫자를 result에 넣어 계산 초기값을 준비한다
        for (int i = 0; i < operators.size(); i++) { // 연산자의 갯수만큼 연산을 반복한다
            Operator operator = operators.get(i);// 순차적으로 연산자 enum 객체를 찾는다
            Integer number = numbers.get(i + 1); // 연산의 대상이 되는 숫자를 number에 셋팅한다
            result = operator.calculate(result, number); // 두 수를 계산하여 중간 계산 결과를 result에 담는다
        }
        return result;
    }
}

