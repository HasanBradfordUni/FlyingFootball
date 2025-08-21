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
Also added a black outline around the football and goal supports, 
this was purely a testing feature and in preparation for collision detection later on.  

### Version 1.31 (October 2024)  
First step in changing the logo, nothing really changed gameplay wise.  

### Version 1.32 (October 2024)  
Logo was fully changed, nothing else changes then.  

### Version 1.33 (October 2024)  
Above goal barrier with outline was added, other than that, nothing else yet  

### Version 1.40 (October 2024)  
Goals now start further out at the beginning (1 whole width of the display further). 
Lower barrier and ball outlines added with first stage of collision detection - at the moment just logs collision.  

### Version 1.50 (March 2025)  
Added Scoring system and text font. Reworked Collision detection and so now works fully as intended, 
nothing happens after collision yet though (just logs it). Barrier and Ball outlines still there for now.  

### Version 1.51 (April 2025)  
Added scoring text, however not fully in center and score gets added when going through barriers as well currently. 
Barrier and Ball outlines still there for now.  

### Version 1.52 (May 2025)  
Added Game Over Texture, New Game State for it and Collision logic. 
Restart logic not yet implemented so can't replay after Game Over currently. 
Barrier and Ball outlines still there for now.  

### Version 1.53 (May 2025)  
Added Restart Game logic, however now a bug noticeable where the ball sticks to the bottom 
and can't be moved up by the player if it touches the bottom. 
Also jump of ball too high so can't actually go through goals. 
Barrier and Ball outlines still there for now.  

### Version 1.54 (May 2025)  
Some minor gameplay adjustments to make it easier. The goal velocity was decreased from 3 to 4. 
Ball jump up velocity was decreased from 30 to 10 so now the jumps are smaller and easier to control. 
Barrier heights were decreased slightly so now it's easier to go through without hitting them. 
Fixed ball sicking to bottom bug so moves up a bit (100 pixels) when hits bottom and can still be moved then.  

### Version 1.55 (May 2025)  
Removed the barrier outlines (were only used for testing purposes) but 
may bring them back as a special togglable feature. Nothing else changed.  

### Version 1.60 (May 2025)  
Reworked game speed logic so now increases with time 
(but also decreases at certain points, uses sine curve). 
Also increased height and width of game over graphic, 
however placement now misaligned (goes off screen towards right side).  

### Version 1.61 (May 2025)  
Some minor graphical and gameplay changes. Decreased width between goals from 3 to 2.5. 
Realigned game over graphic so back in center. Also fixed alignment of score text so fully in center now. 
Also decreased height of barriers slightly so more room for ball to go through goal 
balancing gameplay more (decrease of distance between goals also balances gameplay).  

### Version 1.62 (May 2025)  
Added quite a few changes here, the main addition being the pause menu and game state. 
First of all gamestate constants were added mapping to the different numberings of the now 4 gamestates. 
The "PAUSED" gamestate was added along with the pause button, 
main menu button (on pause screen) and new font size for these features. 
However, in this version the pause button doesn't work yet and the ball floats 
upwards and sticks to the top unless the pause button is tapped.  

### Version 1.63 (May 2025)  
Pause menu button graphics updated so now has a blue backing and looks like an actual button. 
Works now as well and pauses the game. 
A paused game graphic was added which displays when the game is paused (straight after tapping the pause button) 
and disappears when the game is unpaused - by tapping anywhere except the "Main Menu" button while on the pause screen. 
Main Menu button doesn't do anything yet (haven't yet added the main menu but will do soon) but doesn't resume the game either when tapped.  

### Version 1.64 (May 2025)  
Just improved placement and visuals of "Main Menu" button, nothing else yet.  

### Version 1.65 (May 2025)  
Added the Main Menu which loads when the app starts. 
Displays the game title and 3 buttons all arranged vertically in a center column, 
new font size created and used. The 3 buttons currently are "Play Game", "Settings" and "Exit". 
New font size set as Blue colour and size 50, 
used for text that flashes up after tapping "Settings" button - this text should stay there but need to fix that then. 
After pressing "Play Game" button, 
4 new buttons flash up for the different game modes that will be coming soon, 
however these should also stay there and not just flash up, 
planning on fixing that next so can progress to actual game via "Play Game" then "Classic". 
"Exit" button closes the app as expected.  

### Version 1.66 (May 2025)  
Minor styling changes and new game state added. 
The positions of button and font sizes were adjusted slightly, 
as well as a new screen being added for the gamemode select to ensure it doesn't flash but 
appear as a permanent selection screen until tapped again to go back to the main menu. 
However, game crashes on this version straight after 
pressing "Play Game" and gamemode select screen doesn't appear yet.  

### Version 1.67 (June 2025)  
Gamestates were re-adjusted and 2 new screens added. 
The gamemode select screen now works and lets you select a gamemode. 
Currently, both Classic mode and Endless mode buttons take the player to the main game 
and gameplay is the same as before. The "Main Menu" button on the pause menu still doesn't work however. 
Tapping on the Settings button on the main menu also goes to the settings screen 
which is just a coming soon message so far, 
albeit the text size and positioning is still a bit off for this. 
Story and Arcade mode buttons flash up a coming soon message but 
this also needs to be a permanent screen transition.  

### Version 1.70 (June 2025)  
Added a scoring file as well as a leaderboard and 
all associated methods to record and display high scores. 
Leaderboard button was added as a yellow button on the main menu and 
can tap again from any of these screens (Leaderboard, Settings, etc.) to go back to the main menu. 
Main menu button on the pause menu also works now and goes back to the main menu. 
Text styling and visuals need a bit of work though. 
Also game mode coming soon messages still flash and now sends the player back to the main menu straight away. 
Leaderboard scores work but text displayed is too big and unorganised.  

### Version 1.71

### Version 1.72

### Version 1.73

### Version 1.74

### Version 1.75

### Version 1.76
