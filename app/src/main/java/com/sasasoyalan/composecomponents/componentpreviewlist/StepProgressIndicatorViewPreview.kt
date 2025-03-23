package com.sasasoyalan.composecomponents.componentpreviewlist

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sasasoyalan.composecomponents.components.stepprogressindicatorview.StepIndicatorType
import com.sasasoyalan.composecomponents.components.stepprogressindicatorview.StepProgressIndicatorView

@Preview(showBackground = true)
@Composable
internal fun StepProgressIndicatorViewPreview() {
    val ctx = LocalContext.current
    val steps = listOf<@Composable () -> Unit>(
        { PieChart3DViewPreview() },
        { PieChart3DViewPreview() },
        { PieChart3DViewPreview() }
    )

    StepProgressIndicatorView(
        stepIndicatorType = StepIndicatorType.NumberStep,
        stepContents = steps,
        nextButton = {
            Text(
                "Next",
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color(0xFF6200EE), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color.White
            )
        },
        backButton = {
            Text(
                "Back",
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color.White
            )
        },
        isStepsFinished = {
            Toast.makeText(ctx, "Steps finished", Toast.LENGTH_SHORT).show()
        }
    )
}




