package structure

class MyCounter(var count: Long = 0) {
    fun inc(): Unit {
        count += 1
    }

    fun inc(n: Long): Unit {
        count += n
    }

    fun get(): Long {
        return count
    }
}