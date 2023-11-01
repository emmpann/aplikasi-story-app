package com.github.emmpann.aplikasistoryapp

import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.StoryResponse

object DataDummy {

    fun generateDummyStoryResponse(): List<StoryResponse> {
        val items: MutableList<StoryResponse> = arrayListOf()
        for (i in 0..1) {
            val story = StoryResponse(
                "",
                "",
                "",
                "",
                lon = 0.0,
                "",
                lat = 0.0,
            )
            items.add(story)
        }
        return items
    }
}