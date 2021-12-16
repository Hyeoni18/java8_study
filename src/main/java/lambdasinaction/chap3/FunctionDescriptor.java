package lambdasinaction.chap3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FunctionDescriptor {
    public static void main(String ...arg) throws Exception{

        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        //List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);

        forEach(
                Arrays.asList(1,2,3,4,5), (Integer i) -> System.out.println(i)
        );

        List<Integer> l = map(
                Arrays.asList("lambdas", "in", "action"), (String s) -> s.length()
        );

        System.out.println(l);

        //자바8 에서는 기본형을 입출력으로 사용하는 상황에서 오토박싱 동작을 피할 수 있도록 특별한 버전의 함수형 인터페이스를 제공한다.
        IntPredicate evenNumbers = (int i) -> i % 2 == 0;
        evenNumbers.test(1000); //참(박싱없음)
        System.out.println(evenNumbers.test(1000));

        Predicate<Integer> oddNumbers = (Integer i) -> i % 2 == 1;
        oddNumbers.test(1000); //거짓(박싱)
        System.out.println(oddNumbers.test(1000));

        //(T, U) -> R 같은 표기법으로 함수 디스크립터를 설명할 수 있음을 기억하자.
    }

    //Predicate 인터페이스는 test 라는 추상 메서드를 정의하며 test 는 제네릭 형식 T의 객체를 인수로 받아 불린을 반환한다.
    @FunctionalInterface
    public interface Predicate<T> {
        boolean test(T t);
    }

    //String 객체를 인수로 받는 람다를 정의할 수 있다.
    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for(T s: list) {
            if(p.test(s)) {
                results.add(s);
            }
        }
        return results;
    }

    //Consumer 인터페이스는 제네릭 형식 T 객체를 받아서 void 를 반환하는 accept 라는 추상 메서드를 정의한다.
    @FunctionalInterface
    public interface Consumer<T> {
        void accept(T t);
    }

    public static <T> void forEach(List<T> list, Consumer<T> c) {
        for(T i: list) {
            c.accept(i);
        }
    }

    //Function 인터페이스는 제네릭 형식 T를 인수로 받아서 제네릭 형식 R 객체를 반환하는 apply 라는 추상 메서드를 정의한다.
    @FunctionalInterface
    public interface Function<T, R>{
        R apply(T t);
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for(T s: list) {
            result.add(f.apply(s));
        }
        return result;
    }

    //자바의 모든 형식은 참조형(Byte, Integer, Object, List) 아니면 기본형(int, double, byte, char)에 해당한다.
    //하지만 제네릭 파라미터 Consumer<T> 의 T 에는 참조형만 사용할 수 있다. 제네릭의 내부 구현 때문에 어쩔 수 없는 일이다.

    //박싱한 값은 기본형을 감싸는 래퍼며 힙에 저장된다. ?힙
    //따라서 박싱한 값은 메모리를 더 소비하여 기본형을 가져올 때도 메모리를 탐색하는 과정이 필요하다.

    public interface IntPredicate {
        boolean test(int i);
    }
}
