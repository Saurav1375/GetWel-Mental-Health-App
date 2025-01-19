package com.example.getwell.screens.relaxScreen.stigmaQuiz

object Questions {

    var quizzes = listOf<Quiz>()
    val questionBank = listOf(
        Question(
            id = 1,
            questionText = "What does 'stigma' mean in the context of mental health?",
            options = listOf("A positive view of mental illness", "A neutral term with no implications", "Negative attitudes and beliefs about mental illness", "Lack of awareness about mental health"),
            correctAnswerIndex = 2
        ),
        Question(
            id = 2,
            questionText = "How can stigma impact individuals with mental health conditions?",
            options = listOf("It can improve their self-esteem", "It can encourage them to seek help", "It can lead to discrimination and isolation", "It has no effect on them"),
            correctAnswerIndex = 2
        ),
        Question(
            id = 3,
            questionText = "Which of the following is an example of public stigma?",
            options = listOf("A person being ashamed of their own mental health condition", "Society discriminating against those with mental health issues", "Offering support and resources for mental health", "A family member hiding a relative’s mental health condition"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 4,
            questionText = "How does self-stigma differ from public stigma?",
            options = listOf("It involves discrimination from friends and family", "It involves internalizing negative beliefs about oneself", "It only affects those without mental health issues", "It has no effect on mental health outcomes"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 5,
            questionText = "Which of the following can help reduce stigma around mental health?",
            options = listOf("Spreading myths about mental illness", "Encouraging open conversations about mental health", "Discouraging people from seeking help", "Ignoring the problem"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 6,
            questionText = "What is the main goal of mental health awareness?",
            options = listOf("To isolate those with mental health issues", "To create fear around mental health topics", "To educate the public and promote understanding", "To discourage people from discussing mental health"),
            correctAnswerIndex = 2
        ),
        Question(
            id = 7,
            questionText = "Which day is globally recognized as World Mental Health Day?",
            options = listOf("October 10", "May 10", "January 20", "December 5"),
            correctAnswerIndex = 0
        ),
        Question(
            id = 8,
            questionText = "Why is it important to recognize early signs of mental health issues?",
            options = listOf("To prevent progression of mental illness and seek timely help", "To avoid interacting with those affected", "So people can ignore symptoms until they go away", "To discourage individuals from sharing their experiences"),
            correctAnswerIndex = 0
        ),
        Question(
            id = 9,
            questionText = "Which of the following statements is true about mental health?",
            options = listOf("Mental health only affects a small percentage of the population", "Mental health includes emotional, psychological, and social well-being", "Mental health problems are always visible and easy to identify", "Only adults experience mental health issues"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 10,
            questionText = "How can mental health awareness campaigns help society?",
            options = listOf("By making mental health less important", "By reducing stigma and promoting supportive environments", "By encouraging people to avoid those with mental illnesses", "By discouraging people from talking about their issues"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 11,
            questionText = "Which of the following is a common myth about mental health?",
            options = listOf("Mental health issues are treatable", "Mental health problems are a sign of personal weakness", "Seeking help is important for mental well-being", "Mental health issues can happen to anyone"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 12,
            questionText = "True or False: Only people with severe mental health issues need to seek help.",
            options = listOf("True", "False"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 13,
            questionText = "Which of these statements is true?",
            options = listOf("Mental illness is always lifelong and untreatable", "With treatment, many people with mental health issues can recover or manage their conditions", "Mental health only affects one's emotional state, not physical health", "Therapy and medication are ineffective for mental health conditions"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 14,
            questionText = "What is one misconception about people with mental health conditions?",
            options = listOf("They are often unpredictable and dangerous", "They are capable of managing relationships and careers", "They are as deserving of respect and empathy as anyone else", "With treatment, they can lead fulfilling lives"),
            correctAnswerIndex = 0
        ),
        Question(
            id = 15,
            questionText = "True or False: Talking about mental health openly encourages stigma.",
            options = listOf("True", "False"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 16,
            questionText = "Which of the following is a positive approach to supporting someone with a mental health condition?",
            options = listOf("Ignoring their feelings and behaviors", "Offering a listening ear and non-judgmental support", "Blaming them for their condition", "Keeping them isolated"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 17,
            questionText = "What role do education and awareness play in reducing stigma?",
            options = listOf("They have no effect on stigma", "They reinforce negative stereotypes", "They help people understand and accept mental health conditions", "They encourage people to hide their struggles"),
            correctAnswerIndex = 2
        ),
        Question(
            id = 18,
            questionText = "How can media portrayals influence stigma around mental health?",
            options = listOf("By promoting only negative stereotypes", "By raising awareness and educating the public", "By preventing people from understanding mental health issues", "By discouraging open discussions"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 19,
            questionText = "What can individuals do to help reduce mental health stigma in their communities?",
            options = listOf("Avoid conversations about mental health", "Share accurate information and listen without judgment", "Encourage people to hide their mental health status", "Spread myths and misconceptions"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 20,
            questionText = "Which of these actions could reduce self-stigma in people facing mental health challenges?",
            options = listOf("Practicing self-compassion and seeking support", "Blaming oneself for having mental health issues", "Avoiding any discussions about mental health", "Isolating from friends and family"),
            correctAnswerIndex = 0
        ),
        // More questions would follow in a similar format up to id = 70.
        Question(
            id = 21,
            questionText = "What is an important step towards supporting mental health in workplaces?",
            options = listOf("Providing regular mental health training", "Avoiding conversations on mental health", "Discouraging employees from seeking help", "Ignoring mental health concerns"),
            correctAnswerIndex = 0
        ),
        Question(
            id = 21,
            questionText = "What is a common sign of mental health distress?",
            options = listOf("Increased energy levels", "Withdrawal from social activities", "Excessive happiness", "Enhanced focus"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 22,
            questionText = "How can friends support someone with a mental health condition?",
            options = listOf("By avoiding them", "By encouraging unhealthy coping mechanisms", "By listening and being present", "By judging their feelings"),
            correctAnswerIndex = 2
        ),
        Question(
            id = 23,
            questionText = "Which of the following is a potential barrier to seeking help for mental health?",
            options = listOf("Availability of resources", "Support from family and friends", "Fear of stigma", "Awareness of mental health issues"),
            correctAnswerIndex = 2
        ),
        Question(
            id = 24,
            questionText = "True or False: Mental health disorders are often hereditary.",
            options = listOf("True", "False"),
            correctAnswerIndex = 0
        ),
        Question(
            id = 25,
            questionText = "Which mental health condition is characterized by persistent feelings of sadness and loss of interest?",
            options = listOf("Anxiety Disorder", "Depression", "Bipolar Disorder", "Schizophrenia"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 26,
            questionText = "What role does therapy play in mental health treatment?",
            options = listOf("It is always the only treatment needed", "It helps individuals develop coping strategies", "It is not effective for any mental health issues", "It focuses solely on medication management"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 27,
            questionText = "Which of the following is a common misconception about therapy?",
            options = listOf("Therapists provide unconditional support", "Therapy is only for people with severe issues", "Therapists are judgmental", "Therapy can help improve mental health"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 28,
            questionText = "How can regular exercise benefit mental health?",
            options = listOf("It can increase feelings of stress", "It helps in developing negative thought patterns", "It releases endorphins and improves mood", "It has no impact on mental health"),
            correctAnswerIndex = 2
        ),
        Question(
            id = 29,
            questionText = "What is the term for experiencing intense fear or panic attacks without a clear trigger?",
            options = listOf("Generalized Anxiety Disorder", "Social Anxiety Disorder", "Panic Disorder", "Obsessive-Compulsive Disorder"),
            correctAnswerIndex = 2
        ),
        Question(
            id = 30,
            questionText = "True or False: Everyone experiences mental health issues at some point in their lives.",
            options = listOf("True", "False"),
            correctAnswerIndex = 0
        ),
        Question(
            id = 31,
            questionText = "What is one effective way to manage stress?",
            options = listOf("Ignoring stressors", "Practicing mindfulness and relaxation techniques", "Taking on more responsibilities", "Avoiding social interactions"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 32,
            questionText = "Which demographic is particularly at risk for mental health issues due to societal pressures?",
            options = listOf("Teenagers", "Middle-aged adults", "Elderly individuals", "All of the above"),
            correctAnswerIndex = 3
        ),
        Question(
            id = 33,
            questionText = "What is the impact of stigma on mental health treatment?",
            options = listOf("It encourages more people to seek help", "It can discourage individuals from seeking necessary treatment", "It has no impact", "It promotes understanding"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 34,
            questionText = "Which of the following is a common symptom of PTSD?",
            options = listOf("Increased focus", "Flashbacks", "Excessive energy", "Mood swings"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 35,
            questionText = "How can workplaces promote mental health awareness?",
            options = listOf("Ignoring employee well-being", "Providing mental health resources and support", "Discouraging open discussions about mental health", "Limiting access to mental health days"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 36,
            questionText = "What does 'mindfulness' refer to?",
            options = listOf("Living in the past", "Being present in the moment", "Focusing solely on future goals", "Ignoring one’s feelings"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 37,
            questionText = "Which mental health condition is often characterized by extreme mood swings?",
            options = listOf("Anxiety Disorder", "Bipolar Disorder", "Depression", "Schizophrenia"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 38,
            questionText = "What can individuals do to support mental health awareness?",
            options = listOf("Spread rumors about mental illness", "Educate themselves and others about mental health", "Avoid discussing mental health issues", "Ignore signs of distress in others"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 39,
            questionText = "What role does sleep play in mental health?",
            options = listOf("It has no effect on mental health", "Good sleep can improve mental health, while poor sleep can worsen it", "Sleep is only important for physical health", "Sleep patterns do not relate to emotional well-being"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 40,
            questionText = "How can talking about mental health help reduce stigma?",
            options = listOf("It makes people uncomfortable", "It can foster understanding and acceptance", "It has no effect on stigma", "It discourages people from sharing their experiences"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 41,
            questionText = "What is one risk factor for developing a mental health condition?",
            options = listOf("Having a supportive family", "High levels of stress", "Regular exercise", "Positive social interactions"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 42,
            questionText = "True or False: Therapy is a sign of weakness.",
            options = listOf("True", "False"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 43,
            questionText = "Which of the following can be a symptom of anxiety?",
            options = listOf("Feeling calm and relaxed", "Increased heart rate", "Improved focus", "Decreased irritability"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 44,
            questionText = "What can be a barrier to accessing mental health care?",
            options = listOf("Availability of therapy", "High costs of treatment", "Support from friends", "Awareness of mental health issues"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 45,
            questionText = "Which mental health issue is often misdiagnosed as a physical condition?",
            options = listOf("Depression", "Obsessive-Compulsive Disorder", "Generalized Anxiety Disorder", "All of the above"),
            correctAnswerIndex = 3
        ),
        Question(
            id = 46,
            questionText = "How can journaling benefit mental health?",
            options = listOf("It causes more stress", "It helps to process thoughts and feelings", "It is a waste of time", "It encourages negative thinking"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 47,
            questionText = "Which of the following is a protective factor for mental health?",
            options = listOf("Isolation", "Supportive relationships", "High stress levels", "Negative self-talk"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 48,
            questionText = "What is the main purpose of the National Suicide Prevention Lifeline?",
            options = listOf("To encourage people to keep their struggles to themselves", "To provide free and confidential support for those in crisis", "To diagnose mental health issues", "To promote medication as the only solution"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 49,
            questionText = "Which of the following is a sign that someone may be struggling with mental health?",
            options = listOf("Increased productivity", "Changes in behavior and mood", "Positive social interactions", "Enhanced focus on tasks"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 50,
            questionText = "How can community programs help with mental health?",
            options = listOf("They can promote understanding and access to resources", "They can spread stigma", "They can ignore mental health issues", "They can discourage seeking help"),
            correctAnswerIndex = 0
        ),
        Question(
            id = 51,
            questionText = "What is the benefit of practicing gratitude for mental health?",
            options = listOf("It has no effect", "It can improve mood and overall well-being", "It increases anxiety", "It discourages positive thinking"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 52,
            questionText = "True or False: Children cannot experience mental health issues.",
            options = listOf("True", "False"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 53,
            questionText = "Which of the following therapies is often used to treat PTSD?",
            options = listOf("Cognitive Behavioral Therapy", "Electroconvulsive Therapy", "Medication only", "Surgery"),
            correctAnswerIndex = 0
        ),
        Question(
            id = 54,
            questionText = "What can be a long-term effect of untreated mental health issues?",
            options = listOf("Improved relationships", "Increased stress", "Better performance at work", "Enhanced coping skills"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 55,
            questionText = "What does it mean to be mentally healthy?",
            options = listOf("Having no problems", "Being able to cope with daily stressors", "Always feeling happy", "Avoiding all negative feelings"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 56,
            questionText = "How can social media impact mental health?",
            options = listOf("It has no impact", "It can lead to comparison and negative feelings", "It always improves mental health", "It only affects older adults"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 57,
            questionText = "What is self-care?",
            options = listOf("Ignoring personal needs", "Taking actions to improve mental and physical health", "Only focusing on work", "Avoiding self-reflection"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 58,
            questionText = "Which of the following statements about mental health is true?",
            options = listOf("Mental health problems are rare", "Mental health is not important", "Mental health affects overall well-being", "Only people with severe issues need help"),
            correctAnswerIndex = 2
        ),
        Question(
            id = 59,
            questionText = "What is one of the first signs of stress?",
            options = listOf("Increased sleep", "Better concentration", "Irritability", "Calmness"),
            correctAnswerIndex = 2
        ),
        Question(
            id = 60,
            questionText = "True or False: Medication is the only way to treat mental health issues.",
            options = listOf("True", "False"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 61,
            questionText = "Which of the following can trigger mental health issues?",
            options = listOf("Chronic stress", "Supportive relationships", "Positive life changes", "Healthy coping mechanisms"),
            correctAnswerIndex = 0
        ),
        Question(
            id = 62,
            questionText = "How can family members support someone with mental health issues?",
            options = listOf("By criticizing their feelings", "By being understanding and supportive", "By ignoring their problems", "By discouraging them from seeking help"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 63,
            questionText = "What is one effect of social isolation on mental health?",
            options = listOf("Improved mood", "Increased feelings of loneliness", "Better relationships", "Enhanced self-esteem"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 64,
            questionText = "True or False: Stigma can prevent individuals from seeking help for mental health issues.",
            options = listOf("True", "False"),
            correctAnswerIndex = 0
        ),
        Question(
            id = 65,
            questionText = "What does emotional resilience refer to?",
            options = listOf("The ability to ignore emotions", "The ability to recover from stress and adversity", "Never feeling sad", "Avoiding challenges"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 66,
            questionText = "Which of the following is a healthy coping strategy?",
            options = listOf("Substance abuse", "Talking to a friend", "Avoiding problems", "Isolating oneself"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 67,
            questionText = "How can one reduce stress at work?",
            options = listOf("By ignoring deadlines", "By taking regular breaks", "By increasing workload", "By avoiding communication with coworkers"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 68,
            questionText = "What is the benefit of talking about mental health?",
            options = listOf("It encourages more stigma", "It can lead to better understanding and support", "It has no effect", "It discourages others from sharing their experiences"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 69,
            questionText = "What is the role of peer support in mental health recovery?",
            options = listOf("It is not helpful", "It can provide understanding and encouragement", "It only applies to physical health", "It is always unnecessary"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 70,
            questionText = "Which organization can provide support for mental health issues?",
            options = listOf("Support groups", "Educational institutions", "Mental health professionals", "All of the above"),
            correctAnswerIndex = 3
        )
    )

}