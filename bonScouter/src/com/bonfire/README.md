# bonScouter
A script that scouts a specific area and hops worlds, calling out any people that have specific items.

## Features
* Scans nearby players' equipment
* Checks equipment against a user-submitted list of valuable items
* Notifies the user of any players that have valuable items, providing the following information:
  * The player's name
  * The player's world
  * If the player is skulled or  not
  * The player's equipment list
  * The player’s combat level
  * The player’s target
  * The item that caused that player to be targeted
* Includes Discord WebHook integration to allow for Discord message sending when a valuable player is found
* Hops to a random world (can be filtered) once the full scan is complete

## Preview
![Script configuration menu](https://i.imgur.com/QKOrfL4.png)

_Script configuration menu_

![In-game view with statistics at the top-left](https://i.imgur.com/qWuroqM.png)

_In-game view with statistics at the top-left_

![Discord Webhook view](https://i.imgur.com/DeJ4ii7.png)

_Discord Webhook view_

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

## Inspiration
The inspiration for this script came from Crumb's video on Revenant Scouting Bots located [HERE](https://www.youtube.com/watch?v=FF6R_XamIV0). People charge a lot of money to run bots like this, so I figured I'd make one for free.

## Credits
Thank you to [k3kdude](https://github.com/k3kdude) for his DiscordWebhook helper class for Java located [HERE](https://gist.github.com/k3kdude/fba6f6b37594eae3d6f9475330733bdb#file-discordwebhook-java)

Thank you to "catch (Throwable e) { }" on the RSPeer discord for their help with the PVP world switch interface and helping me fix my `WorldHopper.randomHop()` predicates.
