package data;

import javafx.beans.Observable;
import model.cards.Card;

import java.util.*;

public class ApExamTest {
    public static void main(String[] args) {
        MyClass a = new MyClass();
        MyClass b = new MyClass();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted())
                    MyClass.f(true);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted())
                    MyClass.f(false);
            }
        });
        /*t1.setDaemon(true);
        t2.setDaemon(true);*/
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}

class MyClass {
    static int i = 0;
    static final Object key = new Object();

    public static void f(boolean b) {
        synchronized (key) {
            if (b)
                System.out.println(i++);
            else
                System.out.println(i--);
        }
    }

}

