package lambdasinaction.chap1;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//JAVA 8 IN ACTION 예제 1

class Main {
    public static void main(String[] args) throws Exception{
        List<Student> list = new ArrayList<Student>();
        
        list.add(new Student("a", 5));
        list.add(new Student("b", 10));
        list.add(new Student("c", 1));
        list.add(new Student("d", 52));
        list.add(new Student("e", 23));

        Collections.sort(list, new Comparator<Student>() {
            
            public int compare(Student s1, Student s2) {
                if(s1.getScore() < s2.getScore()) {
                    return -1;
                } else if (s1.getScore() > s2.getScore()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        //java 8
        list.sort(Comparator.comparing(Student::getScore));

        for (Student s : list) {
            System.out.println(s.getScore());
        }

        String dirName = "C:\\";
        File dir = new File(dirName);
        File[] hiddenFiles = dir.listFiles(File::isHidden);

        File[] hiddenFiles2 = new File(".").listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isHidden();
            }
        });

       File[] hiddenFiles3 = new File(".").listFiles(File::isHidden);

        for (File hiddenFile : hiddenFiles) {
            System.err.println(hiddenFile.getName());
        }
      
    }
}

class Student {
    String name;
    int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }
}