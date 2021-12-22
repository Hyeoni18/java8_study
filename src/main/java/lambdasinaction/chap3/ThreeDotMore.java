package lambdasinaction.chap3;

import lambdasinaction.chap8.FactoryMain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ThreeDotMore {
    public static void main(String[] args) throws Exception{

        //기존 코드
        Comparator<Apple> byWeight = new Comparator<Apple>() {
            public int compare(Apple a1, Apple a2) {
                return a1.getWeight().compareTo(a2.getWeight());
            }
        };

        //람다를 사용한 코드
		Comparator<Apple> d = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());

		//생성자 레퍼런스 (3.6.2)
		Supplier<Apple> c1 = Apple::new; //디폴드 생성자 Apple()의 생성자 레퍼런스
		Apple a1 = c1.get(); //Supplier의 get 메서드를 호출해서 새로운 Apple 객체를 만들 수 있다.
		System.out.println(a1);
		//위 예제는 다음과 같다
		Supplier<Apple> c2 = () -> new Apple(); //람다 표현식은 디폴트 생성자를 가진 Apple을 만든다.
		Apple a2 = c2.get(); //Supplier의 get 메서드를 호출해서 새로운 Apple 객체를 만들 수 있다.
		System.out.println(a2);

		Function<Integer, Apple> c3 = Apple::new; //Apple(Integer weight)의 생성자 레퍼런스
		Apple a3 = c3.apply(110);
		System.out.println(a3);

		Function<Integer, Apple> c4 = (weight) -> new Apple(weight); //특정 무게의 사과를 만드는 람다 표현식
		Apple a4 = c4.apply(110);
		System.out.println(a4);

		List<Integer> weights = Arrays.asList(7,3,4,10);
		System.out.println(weights);
		List<Apple> apples = map(weights, Apple::new);
		System.out.println(apples);

		BiFunction<Integer, String, Apple> c5 = Apple::new;
		Apple a5 = c5.apply(110, "green");
		System.out.println(a5);
		//이렇게 인스턴스화하지 않고도 생성자에 접근할 수 있는 기능을 다양한 상황에 응용할 수 있다.

//		TriFunction<Integer, Integer, Integer, Color> color = Color::new; // go to line 121
	}

	//그리고 String, Integer가 주어졌을 때 다양한 무게를 갖는 여러 종류의 과일을 만드는 giveMeFruit라는 메서드를 만들 수 있다.
	static Map<String, Function<Integer, Fruit>> map = new HashMap<>();
    static {
  //  	map.put("apple", Apple::new);
  //  	map.put("orange", Orange::new);
	}

	public static Fruit giveMeFruit(String fruit, Integer weight) {
    	return map.get(fruit.toLowerCase()) //map에서 Function<Integer, Fruit>를 얻었다.
				.apply(weight);	//Function 의 apply 메서드에 정수 무게 파라미터를 제공해서 Fruit 를 만들 수 있다.
	}

	public static class Fruit {

	}

	public static List<Apple> map(List<Integer> list, Function<Integer, Apple> f) {
    	List<Apple> result = new ArrayList<>();
    	for(Integer e : list) {
    		result.add(f.apply(e));
		}
    	return result;
	}

    public static class Apple {
		private int weight = 0;
		private String color = "";

		public Apple(int weight, String color){
			this.weight = weight;
			this.color = color;
		}

		public Apple() {

		}

		public Apple(int weight) {
			this.weight = weight;
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
	//생성자 레퍼런스 퀴즈, 인수가 세 개인 생성자 레퍼런스를 사용하기 위해 생성자 레퍼런스와 일치하는 시그니처를 갖는 함수형 인터페이스를 만들어줬다. (제공되지 않으므로)
//	public interface TriFunction(T, U, V, R) {
//    	R apply(T t, U u, V v);
//	}
}
