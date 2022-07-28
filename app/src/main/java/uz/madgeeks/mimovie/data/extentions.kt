package uz.madgeeks.mimovie.data

fun Int.runtimeToHM(): String {
    return "${this / 60}h ${this % 60}m"
}

fun String.changeMoneyType(): String {
    var reversedSum = ""
    for (i in this.length - 1 downTo 0) {
        reversedSum += this[i]
    }
    var changedSum = ""
    if (reversedSum.length > 3) {
        var k = 2
        for (i in reversedSum.indices) {
            changedSum += reversedSum[i]
            if (i >= k) {
                changedSum += " "
                k += 3
            }
        }
        reversedSum = ""
        for (i in changedSum.length - 1 downTo 0) {
            reversedSum += changedSum[i]
        }
    } else {
        reversedSum = this
    }
    return reversedSum.trim()
}

fun String.formatDate() : String {
    return "${this.getMonth()} ${this.getDay()}, ${this.getYear()}"
}

fun String.getYear(): String {
    return "${this[0]}${this[1]}${this[2]}${this[3]}"
}

fun String.getMonth(): String {
    return when ("${this[5]}${this[6]}") {
        "01" -> "January"
        "02" -> "February"
        "03" -> "March"
        "04" -> "April"
        "05" -> "May"
        "06" -> "June"
        "07" -> "July"
        "08" -> "August"
        "09" -> "September"
        "10" -> "October"
        "11" -> "November"
        "12" -> "December"
        else -> ""
    }
}

fun String.getDay(): String {
    return if ("${this[8]}" == "0") "${this[9]}" else "${this[8]}${this[9]}"
}