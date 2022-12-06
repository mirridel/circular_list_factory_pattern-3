package javapart.org.example;

import javapart.data.structure.CircularList;
import javapart.data.types.Point;
import javapart.data.types.UserFactory;
import javapart.data.types.UserType;

import javapart.data.types.Comparator;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        //Creates CircularList cl - 1, 2, 3, 4
        CircularList<Object> cl = new CircularList<>();
        UserType p = UserFactory.getBuilderByName("Integer");
        test(p);

        for (int i = 20; i > 0; i--) {
            cl.add(i);
        }

        //prints the original list
        System.out.println("\nOriginal list: ");
        System.out.println(cl.toString());

        //adds new item at the front of the list
        System.out.println("\naddFront(9)");
        cl.addFront(9);
        System.out.println(cl.toString());
        if ((Integer)cl.getHeadData() == 9)
            System.out.println("OK!");
        else
            System.out.println("FAIL!");

        //adds new item at the back of the list
        System.out.println("\naddBack(8)");
        cl.addBack(8);
        System.out.println(cl.toString());
        if ((Integer)cl.getTailData() == 8)
            System.out.println("OK!");
        else
            System.out.println("FAIL!");

        //inserts new item at the specified position
        System.out.println("\naddAtPosition(6,2)");
        cl.addAtPosition(6, 2);
        System.out.println(cl.toString());
        if ((Integer)cl.getDataAtPosition(2) == 6)
            System.out.println("OK!");
        else
            System.out.println("FAIL!");

        //removes the specified item from the list
        System.out.println("\nremove(4)");
        cl.remove(4);
        System.out.println(cl.toString());
        if (cl.get(4) == null)
            System.out.println("OK!");
        else
            System.out.println("FAIL!");

        //removes the front item from the list
        System.out.println("\nremoveFront()");
        cl.removeFront();
        System.out.println(cl.toString());
        if ((Integer) cl.getHeadData() == 20)
            System.out.println("OK!");
        else
            System.out.println("FAIL!");

        //removes the last item  from the list
        System.out.println("\nremoveBack()");
        cl.removeBack();
        System.out.println(cl.toString());
        if ((Integer) cl.getTailData() == 1)
            System.out.println("OK!");
        else
            System.out.println("FAIL!");

        //removes the item in the specified position
        System.out.println("\nremoveAtPosition(1)");
        cl.removeAtPosition(1);
        System.out.println(cl.toString());
        if ((Integer) cl.getDataAtPosition(1) == 19)
            System.out.println("OK!");
        else
            System.out.println("FAIL!");

        //removes the item in the specified position
        System.out.println("\nsortList(comparator)");
        //sort list
        try {
            cl.sort((Comparator<Object>) p.getTypeComparator());
        } catch (StackOverflowError ignored) {
            System.err.println("Stack error");
            return;
        }
        System.out.println(cl.toString());



    }

    private static void test(UserType builder) {
        for (int i = 1; i < 11; i++) {
            int n = (i * i) * 10000;
            System.out.println("N = " + n);
            CircularList<Object> list = new CircularList<>();
            for (int j = 0; j < n; j++) list.add(builder.create());

            long start = System.nanoTime();

            try {
                list.sort((Comparator<Object>) builder.getTypeComparator());
            } catch (StackOverflowError ignored) {
                System.err.println("Stack error");
                return;
            }
            long end = System.nanoTime();
            System.out.println("Millis elapsed " + (end - start) * 1.0);
        }
    }
}
