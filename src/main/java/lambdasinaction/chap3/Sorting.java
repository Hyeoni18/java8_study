package lambdasinaction.chap3;

import lambdasinaction.chap2.FilteringApples;
import lambdasinaction.chap2.TestQuize;

import java.util.*;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;

public class Sorting {

    public static void main(String...args){

        // 1
        List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(new Apple(80,"green"), new Apple(155, "green"), new Apple(120, "red")));

        // [Apple{color='green', weight=80}, Apple{color='red', weight=120}, Apple{color='green', weight=155}]
        inventory.sort(new AppleComparator());
        System.out.println(inventory);

        // reshuffling things a little
        inventory.set(1, new Apple(30, "green"));
        
        // 2
        // [Apple{color='green', weight=30}, Apple{color='green', weight=80}, Apple{color='green', weight=155}]
        inventory.sort(new Comparator<Apple>() {
            public int compare(Apple a1, Apple a2){
                return a1.getWeight().compareTo(a2.getWeight()); 
        }});
        System.out.println(inventory);

        // reshuffling things a little
        inventory.set(1, new Apple(20, "red"));
        
        // 3
        // [Apple{color='red', weight=20}, Apple{color='green', weight=30}, Apple{color='green', weight=155}]
        inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
        System.out.println(inventory);
        
        // reshuffling things a little
        inventory.set(1, new Apple(10, "red"));
        
        // 4
        // [Apple{color='red', weight=10}, Apple{color='red', weight=20}, Apple{color='green', weight=155}]
        inventory.sort(comparing(Apple::getWeight)); //Comparator는 Comparable 키를 추출해서 Comparator 객체로 만드는 Function 함수를 인수로 받는 정적 메서드 comparing 을 포함한다.
        System.out.println(inventory);

        //역정렬
        inventory.sort(comparing(Apple::getWeight).reversed());

        //만약 같은 무게가 존재할 때 비교 결과를 좀 더 다듬는 Comparator를 만들 수 있다.
        inventory.sort(comparing(Apple::getWeight)
                        .reversed()
                        .thenComparing(Apple::getColor)); //무게가 같으면 색별로 정렬렬

        //3.8.2 Predicate 조합
        //빨간색이 아닌 사과, 처럼 특정 프레디케이트를 반전시킬 때 negate메서드를 사용할 수 있다.
        //Predicate<Apple> notRedApple = redApple.negate();
        //또한 and 메서드를 이용해서 빨간색이면서 무거운 사과를 선택하도록 두 람다를 좋바할 수 있다.
        //Predicate<Apple> redAndHeavyApple = redApple.and(a -> a.getWeight() > 150);
        //그뿐만 아니라 or을 이용해서 빨간색이면서 무거운 사과 또는 그냥 녹색 사과 등 다양한 조건을 만들 수 있다.
        //Predicate<Apple> redAndHeavyAppleOrGreen = redApple.and(a -> a.getWeight() > 150).or(a -> "green".equals(a.getColor()));
    }

    public static class Apple {
        private Integer weight = 0;
        private String color = "";

        public Apple(Integer weight, String color){
            this.weight = weight;
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String toString() {
            return "Apple{" +
                   "color='" + color + '\'' +
                   ", weight=" + weight +
                   '}';
        }
    }

    static class AppleComparator implements Comparator<Apple> {
        public int compare(Apple a1, Apple a2){
            return a1.getWeight().compareTo(a2.getWeight());
        }
    }

    //람다식으로 사용
    public interface Predicate<T> {
        boolean test(T t);
    }

    public static <T> List<T> filter(List<T> list, TestQuize.Predicate<T> p) { //형식 파라미터 T
        List<T> result = new ArrayList<>();
        for(T e : list) {
            if(p.test(e)) {
                result.add(e);
            }
        }
        return result;
    }
}
