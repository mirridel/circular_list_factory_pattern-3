import gui.ListActionListenerImpl
import javapart.gui.UI

fun main(args : Array<String>) {
	val listActionListener = ListActionListenerImpl()
	UI(listActionListener)
}