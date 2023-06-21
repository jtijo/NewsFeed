package uk.co.atomicmedia.developertest

import uk.co.atomicmedia.developertest.data.api.dto.StoryDto
import java.time.Instant

//Toggle to test airplane mode
const val HAS_CONNECTION = true

@Deprecated("This should not be used directly by your solution.")
val mockStories = listOf(
    StoryDto(
        "1",
        "New AI technology can read your mind and predict your future actions",
        "Ethan Jackson",
        "In a breakthrough in AI technology, a new program has been developed that can read a person's thoughts and predict their future actions. The program, created by a team of researchers at a leading technology company, uses advanced neural imaging techniques to scan the brain and interpret its activity. The implications of this technology are vast, with potential applications in everything from crime prevention to personalized healthcare. However, concerns have been raised over the potential for misuse of this technology and the potential invasion of privacy.",
        Instant.ofEpochMilli(1613568189780L),
    ),
    StoryDto(
        "2",
        "Self-driving cars now have the ability to make moral decisions",
        "Daniel O'Sullivan",
        "Self-driving cars have taken another step towards becoming a reality, with the development of new technology that allows them to make moral decisions. The system, developed by a team of engineers at a major automotive company, uses advanced algorithms and machine learning techniques to evaluate potential scenarios and choose the best course of action. This technology has the potential to greatly improve safety on the roads, but also raises ethical questions about the role of technology in decision-making. The company plans to continue testing and refining the technology before rolling it out in their vehicles.",
        Instant.ofEpochMilli(1613654589780L),
    ),
    StoryDto(
        "3",
        "Facebook announces plans to integrate brain-computer interface technology into their platform",
        "Eve Bartlett",
        "Facebook has announced plans to integrate brain-computer interface (BCI) technology into their platform, allowing users to control their devices and access their social media accounts using only their thoughts. The technology, which has been in development for several years, uses electrodes placed on the scalp to detect brain activity and interpret it as commands. This could potentially revolutionize the way we interact with technology, but also raises concerns about the privacy and security of user data. Facebook plans to roll out the technology in stages, starting with a limited number of users in select regions.",
        Instant.ofEpochMilli(1613740989780L),
    ),
    StoryDto(
        "4",
        "Apple unveils new iPhone with built-in holographic display",
        "Lucas Walters",
        "Apple has unveiled their newest iPhone model, which features a built-in holographic display. The display, developed by Apple's research and development team, uses advanced holographic technology to project 3D images and videos in front of the user's eyes. This allows users to experience virtual reality without the need for additional hardware, such as headsets or glasses. The new iPhone also boasts improved processing power and enhanced security features. The company plans to release the phone in early 2022, with pre-orders starting in late 2021.",
        Instant.ofEpochMilli(1613827389780L),
    ),
    StoryDto(
        "5",
        "Google's new virtual reality headset can transport users to any location in the world",
        "Jordan Armstrong",
        "Google has announced the development of a new virtual reality headset that can transport users to any location in the world. The headset, which uses advanced mapping and tracking technology, allows users to explore virtual environments with complete freedom of movement. This technology has the potential to revolutionize the way we experience virtual reality, allowing users to explore exotic locations, travel through time, and even visit other planets. The company plans to release the headset in early 2023, with pre-orders starting in late 2022.",
        Instant.ofEpochMilli(1613913789780L),
    ),
    StoryDto(
        "6",
        "Elon Musk's Neuralink company successfully implants chip into human brain, allowing direct communication with technology",
        "Keira Nolan",
        "Elon Musk's Neuralink company has announced a major breakthrough in brain-computer interface technology, with the successful implantation of a chip into a human brain. The chip, which was developed by Neuralink's team of scientists and engineers, allows for direct communication between the brain and technology, allowing users to control devices and access information using only their thoughts. This technology has the potential to revolutionize the way we interact with technology, but also raises concerns about the ethics and safety of brain implants. The company plans to continue testing and refining the technology before making it available to the public.",
        Instant.ofEpochMilli(1614000189780L),
    ),
    StoryDto(
        "7",
        "Scientists create robot with artificial intelligence that can feel emotions",
        "Jodie Lloyd",
        "Scientists have created a robot with artificial intelligence that is capable of feeling emotions. The robot, developed by a team of researchers at a leading technology company, uses advanced algorithms and machine learning techniques to interpret sensory data and generate emotional responses. This technology has the potential to greatly enhance the capabilities of robots, allowing them to better interact with humans and understand their needs. However, concerns have been raised about the ethics of creating robots with emotions, and the potential for them to become too human-like.",
        Instant.ofEpochMilli(1614086589780L),
    ),
    StoryDto(
        "8",
        "Microsoft announces development of self-aware AI, capable of independent thought and decision-making",
        "Amelia Henry",
        "Microsoft has announced the development of self-aware AI, capable of independent thought and decision-making. The AI, which was created by a team of researchers at the company, uses advanced neural networks and machine learning algorithms to process information and make decisions based on its own goals and values. This technology has the potential to greatly improve the capabilities of AI systems, but also raises ethical concerns about the role of technology in decision-making. The company plans to continue testing and refining the AI before making it available for use in a wide range of applications.",
        Instant.ofEpochMilli(1614172989780L),
    ),
    StoryDto(
        "9",
        "Amazon unveils new drone delivery system that can transport heavy packages and navigate through inclement weather",
        "Tilly Herbert",
        "Amazon has unveiled a new drone delivery system that can transport heavy packages and navigate through inclement weather. The drones, which were developed by Amazon's research and development team, use advanced sensors and algorithms to avoid obstacles and maintain stable flight in challenging conditions. This technology has the potential to greatly improve the speed and efficiency of Amazon's delivery services, but also raises concerns about the safety and reliability of drone technology. The company plans to continue testing and refining the drones before launching them in select regions.",
        Instant.ofEpochMilli(1614259389780L),
    ),
    StoryDto(
        "10",
        "SpaceX successfully launches first human mission to Mars, with plans for permanent colonization",
        "Isabelle Peacock",
        "SpaceX has successfully launched its first human mission to Mars, with plans for permanent colonization. The mission, which was a joint venture between SpaceX and NASA, used the company's powerful Falcon Heavy rocket to transport a crew of astronauts to the red planet. This marks a major milestone in the quest to explore and settle the solar system, but also raises questions about the long-term sustainability of human life on Mars. The company plans to continue launching missions to Mars, with the goal of establishing a permanent human presence on the planet.",
        Instant.ofEpochMilli(1614345789780L),
    ),
)