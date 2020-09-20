private val maximumAmount: Double = 10000.0
private val minimumAmount: Double = 2000.0
private val maximumPeriod: Int = 60
private val minimumPeriod: Int = 12
private val currency = "€"
fun main(args: Array<String>) {
    val creditModifier: Double = 300.0
    //val creditModifier: Double = 30.0
    val loanPeriod: Int = 12 // 12 //1199    
    val loanAmount: Double = 4000.0 // 4000.0, modifier 300, period 12 //min 3599
    var creditScore: Double

    if (loanAmount < minimumAmount) {
        println("Your input loan amount too small: $loanAmount")
    } else if (loanAmount > maximumAmount) {
        println("Your input loan amount too big: $loanAmount")
    } else if (loanPeriod < minimumPeriod) {
        println("Your input loan period too small: $loanPeriod")
    } else if (loanPeriod > maximumPeriod) {
        println("Your input loan period too big: $loanPeriod")
    } else {        
        creditScore = calcCreditScore(creditModifier, loanAmount, loanPeriod)
        println("credit score is: $creditScore")
        if (creditScore >= 1) {
            val maxLoanAmount = findMaxPossibleAmount(creditModifier, loanAmount, loanPeriod)
            println("max possible credit: $maxLoanAmount $currency")
        } else if (creditScore < 1) {
            val minLoanAmount = findMinPossibleAmount(creditModifier, loanAmount, loanPeriod)
            println("min: $minLoanAmount")
 
            if (minLoanAmount.equals(0.0)) {
                println("min possible credit: $loanAmount $currency")
                val minLoanPeriod = findMinPossiblePeriod(creditModifier, loanAmount, loanPeriod)
                if (minLoanPeriod > maximumPeriod) {
                    println("Your minimum loan period $minLoanPeriod is bigger than period limit $maximumPeriod. Loan conditions failed.")
                } else {
                    println("min possible period: $minLoanPeriod")
                }
            } else {
                println("min possible credit: $minLoanAmount $currency")
            }
        }
    }
}
fun calcCreditScore(creditModifier: Double, loanAmount: Double, loanPeriod: Int): Double {
    return ((creditModifier / loanAmount) * loanPeriod)
}
fun findMaxPossibleAmount(creditModifier: Double, loanAmount: Double, loanPeriod: Int): Double {
    var creditScore: Double = 1.0
    var curAmount: Double = loanAmount

    while (creditScore >= 1) {
        if (curAmount > maximumAmount) {
            curAmount = maximumAmount
            break
        }
        creditScore = calcCreditScore(creditModifier, curAmount, loanPeriod)
        if (creditScore < 1) break
        curAmount += 1
    }
    return curAmount
}

fun findMinPossibleAmount(creditModifier: Double, loanAmount: Double, loanPeriod: Int): Double {
    var creditScore: Double = 0.0
    var curAmount: Double = loanAmount

    while (creditScore < 1) {
        if (curAmount < minimumAmount) {
            curAmount = 0.0
            break
        }
        creditScore = calcCreditScore(creditModifier, curAmount, loanPeriod)
        curAmount -= 1
    }
    return curAmount
}
fun findMinPossiblePeriod(creditModifier: Double, loanAmount: Double, loanPeriod: Int): Int {
    var creditScore: Double = 0.0
    var curPeriod: Int = loanPeriod

    while (creditScore < 1) {
        creditScore = calcCreditScore(creditModifier, loanAmount, curPeriod)
        curPeriod += 1
    }    
    return curPeriod
}