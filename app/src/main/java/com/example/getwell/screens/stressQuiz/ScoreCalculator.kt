package com.example.getwell.screens.stressQuiz

import com.example.getwell.data.AnxietyValues
import com.example.getwell.data.DepressionValues
import com.example.getwell.data.StigmaValues
import com.example.getwell.data.StressValues
import com.example.getwell.screens.stressanalysis.StressAIObj
import com.google.firebase.Timestamp
import java.util.Calendar

fun calculatePSSScore(quizItems: List<QuizItem>): Double {
    // Questions 4, 5, 7, and 8 need to be reverse scored
    val flag = hasUserCompletedTest(quizItems)
    val reverseScoreIds = setOf(4, 5, 7, 8)

    var totalScore = 0

    for (item in quizItems) {
        val score = if (item.id in reverseScoreIds) {
            // Reverse scoring: 0 -> 4, 1 -> 3, 2 -> 2, 3 -> 1, 4 -> 0
            4 - item.selectedOption
        } else {
            item.selectedOption
        }
        totalScore += score
    }
    return if (flag == 1.0) totalScore.toDouble() else -1.0
}

fun calculateDASSStressScore(quizItems: List<QuizItem>): Double {
    // Stress-related question IDs
    val flag = hasUserCompletedTest(quizItems)
    val stressQuestionIds = setOf(1, 6, 8, 11, 12, 14, 18)

    println("DSS LIST: $quizItems, flag : $flag")

    // Sum scores for stress-related questions only
    var stressScore = 0
    for (item in quizItems) {
        if (item.id in stressQuestionIds) {
            stressScore += item.selectedOption
        }
    }

    // Multiply the summed score by 2
    return if (flag == 1.0) (stressScore * 2).toDouble() else -1.0
}

fun calculateBDIScore(quizItems: List<QuizItem>): Double {
    // Stress-related question IDs
    val flag = hasUserCompletedTest(quizItems)
    println("BDI LIST: $quizItems, flag : $flag")
    var bdiScore = 0
    for (item in quizItems) {
            bdiScore += item.selectedOption
    }
    return if (flag == 1.0) (bdiScore).toDouble() else -1.0
}

fun calculateBDI(x: Double): Double {
    return when (x) {
        in 0.0..21.0 -> (x / 21.0) * 0.2
        in 21.0..36.0 -> 0.2 + 0.4 * ((x - 21.0) / 15.0)
        in 36.0..49.0 -> 0.6 + 0.2 * ((x - 36.0) / 13.0)
        in 49.0..63.0 -> 0.8 + 0.2 * ((x - 49.0) / 14.0)
        else -> 0.0
    }
}

fun calculateDASSDepressionScore(quizItems: List<QuizItem>): Double {
    // Stress-related question IDs
    val flag = hasUserCompletedTest(quizItems)
    val stressQuestionIds = setOf(3, 5, 10, 13, 16, 17, 21)

//    println("DSS Anxiety LIST: $quizItems, flag : $flag")

    // Sum scores for stress-related questions only
    var stressScore = 0
    for (item in quizItems) {
        if (item.id in stressQuestionIds) {
            stressScore += item.selectedOption
        }
    }

    // Multiply the summed score by 2
    return if (flag == 1.0) (stressScore * 2).toDouble() else -1.0
}

fun calculateDASSDepression(x: Double): Double {
    return when (x) {
        in 0.0..9.0 -> (x / 9.0) * 0.2
        in 9.0..13.0 -> 0.2 + ((x - 9.0) / 4.0) * 0.2
        in 13.0..20.0 -> 0.4 + ((x - 13.0) / 7.0) * 0.2
        in 20.0..27.0 -> 0.6 + ((x - 20.0) / 7.0) * 0.2
        in 27.0..42.0 -> 0.8 + ((x - 27.0) / 15.0) * 0.2
        else -> 0.0
    }
}



fun calculateBAIScore(quizItems: List<QuizItem>): Double {
    // Stress-related question IDs
    val flag = hasUserCompletedTest(quizItems)
    println("BAI LIST: $quizItems, flag : $flag")
    var baiScore = 0
    for (item in quizItems) {
        baiScore += item.selectedOption
    }
    return if (flag == 1.0) (baiScore).toDouble() else -1.0
}

fun calculateBAI(x: Double): Double {
    return when (x) {
        in 0.0..20.999 -> 0.2 * x / 21  // 0 <= x < 21
        in 21.0..34.999 -> 0.2 + ((x - 21) / 14) * 0.3  // 21 <= x < 35
        in 35.0..43.999 -> 0.5 + ((x - 35) / 9) * 0.1926  // 35 <= x < 44
        in 44.0..63.0 -> 0.6926 + ((x - 44) / 19) * 0.3074  // 44 <= x < 63
        else -> 0.0
    }
}


fun calculateDASSAnxietyScore(quizItems: List<QuizItem>): Double {
    // Stress-related question IDs
    val flag = hasUserCompletedTest(quizItems)
    val stressQuestionIds = setOf(2, 4, 7, 9, 15, 19, 20 )

//    println("DSS Anxiety LIST: $quizItems, flag : $flag")

    // Sum scores for stress-related questions only
    var stressScore = 0
    for (item in quizItems) {
        if (item.id in stressQuestionIds) {
            stressScore += item.selectedOption
        }
    }

    // Multiply the summed score by 2
    return if (flag == 1.0) (stressScore * 2).toDouble() else -1.0
}

fun calculateDASSAnxiety(x: Double): Double {
    return when (x) {
        in 0.0..6.999 -> (x / 7) * 0.20  // 0 <= x < 7
        in 7.0..13.999 -> 0.20 + ((x - 7) / 7) * 0.30  // 7 <= x < 14
        in 14.0..23.999 -> 0.50 + ((x - 14) / 10) * 0.30  // 14 <= x < 24
        in 24.0..42.0 -> 0.80 + ((x - 24) / 18) * 0.20  // 24 <= x <= 42
        else -> 0.0
    }
}



fun calculateAcadScore(quizItems: List<QuizItem>): Double {
    // Stress-related question IDs
    val flag = hasUserCompletedTest(quizItems)
    println("Acad LIST: $quizItems, flag : $flag")
    var acadScore = 0
    for (item in quizItems) {
        acadScore += item.selectedOption
    }
    return if (flag == 1.0) (acadScore / 15).toDouble() else -1.0
}


fun calculatePSS(x: Double): Double {
    return when (x) {
        in 0.0..13.0 -> (2.0 / 130.0) * x
        in 13.0..27.0 -> (1.0 / 35.0) * (x - 13.0) + 0.2
        in 27.0..34.0 -> 0.6 + (3.0 / 70.0) * (x - 27.0)
        in 34.0..40.0 -> 0.9 + (x - 34.0) / 60.0
        else -> 0.0 // Define behavior for values outside [0, 40] if needed
    }
}


fun calculateDASS(x: Double): Double {
    return when (x) {
        in 0.0..13.0 -> (2.0 / 130.0) * x
        in 13.0..26.0 -> (4.0 / 130.0) * (x - 13.0) + 0.2 // Fourth term
        in 26.0..34.0 -> (0.6 + (3.0 / 80.0) * (x - 26.0)) // Second term
        in 34.0..42.0 -> (0.9 + (x - 34.0) / 80.0)  // Third term
        else -> 0.0 // Behavior for values outside the defined range
    }
}

fun calculateMood(list1: List<Int>, list2: List<Double>): Double {
    // Step 1: Calculate the dot product
    val flag = hasUserCompletedTest(list1)
    val dotProduct = list1.zip(list2) { a, b -> a * b }.sum()

    // Step 2: Calculate the sum of all elements in the second list
    val sumList2 = list2.sum()

    // Step 3: Calculate the result and multiply by 20
    val result = if (sumList2 != 0.0) {
        (dotProduct / sumList2) * 20
    } else {
        0.0 // Handle division by zero if the second list is empty or contains all zeros
    }

    return if (flag == 1.0) result else 0.0
}


fun calculateFace(list1: List<Int>, list2: List<Double>): Double {
    // Step 1: Calculate the dot product
    val flag = hasUserCompletedTest(list1)
    val dotProduct = list1.zip(list2) { a, b -> a * b }.sum()

    // Step 2: Calculate the sum of all elements in the second list
    val sumList2 = list2.sum()

    // Step 3: Calculate the result and multiply by 20
    val result = if (sumList2 != 0.0) {
        (dotProduct / sumList2)
    } else {
        0.0 // Handle division by zero if the second list is empty or contains all zeros
    }

    return if (flag == 1.0) result else -1.0
}

fun calculateISMIScore(quizItems: List<QuizItem>): Double {
    val answeredCount = quizItems.count { it.selectedOption != -1 }
    val flag = hasUserCompletedTest(quizItems)
    val reverseScoreIds = setOf(2, 9)
    quizItems.forEach { item ->
        if (item.selectedOption != -1) {
            item.selectedOption += 1
        }
    }

    var totalScore = 0

    for (item in quizItems) {
        val score = if (item.id in reverseScoreIds) {
            5 - (item.selectedOption)
        } else {
            item.selectedOption
        }
        totalScore += score
    }
    return if (flag == 1.0) totalScore.toDouble() / answeredCount else -1.0
}

fun calculateDaysScore(quizItems: List<QuizItem>): Double {
    // Stress-related question IDs
    val flag = hasUserCompletedTest(quizItems)
//    println("Acad LIST: $quizItems, flag : $flag")
    var daysScore = 0
    for (item in quizItems) {
        daysScore += (item.selectedOption + 1)
    }
    return if (flag == 1.0) (daysScore).toDouble() else -1.0
}

fun calculateA(
    pssList: List<QuizItem>,
    dassList: List<QuizItem>,
    moodList: List<Int>,
    timeList: List<Timestamp>,
    acadList : List<QuizItem>,
    faceList : List<Int>,
    faceTimeList : List<Timestamp>,
    speechList : List<Int>,
    speechTimeList : List<Timestamp>,

): Double {

    val pssScore = calculatePSSScore(pssList)
    val dssScore = calculateDASSStressScore(dassList)

    val pss = calculatePSS(pssScore) * 100
    val dass = calculateDASS(dssScore) * 100
    val acadScore = (calculateAcadScore(acadList) / 4) * 100

    val modifiedTimeList = calculateConsecutiveDifferences(convertTimestampsToHours(timeList))
    val mood = calculateMood(moodList, modifiedTimeList)

    val modifiedFaceTimeList = calculateConsecutiveDifferences(convertTimestampsToHours(faceTimeList))
    val faceScore = calculateFace(faceList, modifiedFaceTimeList)

    val modifiedSpeechTimeList = calculateConsecutiveDifferences(convertTimestampsToHours(speechTimeList))
    val speechScore = calculateFace(speechList, modifiedSpeechTimeList)

    println(hasUserCompletedTest(moodList) + (hasUserCompletedTest(dassList)) + (hasUserCompletedTest(pssList)) + hasUserCompletedTest(faceList) + hasUserCompletedTest(speechList) + hasUserCompletedTest(acadList))
    return hasUserCompletedTest(moodList) + (hasUserCompletedTest(dassList)) + (hasUserCompletedTest(pssList)) + hasUserCompletedTest(faceList) + hasUserCompletedTest(speechList) + hasUserCompletedTest(acadList)
}

fun calculateAforDep(
    bdiList: List<QuizItem>,
    dassList: List<QuizItem>,
): Double {

    val bdiScore = calculateBDIScore(bdiList)
    val dssAScore = calculateDASSDepressionScore(dassList)

    val bdi = calculatePSS(bdiScore) * 100
    val dass = calculateDASS(dssAScore) * 100


//    println( "A: ${50 * checkTest(mood) + (30 * checkTest(dass)) + (20 * checkTest(pss))}")
    return 50 * hasUserCompletedTest(bdiList) + (50 * hasUserCompletedTest(dassList))
}

fun calculateAforAnxiety(
    baiList: List<QuizItem>,
    dassList: List<QuizItem>,
): Double {

//    println( "A: ${50 * checkTest(mood) + (30 * checkTest(dass)) + (20 * checkTest(pss))}")
    return 50 * hasUserCompletedTest(baiList) + (50 * hasUserCompletedTest(dassList))
}

fun calculateAforStigma(
    ismiList: List<QuizItem>,
    daysList: List<QuizItem>,
): Double {

//    println( "A: ${50 * checkTest(mood) + (30 * checkTest(dass)) + (20 * checkTest(pss))}")
    return hasUserCompletedTest(ismiList) + (hasUserCompletedTest(daysList))
}


fun calculateStress(
    mood: Double,
    dass: Double,
    pss: Double,
    acad : Double,
    face : Double,
    speech : Double,
    pssList: List<QuizItem>,
    dassList: List<QuizItem>,
    moodList: List<Int>,
    timeList: List<Timestamp>,
    acadList : List<QuizItem>,
    faceList : List<Int>,
    faceTimeList : List<Timestamp>,
    speechList : List<Int>,
    speechTimeList : List<Timestamp>,
): Double {
//    println((50 * mood + 30 * dass + 20 * pss))
    val faceAbs = if(face < 0) 0.0 else face
    val speechAbs = if(speech < 0) 0.0 else speech
    val acadAbs = if(acad < 0) 0.0 else acad
    return (mood + dass + pss + faceAbs + speechAbs + acadAbs) / calculateA(pssList, dassList, moodList, timeList, acadList, faceList, faceTimeList, speechList, speechTimeList)
}
fun calculateDepression(
    dass: Double,
    bdi: Double,
    bdiList: List<QuizItem>,
    dassList: List<QuizItem>,
): Double {
//    println((50 * mood + 30 * dass + 20 * pss))
    return (50 * dass + 50 * bdi) / calculateAforDep(bdiList, dassList)
}

fun calculateAnxiety(
    dass: Double,
    bai: Double,
    baiList: List<QuizItem>,
    dassList: List<QuizItem>,
): Double {
//    println((50 * mood + 30 * dass + 20 * pss))
    return (50 * dass + 50 * bai) / calculateAforAnxiety(baiList, dassList)
}

fun calculateStigma(
    ismi: Double,
    days: Double,
    ismiList: List<QuizItem>,
    daysList: List<QuizItem>,
): Double {
//    println((50 * mood + 30 * dass + 20 * pss))
    return (ismi + days) / calculateAforStigma(ismiList, daysList)
}


fun convertTimestampsToHours(timestamps: List<Timestamp>): List<Double> {
    return timestamps.map { timestamp ->
        // Extract the hour and minute from the Timestamp
        val date = timestamp.toDate() // Convert Timestamp to Date
        val calendar = Calendar.getInstance().apply {
            time = date // Set the time of the Calendar to the Date
        }

        // Get hour and minute
        val hour = calendar.get(Calendar.HOUR_OF_DAY) // Hour in 24-hour format
        val minute = calendar.get(Calendar.MINUTE)

        // Convert to hours as Double
        hour + minute / 60.0
    }
}

fun calculateConsecutiveDifferences(hours: List<Double>): List<Double> {
    if (hours.isEmpty()) return emptyList()

    // The first element remains the same
    val result = mutableListOf(hours[0])

    // Calculate the differences for subsequent elements from the previous element
    for (i in 1 until hours.size) {
        val difference = hours[i] - hours[i - 1]
        result.add(difference)
    }

    return result
}

fun finalStress(
    pssList: List<QuizItem>,
    dassList: List<QuizItem>,
    moodList: List<Int>,
    timeList: List<Timestamp>,
    acadList : List<QuizItem>,
    faceList : List<Int>,
    faceTimeList : List<Timestamp>,
    speechList : List<Int>,
    speechTimeList : List<Timestamp>,
): StressValues {
    val pssScore = calculatePSSScore(pssList)
    val dssScore = calculateDASSStressScore(dassList)
    val acadScore = calculateAcadScore(acadList)

    val pss = calculatePSS(pssScore) * 100
    val dass = calculateDASS(dssScore) * 100
    val acad = (acadScore / 4) * 100

    val modifiedTimeList = calculateConsecutiveDifferences(convertTimestampsToHours(timeList))
    val mood = calculateMood(moodList, modifiedTimeList)

    val modifiedFaceTimeList = calculateConsecutiveDifferences(convertTimestampsToHours(faceTimeList))
    val faceScore = calculateFace(faceList, modifiedFaceTimeList)

    val modifiedSpeechTimeList = calculateConsecutiveDifferences(convertTimestampsToHours(speechTimeList))
    val speechScore = calculateFace(speechList, modifiedSpeechTimeList)

    println("pss value: $pss")
    println("dass value: $dass")

    println("pss score : $pssScore")
    println("dass score : $dssScore")
    println("mood value: $mood")
    val stress = calculateStress(
        mood = mood,
        dass = dass,
        pss = pss,
        acad = acad,
        face = faceScore,
        speech = speechScore,
        pssList = pssList,
        dassList = dassList,
        moodList = moodList,
        timeList = timeList,
        faceList = faceList,
        faceTimeList = faceTimeList,
        speechList = speechList,
        speechTimeList = speechTimeList,
        acadList = acadList
    )
    return StressValues(
        stressValue = stress,
        pss = pssScore,
        dss = dssScore,
        acad = acadScore,
        face = faceScore,
        speech = speechScore
    )
}

fun finalDepression(
    bdiList: List<QuizItem>,
    dassList: List<QuizItem>,
): DepressionValues {
    val bdiScore = calculateBDIScore(bdiList)
    val dssScore = calculateDASSDepressionScore(dassList)

    val bdi = calculateBDI(bdiScore) * 100
    val dass = calculateDASSDepression(dssScore) * 100


    val depression = calculateDepression(
        dass = dass,
        bdi = bdi,
        bdiList,
        dassList,
    )
    return DepressionValues(
        depressionValue = depression,
        bdi = bdiScore,
        dssDep = dssScore
    )
}

fun finalAnxiety(
    baiList: List<QuizItem>,
    dassList: List<QuizItem>,
): AnxietyValues {
    val baiScore = calculateBAIScore(baiList)
    val dssScore = calculateDASSAnxietyScore(dassList)

    val bai = calculateBAI(baiScore) * 100
    val dass = calculateDASSAnxiety(dssScore) * 100



    val anxiety = calculateAnxiety(
        dass = dass,
        bai = bai,
        baiList,
        dassList,
    )
    return AnxietyValues(
        anxietyValue = anxiety,
        bai = baiScore,
        dssAnx = dssScore
    )
}


fun finalStigma(
    ismiList: List<QuizItem>,
    daysList: List<QuizItem>,
): StigmaValues {
    val ismiScore = calculateISMIScore(ismiList)
    val daysScore = calculateDaysScore(daysList)

    val ismi = (ismiScore / 4) * 100
    val days = (daysScore / 196) * 100

    val stigma = calculateStigma(
        ismi = ismi,
        days = days,
        ismiList = ismiList,
        daysList = daysList
    )
    return StigmaValues(
        stigmaValue = stigma,
        ismi = ismiScore,
        days =daysScore
    )
}

// Example usage
fun hasUserCompletedTest(list: List<Any>?): Double {
    return if (!list.isNullOrEmpty()) 1.0 else 0.0
}

