package com.example.getwell.domain.provider

import android.annotation.SuppressLint
import android.content.Context
import java.time.LocalDate


val mentalHealthFacts = listOf(
    "Mental health is just as important as physical health.",
    "1 in 4 people in the world will be affected by mental disorders at some point in their lives.",
    "Depression is the leading cause of disability worldwide.",
    "Anxiety disorders are the most common mental health disorders in the U.S.",
    "Mental health disorders can affect anyone, regardless of age, gender, or background.",
    "Stigma is one of the biggest barriers to seeking mental health care.",
    "Mental illness is treatable, with many successful outcomes.",
    "Talking openly about mental health helps reduce stigma.",
    "Chronic stress can lead to mental health conditions like depression or anxiety.",
    "Regular exercise has been shown to reduce symptoms of depression and anxiety.",
    "Meditation and mindfulness can help reduce stress and improve mental health.",
    "Social support is crucial for mental health recovery.",
    "Around 50% of mental health conditions begin by age 14.",
    "Suicide is the second leading cause of death among individuals aged 15-29.",
    "Workplace stress can contribute to mental health issues.",
    "Sleep plays a critical role in maintaining good mental health.",
    "Nearly 1 billion people globally suffer from a mental health disorder.",
    "Seeking help is a sign of strength, not weakness.",
    "Access to mental health care remains a global challenge.",
    "Early intervention can significantly improve recovery outcomes for mental health issues.",
    "Mental health conditions often coexist with substance use disorders.",
    "Support groups can be an effective part of mental health recovery.",
    "There is no health without mental health.",
    "Mental health stigma can deter people from seeking help.",
    "Mental health issues can lead to physical health problems if left untreated.",
    "Stress can trigger or worsen mental health conditions.",
    "Children can also experience mental health disorders.",
    "Mental health affects how we think, feel, and act in everyday life.",
    "Bipolar disorder affects about 45 million people worldwide.",
    "Schizophrenia affects around 20 million people globally.",
    "Psychotherapy is an effective treatment for many mental health disorders.",
    "Loneliness and social isolation can impact mental health negatively.",
    "People with mental health conditions often face discrimination.",
    "Having a mental health condition does not define you.",
    "Post-traumatic stress disorder (PTSD) can develop after experiencing a traumatic event.",
    "It's okay to ask for help when feeling overwhelmed or stressed.",
    "Mental health disorders do not make a person weak.",
    "Chronic stress can lead to burnout, affecting both physical and mental health.",
    "Many mental health conditions can be managed with medication and therapy.",
    "Self-care plays an important role in maintaining mental health.",
    "Mental health care should be affordable and accessible to everyone.",
    "Around 800,000 people die by suicide every year.",
    "Early life experiences, including trauma, can impact mental health.",
    "Eating a balanced diet can improve your mental health.",
    "Mental health should be discussed as openly as physical health.",
    "Supportive workplaces can improve mental health outcomes for employees.",
    "Mental health stigma often leads to silence and shame.",
    "Mental health services are underfunded in many countries.",
    "It is estimated that depression and anxiety cost the global economy $1 trillion each year in lost productivity.",
    "Art, music, and creative outlets can positively impact mental health.",
    "1 in 6 people will experience a mental health issue in any given week.",
    "Panic attacks are a common symptom of anxiety disorders.",
    "Cognitive-behavioral therapy (CBT) is widely used to treat mental health conditions.",
    "Mental health problems can affect anyone, regardless of their socio-economic status.",
    "Mental health is essential for well-being and quality of life.",
    "Approximately 264 million people suffer from depression globally.",
    "It is important to break the silence surrounding mental health.",
    "Learning stress-management techniques can improve mental health.",
    "Self-stigma can lead to internalized shame and low self-esteem.",
    "Exercise can help relieve symptoms of anxiety.",
    "Talking to someone you trust can help when dealing with stress or anxiety.",
    "Sleep deprivation can worsen mental health conditions.",
    "Educating people about mental health can help reduce stigma.",
    "Anxiety and depression are among the most treatable mental health disorders.",
    "Mental health services should be integrated into primary healthcare systems.",
    "Family support is key for recovery from mental health disorders.",
    "Unresolved childhood trauma can impact mental health in adulthood.",
    "Mental health conditions often require long-term management.",
    "Taking mental health days from work or school is important for self-care.",
    "Cultural attitudes towards mental health vary widely.",
    "Perfectionism can be a risk factor for anxiety and depression.",
    "Mental health stigma can be perpetuated by negative portrayals in the media.",
    "Reducing the stigma of mental illness requires ongoing public education.",
    "Lack of understanding can fuel misconceptions about mental health.",
    "Mental health disorders can impact relationships with family and friends.",
    "Depression can manifest physically, including symptoms like fatigue and headaches.",
    "Chronic stress can weaken the immune system.",
    "Individuals with mental health conditions can lead fulfilling lives with appropriate care.",
    "Talking therapies, like counseling, are effective for treating mental health problems.",
    "Emotional well-being is a crucial component of mental health.",
    "Mental health literacy helps people recognize symptoms early.",
    "Social media can impact mental health positively or negatively, depending on usage.",
    "Public attitudes towards mental illness have improved in many countries but stigma still persists.",
    "Living with untreated mental illness can affect your ability to function day to day.",
    "Stress management is an important skill for maintaining mental health.",
    "Mental health conditions can affect sleep patterns.",
    "A supportive community can make a significant difference for those with mental health challenges.",
    "Mental health should be prioritized in policy-making and healthcare reform.",
    "Caring for someone with a mental illness can be emotionally challenging, but support is available.",
    "Mental health conditions can be exacerbated by external factors like poverty and unemployment.",
    "Mental health services need to be inclusive and culturally sensitive.",
    "Many people with mental health conditions contribute positively to society.",
    "Mental health conditions should not be a source of shame.",
    "Recovery from mental illness is possible with the right treatment and support.",
    "Mental health care requires more investment worldwide.",
    "Being kind to yourself is essential for good mental health.",
    "Stress is a natural response but chronic stress can be harmful.",
    "It's important to check in on your mental health regularly, just like your physical health.",
    "Workplace mental health initiatives can lead to better employee well-being and productivity.",
    "Awareness and education are key to combating mental health stigma.",
    "Mental health services are essential in crisis situations like pandemics and natural disasters.",
    "Stigma reduction campaigns can help normalize mental health conversations.",
    "Mental health issues can affect anyone, regardless of age, gender, race, or income level.",
    "Around 50% of mental health conditions begin by age 14, and 75% by age 24.",
    "People with mental health conditions are more likely to face stigma and discrimination.",
    "Good mental health is essential for overall well-being and quality of life.",
    "Talking about mental health can help reduce stigma and encourage others to seek help.",
    "Social support can significantly improve mental health outcomes.",
    "Mindfulness and meditation can help reduce symptoms of anxiety and depression.",
    "Regular sleep patterns contribute to better mental health and emotional stability.",
    "Physical activity can have a positive impact on mood and mental health.",
    "Early intervention and treatment can lead to better outcomes for mental health conditions.",
    "Mental health disorders can coexist with physical health conditions.",
    "Substance abuse can exacerbate mental health issues and vice versa.",
    "Self-care practices can help maintain mental well-being.",
    "Cognitive Behavioral Therapy (CBT) is an effective treatment for many mental health disorders.",
    "Depression affects more than 264 million people globally.",
    "Anxiety disorders are the most common mental illness in the U.S., affecting 40 million adults.",
    "Stigma around mental illness can lead to social isolation.",
    "Feeling sad or anxious is a normal reaction to life's challenges.",
    "Recovery from mental health conditions is possible with proper treatment and support.",
    "Resilience can be developed and is crucial for mental health.",
    "Positive relationships can have a significant impact on mental well-being.",
    "Exposure to trauma can increase the risk of developing mental health disorders.",
    "Healthy coping mechanisms can help manage stress effectively.",
    "The World Health Organization recognizes mental health as a fundamental human right.",
    "Lack of access to mental health care is a significant barrier for many people.",
    "Women are more likely than men to be diagnosed with depression and anxiety.",
    "Men are less likely to seek help for mental health issues due to societal stigma.",
    "Mental health is closely tied to physical health; poor mental health can lead to poor physical health.",
    "Stress management techniques can help mitigate the effects of chronic stress.",
    "Creative activities like art and music can positively influence mental health.",
    "Nature exposure can improve mood and reduce feelings of stress.",
    "Workplace mental health programs can enhance employee well-being and productivity.",
    "Education about mental health can empower individuals to seek help.",
    "Gratitude practices can increase happiness and reduce depression.",
    "Social media can have both positive and negative effects on mental health.",
    "Setting boundaries is important for maintaining mental well-being.",
    "People with mental health disorders often report feeling misunderstood.",
    "Mental health first aid can help individuals support others in crisis.",
    "Advocacy for mental health awareness can lead to better policies and resources.",
    "Learning about mental health can help reduce fear and stigma.",
    "Mental health treatment is not one-size-fits-all; it requires personalized approaches.",
    "Therapy can provide a safe space for individuals to express their thoughts and feelings.",
    "Regular check-ins with friends and family can strengthen social support networks.",
    "It’s okay to not be okay; seeking help is a sign of strength.",
    "Mental health should be prioritized just like physical health.",
    "Digital mental health tools can provide accessible support for many individuals.",
    "Work-life balance is crucial for maintaining mental health.",
    "Support groups can help individuals feel less alone in their struggles.",
    "A healthy diet can positively impact mood and mental health.",
    "Stress can manifest in physical symptoms, such as headaches and stomach issues.",
    "Building emotional intelligence can improve personal and professional relationships.",
    "The stigma surrounding mental health is slowly decreasing, but more work is needed.",
    "Peer support can play a vital role in recovery from mental health issues.",
    "Journaling can be a helpful tool for processing emotions and thoughts.",
    "Engaging in hobbies can enhance overall well-being and reduce stress."
)


object FactOftheDayProvider {
    private const val PREFS_NAME = "FactOfTheDayPrefs"
    private const val KEY_LAST_DATE = "lastDate"
    private const val KEY_CACHED_FACT = "cachedFact"

    @SuppressLint("NewApi")
    fun getFactOfTheDay(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val currentDate = LocalDate.now().toString()
        val lastDate = sharedPreferences.getString(KEY_LAST_DATE, "")

        return if (lastDate == currentDate) {
            sharedPreferences.getString(KEY_CACHED_FACT, mentalHealthFacts[0]) ?: mentalHealthFacts[0]
        } else {
            val currentDayOfYear = LocalDate.now().dayOfYear
            val factOfTheDay = mentalHealthFacts[currentDayOfYear % mentalHealthFacts.size]

            // Save new fact and current date in SharedPreferences
            sharedPreferences.edit().apply {
                putString(KEY_LAST_DATE, currentDate)
                putString(KEY_CACHED_FACT, factOfTheDay)
                apply()
            }

            factOfTheDay
        }
    }
}
