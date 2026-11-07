# MobHeadsLooting

Paper plugin that makes Looting raise head drops from [Jodek's Mob Heads](https://modrinth.com/datapack/mob-heads), which ignores Looting by design.

When a player kills a mob with a Looting weapon and the normal roll gives no head, the plugin rolls Mob Heads' loot table for that mob again, once per Looting level, and keeps the first head it gets. Mob Heads' drop rates, drop mode (player kills or charged creepers) and variants still decide every roll, and a mob never drops two heads. A 5% head becomes 9.8%, 14.3% and 18.5% at Looting I, II and III. Heads from the [Tropical Fish add-on](https://modrinth.com/datapack/mob-heads-tropical-fish) count too.

Looting counts as it does in vanilla: from the main-hand weapon of the player whose attack killed the mob, arrows included. Kills by a tamed wolf don't count.

## Install

1. Install Mob Heads and lib-5555ff on a Paper 26.3 server.
2. Put `MobHeadsLooting-1.0.jar` from [Releases](https://github.com/lzlrd/MobHeadsLooting/releases) in `plugins/` and restart.

There is nothing to configure.

## Build

```sh
./gradlew build
```

Needs JDK 25. Writes `build/libs/MobHeadsLooting-1.0.jar`.

## Licence

[PolyForm Noncommercial 1.0.0](LICENSE.md).
