# unlimited minecart speed

fabric mod for minecraft `26.2` that makes minecarts stupid fast.

this started as just removing the vanilla `max_minecart_speed` cap, but that by itself was kinda lame because carts still accelerated like normal. so now powered rails also boost carts way harder.

## what it does

vanilla caps this gamerule at `1000`:

```mcfunction
/gamerule max_minecart_speed <value>
```

this mod raises the allowed value to `1000000000`, so these work:

```mcfunction
/gamerule max_minecart_speed 10000
/gamerule max_minecart_speed 1000000
/gamerule max_minecart_speed 1000000000
```

it also changes powered rail acceleration. powered rails now do this-ish:

```text
new speed = current speed * 1.5 + 0.2
```

so if the cart keeps hitting powered rails, it keeps getting faster and faster until it hits the gamerule limit.

## important stuff

the gamerule is still a max speed, not a teleport button.

if you put the gamerule at `1000000000`, the cart can go insanely fast, but it still needs powered rails or some other push to actually build up speed.

also, minecraft measures the gamerule as blocks per second, while entity movement uses blocks per tick. so a gamerule of `1000000000` means the real per-tick cap is around `50000000` blocks/tick.

yes, that is probably a bad idea.

## how to use

install the mod, load a world, then run:

```mcfunction
/gamerule max_minecart_speed 1000000000
```

then build a powered rail line and send a cart down it.

if you want less insane speed, use something smaller:

```mcfunction
/gamerule max_minecart_speed 10000
```

## install

you need:

- minecraft `26.2`
- fabric loader `0.19.3` or newer
- fabric language kotlin
- java `25`

put this jar in your instance `mods` folder:

```text
build/libs/unlimited-minecart-speed-1.1.0.jar
```

do not use the sources jar. that one is just code and will not work as the mod.

delete old versions first if you have them, especially any `1.0.0`, `1.0.1`, or `-sources.jar` copies.

## build

from the project folder:

```bash
gradle clean build
```

the mod jar will be here:

```text
build/libs/unlimited-minecart-speed-1.1.0.jar
```

## notes

this is not meant to be balanced.

high speeds can outrun chunk loading, make servers lag, or send carts so far away you lose them. that is kinda the point.

if it breaks, lower the gamerule or stop feeding it powered rails.
game starts lagging at like 200k speed so like idk maybe stay away from 100k and over
