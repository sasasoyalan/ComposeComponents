package com.sasasoyalan.composecomponents

import com.sasasoyalan.composecomponents.componentpreviewlist.AuthViewPreview
import com.sasasoyalan.composecomponents.componentpreviewlist.BarChartViewPreview
import com.sasasoyalan.composecomponents.componentpreviewlist.CommentInputViewPreview
import com.sasasoyalan.composecomponents.componentpreviewlist.PieChart3DViewPreview
import com.sasasoyalan.composecomponents.componentpreviewlist.RatingViewPreview
import com.sasasoyalan.composecomponents.componentpreviewlist.StepProgressIndicatorViewPreview

fun getComponents() = listOf(
    ComponentItem("AuthView", { AuthViewPreview() }),
    ComponentItem("RatingView", { RatingViewPreview() }),
    ComponentItem("CommentInputView", { CommentInputViewPreview() }),
    ComponentItem("BarChartView", { BarChartViewPreview() }),
    ComponentItem("PieChart3DView", { PieChart3DViewPreview() }),
    ComponentItem("StepProgressIndicatorView", { StepProgressIndicatorViewPreview() }),
)