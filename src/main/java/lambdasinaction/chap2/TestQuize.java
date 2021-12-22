package lambdasinaction.chap2;

import java.awt.event.ActionEvent;
import java.util.*;
import java.lang.*;

public class TestQuize {

	public static void main(String ... args){
        
        List<Apple> inventory = Arrays.asList(new Apple(80,"green"), new Apple(155, "green"), new Apple(120, "red"));

        prettyPrintApple(inventory, new AppleReturnString());
        prettyPrintApple(inventory, new AppleFancyString());

		//람다 표현식을 사용한 예제
		List<Apple> redApples = filter(inventory, (Apple apple) -> "red".equals(apple.getColor()));
		System.err.println(redApples);

		//List<String> evenNumbers = filter(numbers, (Integer i) -> i % 2 == 0);

		//Comparator 를 구현해서 sort 메서드의 동작을 다양화할 수 있다. 익명 클래스를 사용하여 무게가 적은 순으로 정렬.
		// inventory.sort(new Comparator<Apple>() {
		// 	public int compare(Apple a1, Apple a2) {
		// 		return a1.getWeight().compareTo(a2.getWeight());
		// 	}			
		// });

		System.out.println(inventory);
		//기존소스
		inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
		System.out.println(inventory);

		//메서드 레퍼런스와 java.util.Comparator.comparing 을 활용한 코드 (chapter 3.6 에서 추가)
		inventory.sort(java.util.Comparator.comparing(Apple::getWeight)); //첫 번째 메서드 레퍼런스
		System.out.println(inventory);

		// Thread t = new Thread(new Runnable() {
		// 	public void run() {
		// 		System.out.println("Hello world");
		// 	}
		// });

		Thread t = new Thread(() -> System.out.println("Hello world"));

		//메서드 레퍼런스 예제2
		List<String> str = Arrays.asList("a","b","A","B");
		System.out.println(str);
		str.sort((s1, s2) -> s1.compareToIgnoreCase(s2)); //람다 정의
		System.out.println(str);
		str.sort(String::compareToIgnoreCase); //메서드 레퍼런스 사용
		System.out.println(str);
    }

    public static void prettyPrintApple(List<Apple> inventory, ApplePredicate p) {
        for(Apple apple : inventory) {
            String output = p.test(apple);
            System.out.println(output);
        }
    }

    public static class Apple {
		private int weight = 0;
		private String color = "";

		public Apple(int weight, String color){
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

    interface ApplePredicate {
        public String test(Apple a);
    }

    static class AppleReturnString implements ApplePredicate{
		public String test(Apple apple){
			return apple.getWeight()+"kg"; //사과무게 출력
		}
	}

    static class AppleFancyString implements ApplePredicate{
        public String test(Apple apple){
            String text = apple.getWeight() > 150 ? "heavy" : "light";
            return apple.getWeight()+"kg is "+text;
        }
    }

	//람다식으로 사용
	public interface Predicate<T> {
		boolean test(T t);
	}

	public static <T> List<T> filter(List<T> list, Predicate<T> p) { //형식 파라미터 T
		List<T> result = new ArrayList<>();
		for(T e : list) {
			if(p.test(e)) {
				result.add(e);
			}
		}
		return result;
	}

	//java.util.Comparator
	public interface Comparator<T> {
		public int compare(T o1, T o2);
	}

	//java.lang.Runnable
	public interface Runnable {
		public void run();
	}

	//java.awt.event.ActionListener
	public interface ActionListener extends EventListener {
		void actionPerformed(ActionEvent e);
	}

	//java.util.concurrent.Callable
	public interface Callable<V> {
		V call();
	}

	//java.security.PrivilegedAction
	public interface PrivilegedAction<V> {
		V run();
	}
}