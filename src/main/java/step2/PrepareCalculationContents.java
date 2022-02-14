package step2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrepareCalculationContents {
    private static final String SEPARATOR = " "; // 문자열 분리의 기준이 되는 상수(공백)
    private static final int INTERVAL = 2; // 문자 배열에서 숫자와 연산자의 위치가 구분되는 인덱스 간격

    // 계산 재료를 준비하는 메서드: 문자열을 수식을 숫자 List와 연산자 List로 분리하고, 계산기 객체를 생성한다.
    public Calculator create(String stringExpression) {
        List<String> strings = Arrays.asList(stringExpression.split(SEPARATOR));// Arrays.asList()는 Arrays의 private 정적 클래스인 ArrayList를 리턴
        List<Integer> numbers = createNumbers(strings); // 문자열 수식에서 숫자 부분을 분리한다
        List<Operator> operators = createOperators(strings); // 문자열 수식에서 연산자 부분을 분리한다.
        return new Calculator(numbers, operators); // 숫자와 연산자 List 이용해 방정식 객체를 만든다
    }

    // 문자열의 짝수 위치 문자를 이용해 숫자 List를 만드는 메서드
    private List<Integer> createNumbers(List<String> strings) {
        List<String> evenPositions = new ArrayList<>(); // 문자열 수식에서 짝수에 해당하는 숫자를 분리한다
        for (int i = 0; i < strings.size(); i = i + INTERVAL) {
            // INTERVAL 상수를 이용해 문자열 수식의 짝수만 담는다.
            evenPositions.add(strings.get(i));
        }
        return evenPositions.stream() // 짝수 위치에 해당하는 숫자 List 전체를 순환한다
                .map(Integer::valueOf) // 문자를 숫자로 변환한 stream을 반환
                .collect(Collectors.toList()); // 변환된 숫자들을 List로 모은다
    }
    // 문자열의 홀수 위치 문자를 이용해 연산자 List를 만드는 메서드
    private List<Operator> createOperators(List<String> strings) {
        List<String> oddPositions = new ArrayList<>(); // 홀수 문자열을 담을 List
        for (int i = 1; i < strings.size(); i = i + INTERVAL) {
            oddPositions.add(strings.get(i)); // 홀수 위치에 해당하는 문자만 담는다.
        }
        return oddPositions.stream()// 홀수 문자를 순환한다.
                .map(Operator::matchOperator) // matchOperator를 참조하여 연산자기호가 일치하는 Enum 객체를 찾아 반환한다
                .collect(Collectors.toList()); // 반환되는 enum 객체를 List로 담는다
    }
}
