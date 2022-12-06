package javapart.org.example;

import javapart.data.types.UserFactory;
import javapart.data.types.UserType;
import javapart.gui.ListActionListener;
import javapart.gui.ListActionListenerImpl;
import javapart.gui.UI;

public class Main {
    public static void main(String[] args) {
        UserType t = UserFactory.getBuilderByName("Point");
        ListActionListener listActionListener = new ListActionListenerImpl();
        new UI(listActionListener);
    }
}