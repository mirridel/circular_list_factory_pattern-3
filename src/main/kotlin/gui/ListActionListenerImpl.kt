package gui

import structure.CircularListKotlin
import javapart.gui.AbstractListActionListener
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

open class ListActionListenerImpl : AbstractListActionListener() {
    private var items = CircularListKotlin<Any>()
    override fun onCreate() {
        val data = builder.create()
        items.add(data)
        listModel.addElement(data)
    }

    override fun onAdd(text: String) {
        if (text.isEmpty()) return
        val data = builder.parseValue(text)
        items.add(data)
        listModel.addElement(data)
    }

    override fun onInsert(text: String, index: Int) {
        if (text.isEmpty()) return
        val data = builder.parseValue(text)
        items.addAtPosition(data, index)
        listModel.add(index, data)
    }

    override fun onRemove(index: Int) {
        items.removeAtPosition(index)
        listModel.remove(index)
    }

    override fun onSort() {
        System.out.println("До: $items")
        items = items.mergeSort(builder.typeComparator)!!
        System.out.println("После: $items")
        listModel.clear()
        items.forEach { el -> listModel.addElement (el) }
    }

    override fun onSave() {
        try {
            ObjectOutputStream(FileOutputStream("save.dat")).use { oos -> oos.writeObject(items) }
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    override fun onLoad() {
        try {
            ObjectInputStream(FileInputStream("save.dat")).use { ois ->
                items = ois.readObject() as CircularListKotlin<Any>
                listModel.clear()
                items.forEach{ el -> listModel.addElement(el) }
            }
        } catch (ex: Exception) {
            println(ex.message)
        }
    }
}