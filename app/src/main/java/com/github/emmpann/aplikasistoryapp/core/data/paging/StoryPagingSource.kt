package com.github.emmpann.aplikasistoryapp.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.StoryResponse
import com.github.emmpann.aplikasistoryapp.core.data.remote.retrofit.ApiService

class StoryPagingSource(private val apiService: ApiService) : PagingSource<Int, StoryResponse>() {
    override fun getRefreshKey(state: PagingState<Int, StoryResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryResponse> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllStory(position, params.loadSize)

            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position -1,
                nextKey = if (responseData.listStory.isEmpty()) null else position + 1
            )

        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}