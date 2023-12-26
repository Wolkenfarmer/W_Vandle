package de.wolkenfarmer.w_vandle

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.wolkenfarmer.w_vandle.ui.LightBoxSize
import de.wolkenfarmer.w_vandle.ui.theme.W_VandleTheme
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            W_VandleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MainContent() {
    var animationStyle by remember { mutableStateOf(AnimationStyle.STATIC) }
    var color by remember { mutableStateOf(Color.White) }
    var lightBoxSize by remember { mutableStateOf(LightBoxSize.MEDIUM) }
    var hideUI by remember { mutableStateOf(false) }
    val hiddenUiAlpha = 0.2f

    val window = (LocalView.current.context as Activity).window
    window.statusBarColor = if (hideUI) Color.Black.toArgb() else colorScheme.primary.toArgb()

    fun setAnimation(newAnimationStyle: AnimationStyle) { animationStyle = newAnimationStyle }
    fun setColor(newColor: Color) { color = newColor }
    fun setLightBoxSize(newLightBoxSize: LightBoxSize) { lightBoxSize = newLightBoxSize }
    fun setHideUI(newHideUI: Boolean) { hideUI = newHideUI }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = if (hideUI) Color.Black else colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f, true))
        LightBox(animationStyle, color, lightBoxSize)
        Spacer(modifier = Modifier.size(50.dp))
        AnimationStyleSelector(
            modifier = Modifier.alpha(if (hideUI) hiddenUiAlpha else 1f),
            onOptionSelected = { setAnimation(it) }
        )
        Spacer(modifier = Modifier.size(25.dp))
        LightBoxSizeSelector(
            modifier = Modifier.alpha(if (hideUI) hiddenUiAlpha else 1f),
            onOptionSelected = { setLightBoxSize(it) }
        )
        Spacer(modifier = Modifier.size(25.dp))
        Text(
            modifier = Modifier.alpha(if (hideUI) hiddenUiAlpha else 1f),
            text = "Color"
        )
        ColorPicker(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .alpha(if (hideUI) hiddenUiAlpha else 1f),
            onColorSelected = { setColor(it) }
        )
        Spacer(modifier = Modifier.weight(1f, true))
        HideUIButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .alpha(if (hideUI) hiddenUiAlpha else 1f),
            onButtonClicked = { setHideUI(it) }
        )
    }
}

@Composable
fun HideUIButton(
    modifier: Modifier = Modifier,
    onButtonClicked: (Boolean) -> Unit
) {
    val uiHidden = remember { mutableStateOf(false) }

    Button(
        onClick = {
            uiHidden.value = !uiHidden.value
            onButtonClicked(uiHidden.value)
        },
        modifier = modifier
    ) {
        Text(if (uiHidden.value) "Show UI" else "Hide UI")
    }
}

@Composable
fun LightBox(
    animationStyle: AnimationStyle,
    color: Color,
    lightBoxSize: LightBoxSize
) {
    val alpha = remember { Animatable(initialValue = 0.4f) }

    LaunchedEffect(key1 = animationStyle) {
        when (animationStyle) {
            AnimationStyle.STATIC -> alpha.snapTo(1f)
            AnimationStyle.ERRATIC ->
                while (true) {
                    val targetAlpha = Random.nextDouble(0.4, 1.0).toFloat()
                    val durationMillis = Random.nextInt(100, 2000)
                    Log.d("Animation", "New target alpha: $targetAlpha, duration: $durationMillis")

                    alpha.animateTo(
                        targetValue = targetAlpha,
                        animationSpec = tween(durationMillis = durationMillis, easing = FastOutSlowInEasing)
                    )
                }
        }
    }

    Box(
        modifier = Modifier
            .background(color.copy(alpha = alpha.value), MaterialTheme.shapes.medium)
            .size(lightBoxSize.size.dp, lightBoxSize.size.dp),
    )
}

@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    MainContent()
}