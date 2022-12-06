package javapart.gui;

import javapart.data.types.UserFactory;

import javax.swing.*;
import java.awt.*;

public class UI extends JFrame {
    private final JList<Object> jList;
    public UI(ListActionListener actionListener) throws HeadlessException {
        this.jList = new JList<>(actionListener.getListModel());
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        // Создание панели меню и добавление компонентов
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("File");
        mb.add(m1);
        m1.add(new UIAction(actionListener::onLoad, "Open"));
        m1.add(new UIAction(actionListener::onSave, "Save"));
        add(BorderLayout.NORTH, mb);

        JPanel listPanel = new JPanel();
        JScrollPane jsp = new JScrollPane(jList);
        jsp.setPreferredSize(new Dimension(320,400));
        listPanel.add(jsp);
        container.add(listPanel, BorderLayout.WEST);

        JPanel right = new JPanel();
        container.add(right, BorderLayout.EAST);

        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        JPanel chooseType = new JPanel();
        right.add(chooseType);
        JComboBox<String> comboBox = new JComboBox<>(UserFactory.getTypeNameList().toArray(new String[0]));
        chooseType.add(comboBox);
        comboBox.addActionListener(e -> {
            JComboBox source = (JComboBox) e.getSource();
            String selectedItem = (String) source.getSelectedItem();
            actionListener.onSelectType(selectedItem);
            right.add(new UIAction(actionListener::onCreate, "Create element"));
            right.add(new UIAction(actionListener::onAdd, "Add element"));
            right.add(new UIAction((text) -> actionListener.onInsert(text, jList.getSelectedIndex()), "Insert element"));
            right.add(new UIAction(() -> actionListener.onRemove(jList.getSelectedIndex()), "Remove element"));
            right.add(new UIAction(actionListener::onSort, "Sort"));
            right.remove(chooseType);
            revalidate();
            repaint();
        });

        setTitle("Program");
        setPreferredSize(new Dimension(640, 480));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
        pack();
        setVisible(true);
    }
}
