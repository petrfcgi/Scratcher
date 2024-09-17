package eu.petrfaruzel.scratcher.core.presentation.compose.screens

import androidx.annotation.StringRes
import eu.petrfaruzel.scratcher.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ErrorScreenWithRetry(@StringRes errorResId: Int, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = errorResId),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(onClick = onRetry) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun ErrorScreenNoRetry(@StringRes errorResId: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = errorResId),
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ErrorScreenWithRetryPreview() {
    ErrorScreenWithRetry(
        R.string.error_generic
    ) {}
}

@Composable
@Preview(showBackground = true)
fun ErrorScreenNoPreview() {
    ErrorScreenNoRetry(
        R.string.error_generic
    )
}