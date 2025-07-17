# FlyingFootball
My repository for my new android game, Flying football. 
Originally designed to be my first android game and a spin-off of `Flappy Bird` with a football theme.
Started by following a [tutorial series on YouTube by gaseluta technology](https://www.youtube.com/playlist?list=PLxgX0Xe-eVYak1iBUEW7ES3e0fQfxOQw0) and 
then using my own ideas, knowledge of Java, and AI help to continue the development.  

## 🎮 Core Features
- **Immersive Backgrounds & Sprite Groups**: Rich visuals and layered sprite architecture enhance player immersion.
- **Responsive Controls & Interaction**: Tap to fly, dodge obstacles, and traverse levels using intuitive input mechanisms.
- **Goal-Based Level Design**: Progress through increasingly complex layouts with adaptive speed scaling.
- **Collision Mechanics**: Intelligent barrier detection paired with a refined physics system ensures engaging challenge.
- **Score System & Feedback**: Real-time scoring with visual feedback encourages continuous replay.
- **Game Over Logic**: A streamlined defeat screen brings closure with leaderboard integration.
- **Pause & Menu Options**: Convenient in-game controls allow players to pause and navigate seamlessly.
- **Start Menu with Navigation**: Launch the game, adjust settings, explore modes, or exit with ease.

## 📊 Player Retention Systems
- **Leaderboard Support**: Tracks high scores separately across different game modes.
- **Username Persistence**: Save and reuse player names across sessions for consistent identity.
- **Customisation Menu**: Switch up your style with selectable football skins (v2.0+).
- **Account Features**: Progress tracking and preferences (v1.9+).

## 🚀 Game Modes Breakdown
Each mode offers a unique twist on the core gameplay loop:

| Mode        | Description                                                                                                                                                                              |
|-------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Endless** | No death — score increases through goals (+1) and decreases with barrier collisions (-1). Players can pause or end the game any time and save their progress to a dedicated leaderboard. |
| **Classic** | True Flappy Bird-style challenge — one hit ends the run. High scores are stored separately.                                                                                              |
| **Story**   | Complete a curated set of levels with fixed lives. Each level set is grouped by difficulty and includes skippable cinematics. See Categories below:                                      |
| **Arcade**  | A power-up-enhanced remix of Classic mode, with multiple lives and random boosts for longevity.                                                                                          |

Story Mode Categories:
- _Noob_: Even noobs can complete these levels
- _Decent_: Decent skill is required to complete
- _Intermediate_: Medium difficulty levels
- _Adept_: Harder than intermediate but you don't need to be a pro or expert to complete
- _Pro/Expert_: If you complete these you're a certified expert at the game and can go pro!
- _Hacker_: Almost impossible to complete, only available in the premium version and cannot be completed unless 'hacks' are turned on...

---

## 📦 Release Timeline
- ✅ Version 1.7 — Initial launch with Endless & Classic modes
- ❎ Version 1.8 — Story mode with structured level design [Coming Soon]
- ❎ Version 1.9 — Arcade mode as well as Account features for player progress tracking [Coming Soon]
- ❎ Version 2.0 — Skins and customisation menu [Coming Soon]
- 🔄 Ongoing updates and maintenance

---

## 📣 Promotion & Publishing
Ready for the Google Play Store. Actively promoted through social and community channels.  

---

## Version History

### Version 1.00 (Nov 2023)
Initial development of the app. Created Sprite batches and Background visible on screen only. 
Started following tutorials series: https://www.youtube.com/playlist?list=PLxgX0Xe-eVYak1iBUEW7ES3e0fQfxOQw0  

### Version 1.01 (Nov 2023)
This version added the football (theme-based substitute for flappy bird) with 2 images. 
The visuals showed it switching between the 2 images every 250 milliseconds (1/4 or 0.25 seconds) and it was in the direct centre of the screen.  

### Version 1.10 (Nov 2023)
Added movement of the football based on user input. 
The football was still switching between images, however removed the 0.25 second delay so was now switching instantly. 
The football moves up a small amount when the player taps the screen and moves down automatically when the player isn't tapping the screen. 
It can go above the display, however it can't go below so sits at the bottom when falls all the way down.  

### Version 1.11 (Nov 2023)
Updated movement of the football, didn't add anything else yet. 
Added top barrier now and moved bottom barrier slightly up so that football is fully visible when sitting at the bottom. 
Football images now only switch when the player taps, normally in state 1, but when the player taps, 
updates to state 2 showing image 2 then switches back when player lets go, if they hold, it stays in state 2 for longer.  

### Version 1.20 (Nov 2023)
Added 1 moving goal with support to the centre of the screen. 
It appears when the player taps the screen and continues moving left, 
then when the player taps again, it reappears in the centre, it can go off screen as well.  

### Version 1.30 (October 2024)
Added more moving goals with random support heights so now there's 6 that loop around. 
Football movement was adjusted so that it moves up higher with each tap (can now move to top of screen with 3 taps). 
Also added a black outline around the football and goal supports, this was purely a testing feature and in preparation for collision detection later on.  

### Version 1.31 (October 2024)
First step in changing the logo, nothing really changed gameplay wise.  

### Version 1.32 (October 2024)
Logo was fully changed, nothing else changes then.  

### Version 1.33 (October 2024)
Above goal barrier with outline was added, other than that, nothing else yet  

### Version 1.40 (October 2024)

### Version 1.50

### Version 1.51

### Version 1.52

### Version 1.53

### Version 1.54

### Version 1.55

### Version 1.60

### Version 1.61

### Version 1.62

### Version 1.63

### Version 1.64

### Version 1.65

### Version 1.66

### Version 1.67

### Version 1.70

### Version 1.71

### Version 1.72

### Version 1.73

### Version 1.74

### Version 1.75

### Version 1.76
