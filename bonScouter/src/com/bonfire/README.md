# bonScouter
A script that scouts a specific area and hops worlds, calling out any people that have specific items.

## Inspiration
The inspiration for this script came from Crumb's video on Revenant Scouting Bots located [HERE](https://www.youtube.com/watch?v=FF6R_XamIV0). People charge a lot of money to run bots like this, so I figured I'd make one for free.

## Features
* Scans nearby players' equipment
* Checks equipment against a user-submitted list of valuable items
* Notifies the user of any players that have valuable items, providing the following information:
  * The player's name
  * The player's world
  * If the player is skulled or  not
  * The player's equipment list
* Includes Discord WebHook integration to allow for Discord message sending when a valuable player is found
* Hops to a random world once the full scan is complete

## Requirements
* A list of items to search for (prompt is shown when running the script)
  * This must be a comma-separated list (e.g. `Armadyl godsword, Staff of air, Dragon platebody`)
  * Item names must be exact
  
## Future Plans
* Have the bot run back to the place it originally started (if it were to die)
* Use a mix of item IDs and item names
* Allow the importing/exporting of valuable item lists
* Create a Java list/listview, allowing for easy insertion and deletion of valuable items
* Enable users to scout all players, not just ones with specific items
* Enable users to select which worlds they're like to scout (PvP, F2P, P2P, etc.)

## Credits
Thank you to [k3kdude](https://github.com/k3kdude) for his DiscordWebhook helper class for Java located here: [HERE](https://gist.github.com/k3kdude/fba6f6b37594eae3d6f9475330733bdb#file-discordwebhook-java)