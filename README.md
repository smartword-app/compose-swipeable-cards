<p align="center">
  <img src="/assets/cards.png" alt="Compose Swipeable Cards" width="200" height="200">
</p>

# Compose Lazy Swipeable Cards

[![](https://jitpack.io/v/smartword-app/compose-swipeable-cards.svg)](https://jitpack.io/#smartword-app/compose-swipeable-cards)

An efficient, lightweight, modern Jetpack Compose library that provides smooth Tinder-style card swiping interactions using Jetpack Compose.

## Video Example
https://github.com/user-attachments/assets/240f8279-1c13-4a97-8fbd-465e09514921

## Features
- ⚡️ Lazy loading to support large data
- 📚 Programmatic Card Swiping
- 🎯 Smooth, physics-based swipe animations
- 🎨 Fully customizable card content
- 🔄 Left/Right swipe actions
- 📚 Stacked cards effect
- 🎮 Interactive gesture controls
- 📱 Easy to integrate
- ⚡ Lightweight with minimal dependencies
- 🎯 Built with Jetpack Compose

## Installation

1. Add JitPack repository to your project's build.gradle:
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency to your app's build.gradle:
```gradle
dependencies {
    implementation 'com.github.smartword-app:compose-swipeable-cards:1.1.4'
}
```

## Basic Usage

```kotlin
@Composable
fun SwipeableCardsExample() {
    val profiles = remember { listOf(/* your data items */) }
    
    val state = rememberSwipeableCardsState(
        initialCardIndex = 0,
        itemCount = { profiles.size }
    )

    LazySwipeableCards<CardData>(
        modifier = Modifier.padding(10.dp),
        state = state,
        onSwipe = { profile, direction ->
            when (direction) {
                SwipeableCardDirection.Right -> { /* Handle right swipe */ }
                SwipeableCardDirection.Left -> { /* Handle left swipe */ }
            }
        }
    ) {
        items(profiles) { profile, index, offset ->
            ProfileCard(
                profile = profile,
                offset = offset
            )
        }
    }
}
```

### Programmatic Card Swiping

You can now programmatically swipe cards in either direction using the new `swipe()` method:

```kotlin
// Add swipe buttons to your UI
FloatingActionButton(
    onClick = { state.swipe(SwipeableCardDirection.Left) }
) {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
        contentDescription = null,
    )
}

FloatingActionButton(
    onClick = { state.swipe(SwipeableCardDirection.Right) }
) {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = null,
    )
}
```

## Customization

### Properties
```kotlin
SwipeableCards(
    properties = SwipeableCardsProperties(
        visibleCardsInStack = 3,                // Number of visible cards
        padding = 20.dp,                        // Stack padding
        swipeThreshold = 50.dp,                 // Swipe distance threshold
        lockBelowCardDragging = true,           // Lock cards below top card
        enableRotation = true,                  // Enable rotation animation
        stackedCardsOffset = 40.dp,             // Offset between cards
        draggingAcceleration = 1.5f             // Drag sensitivity
    )
)
```

### Animations
```kotlin
SwipeableCards(
    animations = SwipeableCardsAnimations(
        cardsAnimationSpec = spring(
            dampingRatio = 0.6f,
            stiffness = 100f
        ),
        rotationAnimationSpec = spring()
    )
)
```

### Custom Factors
```kotlin
LazySwipeableCards(
    factors = SwipeableCardsFactors(
        rotationFactor = { offset ->
            // Custom rotation calculation
            offset.x / 50
        },
        scaleFactor = { index, state, props ->
            // Custom behind card scaling based on currest state
            1f
        },
        cardOffsetCalculation = { index, state, props ->
            // Custom card position calculation
            val offset = props.stackedCardsOffset.value * (index - state.currentCardIndex)
            Offset(-offset, offset)
        }
    )
) { ... }
```

## Example Implementation

Here's a complete example showing a dating app-style card stack:

```kotlin
data class Profile(
    val name: String,
    val age: Int,
    val imageUrl: String
)

@Composable
fun DatingCardStack(profiles: List<Profile>) {
    val state = rememberSwipeableCardsState(itemCount = { profiles.size })

    LazySwipeableCards(
        modifier = Modifier.fillMaxSize(),
        items = profiles,
        state = state,
        onSwipe = { profile, direction ->
            when (direction) {
                SwipeableCardDirection.Right -> handleLike(profile)
                SwipeableCardDirection.Left -> handleDislike(profile)
            }
        }
    ) {
        items(profiles) { profile, index, offset ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(400.dp)
            ) {
                Column {
                    AsyncImage(
                        model = profile.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                    Text(
                        text = "${profile.name}, ${profile.age}",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
```

## State Management

The library provides a `SwipeableCardsState` class to manage the card stack state:

```kotlin
val state = rememberSwipeableCardsState(
    initialCardIndex = 0,
    itemCount = { profiles.size }
)

// Navigate programmatically (e.g on button click)
state.moveNext()     // Move to next card
state.goBack()       // Go to previous card

// Check if can go back (e.g for disable undo button)
if (state.canSwipeBack) {
    // Handle back navigation
}
```

## Requirements
- Android API Level 21+
- Jetpack Compose 1.4.0+
- Kotlin 1.8.0+

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.
