package com.example.getwell.screens.relaxScreen.physicaltherapy.yogaapp.data

import com.example.getwell.R
import com.example.getwell.screens.relaxScreen.physicaltherapy.yogaapp.model.YogaPose

object YogaData {
    val poses = listOf(
        YogaPose(
            id = 1,
            name = "Child's Pose",
            sanskritName = "Balasana",
            description = "A restful pose that helps calm the mind and reduce stress and anxiety.",
            benefits = listOf(
                "Relieves stress and anxiety",
                "Calms the mind",
                "Releases tension in the back, shoulders, and chest",
                "Promotes relaxation"
            ),
            instructions = listOf(
                "Start by kneeling on the floor",
                "Touch your big toes together and sit on your heels",
                "Separate your knees about hip-width apart",
                "Exhale and lay your torso down between your thighs",
                "Extend your arms in front of you, palms down",
                "Rest your forehead on the ground",
                "Hold this pose for 5-10 breaths"
            ),
            imageUrl = R.drawable.balasana,
            duration = 5
        ),
        YogaPose(
            id = 2,
            name = "Legs Up the Wall",
            sanskritName = "Viparita Karani",
            description = "A restorative pose that calms the nervous system and helps with anxiety.",
            benefits = listOf(
                "Reduces anxiety and stress",
                "Calms the nervous system",
                "Improves sleep",
                "Relieves mild depression"
            ),
            instructions = listOf(
                "Sit with one hip against the wall",
                "Swing your legs up the wall as you lie back",
                "Move your buttocks close to the wall",
                "Rest your arms at your sides, palms up",
                "Close your eyes and breathe deeply",
                "Hold this pose for 5-15 minutes"
            ),
            imageUrl = R.drawable.viparitakarani,
            duration = 10
        ),
        YogaPose(
            id = 3,
            name = "Corpse Pose",
            sanskritName = "Savasana",
            description = "A deeply relaxing pose that reduces stress and promotes mental clarity.",
            benefits = listOf(
                "Reduces stress and anxiety",
                "Promotes deep relaxation",
                "Improves mental clarity",
                "Helps with depression"
            ),
            instructions = listOf(
                "Lie on your back",
                "Spread your legs slightly apart",
                "Let your arms rest at your sides, palms up",
                "Close your eyes",
                "Focus on your breath",
                "Relax each part of your body",
                "Stay in this pose for 5-15 minutes"
            ),
            imageUrl = R.drawable.savasana,
            duration = 15
        ),
        YogaPose(
            id = 4,
            name = "Cat-Cow Pose",
            sanskritName = "Marjaryasana-Bitilasana",
            description = "A gentle flow between two poses that warms the body and brings flexibility to the spine while calming the mind.",
            benefits = listOf(
                "Relieves stress",
                "Improves spinal flexibility",
                "Promotes mindfulness and breathing",
                "Soothes the nervous system"
            ),
            instructions = listOf(
                "Start on your hands and knees in a tabletop position",
                "Ensure your wrists are under your shoulders and knees under your hips",
                "Inhale, drop your belly, lift your tailbone and gaze upwards into Cow Pose",
                "Exhale, round your back, tuck your chin, and draw your belly inwards into Cat Pose",
                "Repeat the sequence for 5-10 breaths"
            ),
            imageUrl = R.drawable.marjaryasanabitilasana,
            duration = 5
        ),

        YogaPose(
            id = 5,
            name = "Seated Forward Bend",
            sanskritName = "Paschimottanasana",
            description = "A calming pose that stretches the back body and relieves stress.",
            benefits = listOf(
                "Relieves stress and mild depression",
                "Stretches the spine, shoulders, and hamstrings",
                "Calms the mind",
                "Reduces fatigue"
            ),
            instructions = listOf(
                "Sit on the floor with your legs extended straight in front of you",
                "Inhale and lengthen your spine, reaching your arms overhead",
                "Exhale and hinge forward from your hips, reaching for your feet",
                "Keep your spine long and avoid rounding your back",
                "Hold for 5-10 breaths, breathing deeply"
            ),
            imageUrl = R.drawable.paschimottanasana,
            duration = 5
        ),
        YogaPose(
            id = 6,
            name = "Bridge Pose",
            sanskritName = "Setu Bandhasana",
            description = "A gentle backbend that opens the chest and relieves stress.",
            benefits = listOf(
                "Reduces anxiety and fatigue",
                "Stretches the chest, neck, and spine",
                "Calms the mind and promotes relaxation",
                "Improves blood circulation"
            ),
            instructions = listOf(
                "Lie flat on your back with knees bent and feet hip-width apart",
                "Place your arms by your sides with palms facing down",
                "Press into your feet and lift your hips toward the ceiling",
                "Keep your thighs parallel and interlace your fingers under your back",
                "Hold this pose for 5-10 breaths, then slowly lower your hips"
            ),
            imageUrl = R.drawable.setubandhasana,
            duration = 5
        ),
        YogaPose(
            id = 7,
            name = "Butterfly Pose",
            sanskritName = "Baddha Konasana",
            description = "A seated pose that opens the hips and promotes relaxation.",
            benefits = listOf(
                "Reduces stress and fatigue",
                "Stretches the inner thighs and groin",
                "Promotes a sense of calm",
                "Improves posture and flexibility"
            ),
            instructions = listOf(
                "Sit on the floor with your legs extended in front of you",
                "Bend your knees and bring the soles of your feet together",
                "Hold your feet with your hands and let your knees fall naturally towards the floor",
                "Keep your spine straight and breathe deeply",
                "Hold this pose for 5-10 breaths"
            ),
            imageUrl = R.drawable.baddhakonasana,
            duration = 5
        ),

        YogaPose(
            id = 8,
            name = "Standing Forward Bend",
            sanskritName = "Uttanasana",
            description = "A calming pose that stretches the back and relieves stress.",
            benefits = listOf(
                "Relieves stress and anxiety",
                "Stretches the hamstrings, calves, and back",
                "Calms the mind and reduces fatigue",
                "Promotes relaxation"
            ),
            instructions = listOf(
                "Stand with your feet hip-width apart and arms by your sides",
                "Inhale, then exhale as you hinge forward from your hips",
                "Let your head and arms hang toward the floor",
                "Keep a slight bend in your knees if needed",
                "Hold this pose for 5-10 breaths"
            ),
            imageUrl = R.drawable.uttanasana,
            duration = 5
        ),





    )
}