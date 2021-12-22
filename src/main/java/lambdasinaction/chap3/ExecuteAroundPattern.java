package lambdasinaction.chap3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

public class ExecuteAroundPattern {

    public static void main(String[] args) throws Exception {
        //한 행을 처리하는 코드 (람다전달)
        String oneLine = processFile((BufferedReader br) -> br.readLine());
        System.out.println(oneLine);
        String result = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        System.out.println(result);

        BufferedReaderProcessor p = (BufferedReader br) -> br.readLine();
        System.out.println(p);

        //명시적으로 확인된 예외를 잡을 수 있다.
        Function<BufferedReader, String> f = (BufferedReader b) -> {
            try {
                return b.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        System.out.println(f);
    }

    //함수형 인터페이스를 이용해서 람다를 전달하는 방법
    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("D:\\java8test\\src\\main\\resources\\lambdasinaction\\chap3\\data.txt"))) {
            //return br.readLine();
            return p.process(br);
        }
    }

    //String 과 IOException 을 던질 수 있는 시그니처와 일치하는 함수형 인터페이스를 만들어야 한다.
    @FunctionalInterface
    public interface BufferedReaderProcessor { //정의한 인터페이스를 processFile 메서드의 인수로 전달할 수 있다.
        String process(BufferedReader b) throws IOException;
    }

    @FunctionalInterface
    public interface Function<T, R>{
        R apply(T t);
    }
}
