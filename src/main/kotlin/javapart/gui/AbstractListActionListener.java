package javapart.gui;

import javapart.data.types.UserFactory;
import javapart.data.types.UserType;

import javax.swing.*;

public abstract class AbstractListActionListener implements ListActionListener {
    protected final String filename = "file.dat";

    protected final DefaultListModel<Object> listModel = new DefaultListModel<>();
    protected UserType builder;

    @Override
    public DefaultListModel<Object> getListModel() {
        return listModel;
    }

    @Override
    public void onSelectType(String type) {
        try {
            builder = UserFactory.getBuilderByName(type);
        } catch (Exception ignored) {

        }
    }
}

