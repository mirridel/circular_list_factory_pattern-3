package structure

import javapart.data.Action
import javapart.data.structure.Node
import javapart.data.types.Comparator

open class CircularListKotlin<T> {
    private var head : Node<T>? = null
    private var tail : Node<T>? = null
    private var location : Node<T>? = null
    private var numElements = 0
    private var found = false

    init //default constructor
    {
        head = null
        tail = null
        numElements = 0
        location = null
    }

    fun isEmpty(): Boolean //true if list is empty, else false
    {
        return head == null
    }

    fun size(): Int // Determines the number of elements on this list
    {
        return numElements
    }

    protected fun find(target: T) // Searches list for an occurrence of an element. If successful, sets instance variables
    // found to true, location to node containing the element, and previous
    // to the node that links to location. If not successful, sets found to false.
    {
        location = head
        found = false
        if (!isEmpty()) {
            do {
                if (location!!.getData() == target) // if they match
                {
                    found = true
                    return
                } else {
                    location = location!!.getNext()
                }
            } while (location !== tail!!.getNext())
        }
    }

    operator fun contains(element: T): Boolean // Returns true if this list contains an element e such that
    // e.equals(element), otherwise returns false.
    {
        find(element)
        return found
    }

    private fun findPosition(position: Int) //finds position in this list
    // Assumes Zero based indexing
    {
        var start = 0
        location = head
        found = false
        if (!isEmpty() && position >= 0 && position <= size()) {
            do {
                // move search to the next node
                location = location!!.getNext()
                start++
            } while (location !== head && start < position)
            found = true
        }
    }

    operator fun get(data: T): T? // Returns an element e from this list such that e.equals(element);
    // if no such element exists, returns null.
    {
        find(data)
        return if (found) location!!.getData() else null
    }

    fun reset() // Initializes current position for an iteration through this list,
    // to the first element on this list.
    {
        location = head
    }


    fun add(data: T) // Adds element to this list.
    {
        val newNode: Node<T> = Node<T>(data) // Reference to the new node being added
        if (isEmpty()) // Adding into an empty list
        {
            head = newNode
            tail = newNode
            head!!.setPrevious(tail)
            tail!!.setNext(head)
        } else  // Adding into a non-empty list
        {
            tail!!.setNext(newNode)
            newNode.setPrevious(tail)
            tail = newNode
            tail!!.setNext(head)
        }
        numElements++
    }


    fun addFront(data: T) //adds new element to the front of the list
    {
        val newNode: Node<T> = Node<T>(data) // Reference to the new node being added
        if (isEmpty()) // Adding into an empty list
        {
            head = newNode
            tail = newNode
            head!!.setPrevious(tail)
            tail!!.setNext(head)
        } else  // Adding into a non-empty list
        {
            newNode.setNext(head)
            head!!.setPrevious(newNode)
            head = newNode
            head!!.setPrevious(tail)
            tail!!.setNext(head)
        }
        numElements++
    }

    fun addBack(data: T?) //adds new element to the back of the list
    {
        val newNode: Node<T> = Node<T>(data) // Reference to the new node being added
        if (isEmpty()) // Adding into an empty list
        {
            head = newNode
            tail = newNode
            head!!.setPrevious(tail)
            tail!!.setNext(head)
        } else  // Adding into a non-empty list
        {
            tail!!.setNext(newNode)
            newNode.setPrevious(tail)
            tail = newNode
            tail!!.setNext(head)
        }
        numElements++
    }

    // WITH COUNTER
    fun addBack(data: T?, count: MyCounter) {
        val newNode: Node<T> = Node<T>(data)
        if (isEmpty()) {
            head = newNode
            count.inc()
            tail = newNode
            count.inc()
            head!!.previous = tail
            count.inc()
            tail!!.next = head
            count.inc()
        }
        else {
            tail!!.next = newNode
            count.inc()
            newNode.previous = tail
            count.inc()
            tail = newNode
            count.inc()
            tail!!.previous = head
            count.inc()
        }
        numElements++
        count.inc()
    }

    fun addAtPosition(data: T?, position: Int) //adds new element to the specified position
    {
        val newNode: Node<T> = Node<T>(data)
        if (isEmpty()) {
            // add element to an empty list
            head = newNode
            tail = newNode
            head!!.setPrevious(tail)
            tail!!.setNext(head)
        } else if (position <= 0) {
            // insert at front of the list
            newNode.setNext(head)
            head!!.setPrevious(newNode)
            head = newNode
            head!!.setPrevious(tail)
            tail!!.setNext(head)
        } else if (position >= size()) {
            // if position does not exist, perform add at the most efficient
            // position for circular doubly linked list, the most efficient position is
            // at the end.
            tail!!.setNext(newNode)
            newNode.setPrevious(tail)
            tail = newNode
            tail!!.setNext(head)
        } else {
            /* General Case */
            // determine location where to perform insert
            findPosition(position)

            //inserts the elements to the specified position
            newNode.setPrevious(location!!.getPrevious())
            newNode.setNext(location)
            location!!.getPrevious().setNext(newNode)
            location!!.setPrevious(newNode)
        }
        numElements++
    }


    fun getHeadData(): T? {
        return head!!.getData()
    }

    fun getTailData(): T? {
        return tail!!.getData()
    }

    fun getDataAtPosition(position: Int): T? //adds new element to the specified position
    {
        return if (position <= 0) {
            getHeadData()
        } else if (position >= size()) {
            getTailData()
        } else {
            findPosition(position)
            location!!.getData()
        }
    }

    fun remove(element: T): Boolean // Removes an element e from this list such that e.equals(element)
    // and returns true; if no such element exists, returns false.
    {
        find(element)
        if (found) {
            if (location === head && size() == 1) //removes the only existing element
            //empties the list
            {
                head = null
                tail = null
            } else if (location === head) //removes first node
            {
                head = head!!.getNext()
                (head as Node<T>?)?.setPrevious(tail)
                tail!!.setNext(head)
            } else if (location === tail) //removes last node
            {
                tail = tail!!.getPrevious()
                (tail as Node<T>?)?.setNext(head)
                head!!.setPrevious(tail)
            } else {                         // removes node at location
                location!!.getPrevious().setNext(location!!.getNext())
                location!!.getNext().setPrevious(location!!.getPrevious())
            }
            numElements--
        }
        return found
    }

    fun removeFront() //removes the first element in the list
    {
        if (!isEmpty()) {
            if (head!!.getNext() === head) {  //if the first element is the only element in the list,	        		             //it empties the list
                head = null
                tail = null
            } else {            //removes the first element
                head = head!!.getNext()
                (head as Node<T>?)?.setPrevious(tail)
                tail!!.setNext(head)
            }
        }
        numElements--
    }

    fun removeFront(count: MyCounter) //removes the first element in the list
    {
        if (!isEmpty()) {
            if (head!!.next === head) {  //if the first element is the only element in the list,	        		             //it empties the list
                head = null
                count.inc()
                tail = null
                count.inc()
            } else {            //removes the first element
                head = head!!.next
                count.inc()
                (head as Node<T>?)?.previous = tail
                count.inc()
                tail!!.setNext(head)
                count.inc()
            }
        }
        numElements--
        count.inc()
    }

    fun removeBack() //removes the last element in this list
    {
        if (!isEmpty()) {
            if (head!!.getNext() === head) { //if the last element is the only element in the list,
                //it empties the list
                head = null
                tail = null
            } else {                //removes the last element
                tail = tail!!.getPrevious()
                (tail as Node<T>?)?.setNext(head)
                head!!.setPrevious(tail)
            }
        }
        numElements--
    }

    fun removeAtPosition(position: Int) //removes the element in the specified position
    {
        if (position <= 0) {        //removes front element
            head = head!!.getNext()
            (head as Node<T>?)?.setPrevious(tail)
            tail!!.setNext(head)
        } else if (position >= size() - 1) { //remove last element
            tail = tail!!.getPrevious()
            (tail as Node<T>?)?.setNext(head)
            head!!.setPrevious(tail)
        } else {
            //general case
            //determines the position
            findPosition(position)

            //removes the element in the specified position
            location!!.getPrevious().setNext(location!!.getNext())
            location!!.getNext().setPrevious(location!!.getPrevious())
        }
        numElements--
    }

    override fun toString(): String // prints the elements of the list in a nicely formatted manner in forward order
    {
        var item = "List: [ "
        var current: Node<T>? = head
        if (!isEmpty()) {
            do {
                item += current!!.getData().toString() + " "
                //System.out.println(item);
                current = current.getNext()
            } while (current !== head)
        }
        item += "]"
        return item
    }


    fun printReverse(): String // prints the elements of the list in a nicely formatted manner in reverse order
    {
        var item = "List: [ "
        var current: Node<T>? = tail
        if (!isEmpty()) {
            do {
                item += current!!.getData().toString() + " "
                current = current.getPrevious()
            } while (current !== tail)
        }
        item += "]"
        return item
    }

    fun forEach(a: Action<T?>) {
        var tmp: Node<T>? = head
        for (i in 0 until size()) {
            a.toDo(tmp!!.data)
            tmp = tmp.next
        }
    }

    // WITHOUT Counter
    fun subList(min: Int, max: Int): CircularListKotlin<T>? {
        var lst: CircularListKotlin<T>? = CircularListKotlin()
        var n = 0

        this.forEach{el -> run {
            if (n in min until max)
                lst?.addBack(el)
            n+=1
        }
        }
        return lst
    }

    // WITH Counter
    fun subList(min: Int, max: Int, count: MyCounter): CircularListKotlin<T>? {
        var lst: CircularListKotlin<T>? = CircularListKotlin()
        var n = 0
        this.forEach{el -> run {
            if (n in min until max) {
                lst?.addBack(el)
                count.inc()
            }
            n+=1
            count.inc()
        }
        }
        return lst
    }

    // Sort function WITHOUT counter. Type is merge sort.
    fun mergeSort(comparator: Comparator<Any>): CircularListKotlin<T>? {
        var mid = size() / 2
        if (mid == 0)
            return this

        val L = subList(0, mid)
        val R = subList(mid, size())

        var first = L?.mergeSort(comparator)
        var second = R?.mergeSort(comparator)

        return merge(first, second, comparator)
    }

    // WITH counter
    fun mergeSort(comparator: Comparator<Any>, count: MyCounter): CircularListKotlin<T>? {
        var mid = size() / 2
        count.inc()
        if (mid == 0)
            return this

        val L = subList(0, mid, count)
        val R = subList(mid, size(), count)

        var first = L?.mergeSort(comparator, count)
        var second = R?.mergeSort(comparator, count)

        return merge(first, second, comparator, count)
    }

    // WITHOUT COUNTER
    private fun merge(first: CircularListKotlin<T>?, second: CircularListKotlin<T>?, comparator: Comparator<Any>): CircularListKotlin<T>? {
        var accumulator: CircularListKotlin<T> = CircularListKotlin()
        if (first != null && second != null) {
            while (first.size() > 0 && second.size() > 0) {
                if (comparator.compare(first.getHeadData(), second.getHeadData()) <= 0) {
                    accumulator.addBack(first.getHeadData())
                    first.removeFront()
                }
                else {
                    accumulator.addBack(second.getHeadData())
                    second.removeFront()
                }
            }
            while (first.size() > 0) {
                accumulator.addBack(first.getHeadData())
                first.removeFront()
            }
            while (second.size() > 0) {
                accumulator.addBack(second.getHeadData())
                second.removeFront()
            }
        }
        return accumulator
    }

    // WITH COUNTER
    private fun merge(first: CircularListKotlin<T>?, second: CircularListKotlin<T>?, comparator: Comparator<Any>, count: MyCounter): CircularListKotlin<T>? {
        var accumulator: CircularListKotlin<T> = CircularListKotlin()
        if (first != null && second != null) {
            while (first.size() > 0 && second.size() > 0) {
                if (comparator.compare(first.getHeadData(), second.getHeadData()) <= 0) {
                    accumulator.addBack(first.getHeadData(), count)
                    first.removeFront(count)
                }
                else {
                    accumulator.addBack(second.getHeadData(), count)
                    second.removeFront(count)
                }
            }
            while (first.size() > 0) {
                accumulator.addBack(first.getHeadData(), count)
                first.removeFront(count)
            }
            while (second.size() > 0) {
                accumulator.addBack(second.getHeadData(), count)
                second.removeFront(count)
            }
        }
        return accumulator
    }
}