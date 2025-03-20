package com.sasasoyalan.composecomponents

import com.sasasoyalan.composecomponents.components.authview.AuthViewPreview
import com.sasasoyalan.composecomponents.components.barchartview.BarChartViewPreview
import com.sasasoyalan.composecomponents.components.commentinputview.CommentInputViewPreview
import com.sasasoyalan.composecomponents.components.piechart3dview.PieChart3DViewPreview
import com.sasasoyalan.composecomponents.components.ratingview.RatingViewPreview

fun getComponents() = listOf(
    ComponentItem("AuthView", { AuthViewPreview() }),
    ComponentItem("RatingView", { RatingViewPreview() }),
    ComponentItem("CommentInputView", { CommentInputViewPreview() }),
    ComponentItem("BarChartView", { BarChartViewPreview() }),
    ComponentItem("PieChart3DView", { PieChart3DViewPreview() }),
)