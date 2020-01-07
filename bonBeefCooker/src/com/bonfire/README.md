# bonBeefCooker

A script that collects and cooks beef in Lumbridge for free and easy cooking XP.

## Features
* Runs to the Lumbridge cow pens
* Locates nearby raw beef and picks it up
* Runs to the Lumbridge range once inventory is full of raw beef
* Cooks all raw beef
* Power-drops all cooked and burnt items once finished cooking
* Searches randomly around the pen for beef if it cannot find any
* Keeps track of how much beef was picked up, cooked, and burnt

## Preview

![In-Game Preview](https://i.imgur.com/GYcErbR.png)

*In-Game Preview*

## Requirements
* No requirements

## Setup
* Simply start the script with an empty inventory
* I recommend using DaxWalker for this script, as I had some pathing issues when using Acuity

## Known Issues
* There doesn't exist logic to handle the edge case where you stop the script in the range and drop raw beef, then resume the script
* Bot may sometimes get stuck on door to range (Acuity seems to not have this issue)
* The "Beef Collected" counter may randomly not go up when a piece of raw beef is picked up. Seems to be an issue with how the ItemTableEvent is processed.

## Future Plans
* Fix any bugs
* Add more edge cases to make sure the bot doesn't hang or get stuck
* Add the amount of cooking XP gained and cooking levels gained to the GUI
* Add some sort of world hopping mechanic if there is no beef for an extended period of time

## Credits & Inspiration
The inspiration for this script came from RSPeer user @Hoid's post located ![HERE](https://discourse.rspeer.org/t/lumby-beef-cooker/1219)

Also, a thank you to RSPeer user @August for the GUI inspiration 
