package com.example.basiccomposeintermediate

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basiccomposeintermediate.ui.theme.BasicComposeIntermediateTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			BasicComposeIntermediateTheme {
				MyApp(modifier = Modifier.fillMaxSize())
			}
		}
	}
}

@Composable
fun MyApp(modifier: Modifier = Modifier, ) {
	var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

	Surface(
		modifier = modifier,
	) {
		if (shouldShowOnboarding) {
			OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false})
		} else {
			Greetings()
		}
	}
}

@Composable
fun Greetings(modifier: Modifier = Modifier, names: List<String> = List(1000) { "${it}"}) {
	LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
		items(items = names) {
			Greeting(it)
		}
	}
}

@Composable
fun Greeting(name: String) {
	val expanded = remember { mutableStateOf(false) }

	Card(
		modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.primary
		),
	) {
		Row(
			modifier = Modifier
				.padding(12.dp)
				.animateContentSize(
					animationSpec = spring(
						dampingRatio = Spring.DampingRatioMediumBouncy,
						stiffness = Spring.StiffnessLow
					)
				)
		) {
			Row(modifier = Modifier.padding(24.dp)) {
				Column(modifier = Modifier
					.weight(1f)
					.padding(bottom = 12.dp)
				) {
					Text(text = "Hello,")
					Text(text = name, style = MaterialTheme.typography.headlineMedium.copy(
						fontWeight = FontWeight.ExtraBold
					))
					if (expanded.value) {
						Text(
							text = ("Composem ipsum color sit lazy, " +
									"padding theme elit, sed do bouncy. ").repeat(4),
						)
					}
				}
				IconButton(onClick = { expanded.value = !expanded.value }) {
					Icon(
						imageVector = if (expanded.value) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
						contentDescription = if (expanded.value) {
							stringResource(R.string.show_less)
						} else {
							stringResource(R.string.show_more)
						}
					)
				}
			}
		}
	}
}


@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit, modifier: Modifier = Modifier, ) {
	Column(
		modifier = modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text("Welcome to the Basics Codelab!")
		Button(
			modifier = Modifier.padding(vertical = 24.dp),
			onClick = onContinueClicked
		) {
			Text("Continue")
		}
	}
}

@Composable
fun OnboardingPreview() {
	BasicComposeIntermediateTheme {
		OnboardingScreen({})
	}
}

@Preview(
	showBackground = true,
	widthDp = 320,
	uiMode = UI_MODE_NIGHT_YES,
	name = "Dark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
	BasicComposeIntermediateTheme {
		Greetings()
	}
}
