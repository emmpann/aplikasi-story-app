package com.github.emmpann.aplikasistoryapp

import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.StoryResponse

object DataDummy {

    fun generateDummyStoryResponse(): List<StoryResponse> {
        val items: MutableList<StoryResponse> = arrayListOf()
        for (i in 0..30) {
            val story = StoryResponse(
                "https://photo",
                "12-02-2023",
                "gaming",
                "this is a photo that I took when I was 10",
                lon = 0.0,
                "1",
                lat = 0.0,
            )
            items.add(story)
        }
        return items
    }
}