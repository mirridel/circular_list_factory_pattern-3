package javapart.gui;

import javapart.data.Action;
import javapart.data.structure.CircularList;

import java.io.*;

public class ListActionListenerImpl extends AbstractListActionListener {

    protected CircularList<Object> items = new CircularList<>();

    @Override
    public void onCreate() {
        Object data = builder.create();
        items.add(data);
        listModel.addElement(data);
    }

    @Override
    public void onAdd(String text) {
        if (text.isEmpty()) return;
        Object data = builder.parseValue(text);
        items.add(data);
        listModel.addElement(data);
    }

    @Override
    public void onInsert(String text, int index) {
        if (text.isEmpty()) return;
        Object data = builder.parseValue(text);
        items.addAtPosition(data, index);
        listModel.add(index, data);
    }

    @Override
    public void onRemove(int index) {
        items.removeAtPosition(index);
        listModel.remove(index);
    }

    @Override
    public void onSort() {
        items.sort(builder.getTypeComparator());
        listModel.clear();
        items.forEach((Action<Object>) listModel::addElement);
    }

    @Override
    public void onSave() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.dat")))
        {
            oos.writeObject(items);
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void onLoad() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.dat")))
        {
            items = (CircularList<Object>) ois.readObject();
            listModel.clear();
            items.forEach((Action<Object>) listModel::addElement);
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }
}
