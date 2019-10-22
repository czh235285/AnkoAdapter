package com.czh.adapter


fun main() {
    factor(951210)

}

fun factor(num: Int) {
    var a = num
    var k = 2
    print("$a=")
    while (k <= a) {
        if (k == a) {
            println("" + a)
            break
        } else if (a % k == 0) {
            print("$k*")
            a /= k
        } else {
            k++
        }
    }
}
