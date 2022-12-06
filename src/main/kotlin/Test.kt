import javapart.data.types.Comparator
import javapart.data.types.UserFactory
import javapart.data.types.UserType
import structure.CircularListKotlin
import structure.MyCounter

fun main(args : Array<String>) {

    val cl = CircularListKotlin<Any>()
    val p = UserFactory.getBuilderByName("Integer")

    for (i in 20 downTo  1) {
        cl.add(i)
    }

    //prints the original list
    println("\nOriginal list: ")
    println(cl.toString())

    //adds new item at the front of the list
    println("\naddFront(9)")
    cl.addFront(9)
    if (cl.getHeadData() == 9) println("OK!")
    else println("FAIL!")

    //adds new item at the back of the list
    println("\naddBack(8)")
    cl.addBack(8)
    println(cl.toString())
    if (cl.getTailData() == 8) println("OK!")
    else println("FAIL!")

    //inserts new item at the specified position
    println("\naddAtPosition(6,2)")
    cl.addAtPosition(6, 2)
    println(cl.toString())
    if (cl.getDataAtPosition(2) == 6) println("OK!")
    else println("FAIL!")

    //removes the specified item from the list
    println("\nremove(4)")
    cl.remove(4)
    println(cl.toString())
    if (cl.getDataAtPosition(4) != null) println("OK!")
    else println("FAIL!")

    //removes the front item from the list
    println("\nremoveFront()")
    cl.removeFront()
    println(cl.toString())
    if (cl.getHeadData() == 20) println("OK!")
    else println("FAIL!")

    //removes the last item  from the list
    println("\nremoveBack()")
    cl.removeBack()
    println(cl.toString())
    if (cl.getTailData() == 1) println("OK!")
    else println("FAIL!")

    //removes the item in the specified position
    println("\nremoveAtPosition(1)")
    cl.removeAtPosition(1)
    println(cl.toString())
    if (cl.getDataAtPosition(1) == 19) println("OK!")
    else println("FAIL!")

    //sort list
    println("\nsortList(comparator)")
    var clSorted: CircularListKotlin<Any>
    try {
        clSorted = cl.mergeSort((p.typeComparator as Comparator<Any>))!!
    } catch (ignored: StackOverflowError) {
        System.err.println("Stack error")
        return
    }
    println("Before: $cl")
    println("After: $clSorted")
    println()

    test(p)
}

private fun test (builder: UserType): Unit {
    var i = 1
    while (i <= 2048) {
        val n = i * 10000
        println("N = $n")

        val list = CircularListKotlin<Any>()
        for (j in 0 until n) {
            list.add(builder.create())
        }
        val start = System.nanoTime()
        var myCounter = MyCounter()

        try {
            var result = list.mergeSort(builder.typeComparator, myCounter)
            println("Count: ${myCounter.get()}")
        }
        catch (e: StackOverflowError){
            error("StackOverflowError")
            return
        }

        val end = System.nanoTime()
        val fin = (end - start) * 0.000000001
        println("Seconds elapsed $fin")
        i *= 2
    }
}