package com.sasasoyalan.composecomponents

import com.sasasoyalan.composecomponents.componentpreviews.AuthViewPreview
import com.sasasoyalan.composecomponents.componentpreviews.BarChartViewPreview
import com.sasasoyalan.composecomponents.componentpreviews.CollapsingHeaderViewPreview
import com.sasasoyalan.composecomponents.componentpreviews.CommentInputViewPreview
import com.sasasoyalan.composecomponents.componentpreviews.ExpandableListViewPreview
import com.sasasoyalan.composecomponents.componentpreviews.ExpandableTextViewPreview
import com.sasasoyalan.composecomponents.componentpreviews.PieChart3DViewPreview
import com.sasasoyalan.composecomponents.componentpreviews.RatingViewPreview
import com.sasasoyalan.composecomponents.componentpreviews.StepProgressIndicatorViewPreview

fun getComponents() = listOf(
    ComponentItem("AuthView", { AuthViewPreview() }),
    ComponentItem("RatingView", { RatingViewPreview() }),
    ComponentItem("CommentInputView", { CommentInputViewPreview() }),
    ComponentItem("BarChartView", { BarChartViewPreview() }),
    ComponentItem("PieChart3DView", { PieChart3DViewPreview() }),
    ComponentItem("StepProgressIndicatorView", { StepProgressIndicatorViewPreview() }),
    ComponentItem("ExpandableListView", { ExpandableListViewPreview() }),
    ComponentItem("CollapsingHeaderView", { CollapsingHeaderViewPreview() }),
    ComponentItem("ExpandableTextView", { ExpandableTextViewPreview() }),
)