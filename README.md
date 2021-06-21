# AntiAFK
AntiAFK plugin for Minecraft servers.

Designed for 1.8.8 servers not tested on other versions.
- Antirejoin
- Changeable items
- Changeable trigger blocks
- Changeable kick options

That was my first plugin attempt, do not take it seriously.

Example config:

``` yaml
# Blockbreak event check list
block-break:
  - COAL_ORE
  - IRON_ORE
  - GOLD_ORE
  - DIAMOND_ORE
  - REDSTONE_ORE
  - EMERALD_ORE
  - LAPIS_ORE
#Chance for checking player
check-chance: 0.25
# Bot check menu item list
menu-items:
  COAL_ORE: '&aCoal Ore'
  DIRT: '&aDirt'
  SAND: '&aSand'
  IRON_ORE: '&aIron Ore'
  GOLD_BLOCK: '&aGold Block'
  GRASS: '&aGrass'
  BEACON: '&aBeacon'
  MINECART: '&aMinecart'
  BUCKET: '&aBucket'
# Kick options
kick-mode: true
kick-interval: 10
kick-message: '&4You have been kicked from the server because you did not complete bot control within 60 seconds.'
anti-rejoin: true
# Plugin reload options
reload-message: '&6[ANTIAFK] Plugin reloaded.'
true-usage: '&6[ANTIAFK] &a/antiafk reload'
```
