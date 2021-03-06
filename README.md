# Akmk (灰汁巻き)

This library adds ConstraintLayout DSL in Anko library. It is based on the `1.1.0-beta4` version of the library.

## Usage([wiki](https://github.com/seisuke/akmk/wiki/Usage))

ConstraintLayout is defined and added to other ViewGroups in the same way as any other view in Anko:

```kotlin
anyViewGroupLayout {
    constraintLayout {
        val button1 = button {
            id = View.generateViewId()
            text = "1"
        }

        val button2 = button {
            id = View.generateViewId()
            text = "2"
        }
        
        constraints({
            // view horizontal constraints definitions
            button1 - dip(30) % button2
            alignTail(button1, button2)
        }, {
            // view vertical constraints definitions
            parent - button1 - parent
        })
    }
}
```

## Installation

```gradle
dependencies {
    compile 'com.github.seisuke:akmk:0.3.0'
}

repositories {
    maven { url 'http://raw.github.com/seisuke/akmk/release/repository/' }
}
```

## Similar projects

 * https://github.com/AckeeCZ/anko-constraint-layout

## References

 * https://developer.android.com/training/constraint-layout/index.html
 
## TODO

 * Placeholders
 * Dimension DSL
 * Percent Dimensions
