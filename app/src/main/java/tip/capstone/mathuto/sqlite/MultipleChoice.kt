package tip.capstone.mathuto.sqlite

data class MultipleChoice(
    val id: Int,
    var question: String,
    var optionA: String,
    var optionB: String,
    var optionC: String,
    var optionD: String,
    val correctAnswer: Int,
    var explanation: String
)
